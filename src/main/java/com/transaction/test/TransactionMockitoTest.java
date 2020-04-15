package com.transaction.test;

import static org.junit.Assert.*;

import com.transaction.test.dao.User1Mapper;
import com.transaction.test.dao.UserMapper;
import com.transaction.test.entry.User;
import com.transaction.test.service.TransactionProtogationTest;
import com.transaction.test.service.UserService;
import com.transaction.test.util.SpringUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static org.mockito.Mockito.*;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TransactionMockitoTest {

    @Resource
    private TransactionProtogationTest protogationTest;

    @Resource
    UserMapper userMapper;

    @Resource
    User1Mapper user1Mapper;

    @Before
    public void before(){
        protogationTest.truncated();
    }

    @Test
    @Transactional()
    public void simpleTest(){

        //创建mock对象，参数可以是类，也可以是接口
        List<String> list = mock(List.class);

        //设置方法的预期返回值
        when(list.get(0)).thenReturn("helloworld");

        String result = list.get(0);

        //验证方法调用(是否调用了get(0))
        verify(list).get(0);

        //junit测试
        assertEquals("helloworld", result);
    }

    /**
     * 条件：外部函数没有加事务，两个子事务是required，并且在外部调用函数的最后抛出异常
     * 结果：两个子函数都成功
     * 分析：（propagation = Propagation.REQUIRED）在外部没有事务，内部没有抛出异常的情况下，
     * 插入“张三”、“李四”方法在自己的事务中独立运行，外围方法异常不影响内部插入“张三”、“李四”方法独立的事务
     * @throws RuntimeException
     */
    @Test
    public void testNoTranNoInnerExptRequired(){
//        try {
//            protogationTest.notransaction_exception_required_required();
//        }catch (RuntimeException){
//
//        }
//        assertTrue(confirm());
    }

    /**
     * 条件：外部函数没有加事务，两个子事务是required，并且在内部一个函数中抛出异常
     * 结果：第一个函数成功，第二个抛出异常的函数失败
     * 分析：（propagation = Propagation.REQUIRED）在外部没有事务，内部有的事务抛出异常的情况下，
     * 插入“张三”、“李四”方法都在自己的事务中独立运行,所以插入“李四”方法抛出异常只会回滚插入“李四”方法，插入“张三”方法不受影响。
     */
    @Test
    public void testNoTranHasInnerExpRequired(){
        protogationTest.notransaction_required_required_exception();
    }

    /**
     * 条件：外部函数加事务，两个子事务是required，并且在外部调用函数的最后抛出异常
     * 结果：第一个函数和第二个函数都插入失败
     * 分析：（propagation = Propagation.REQUIRED）在外围开启事务，并且外围事务有异常的情况下，
     * 插入“张三”、“李四”方法都因为最后的抛出异常回滚，这两个插入方法被纳入同一个事务收到影响。
     * （外围方法开启事务，外围方法内的方法就应该在同一个事务中。外围方法抛出异常，整个事务所有方法都会被回滚。）
     */
    @Test
    public void testHasTranNoInnerExpRequired(){
        protogationTest.transaction_exception_required_required();
    }

    /**
     * 条件：外部函数加事务，两个子事务是required，并且在调用函数内部函数抛出异常
     * 结果：没有两个函数都插入失败
     * 分析：（propagation = Propagation.REQUIRED）在外围开启事务，并且内部事务有异常的情况下，
     * 插入“张三”、“李四”方法都因为最后调用的函数抛出异常回滚，这两个插入方法被纳入同一个事务收到影响。
     * 如果调换两个函数的顺序，依然会在遇到第一个异常的时候就回滚，导致两个函数都插入数据失败。
     */
    @Test
    public void testHasTranHasInnerExpRequired(){
        protogationTest.transaction_required_required_exception();
    }

    /**
     * 条件：外部函数加事务，两个子事务是required，并且在调用函数内部函数中捕获异常
     * 结果：两个函数都插入失败
     * 分析：外围方法开启事务，内部方法加入外围方法事务，内部方法抛出异常回滚，
     * 即使方法被catch不被外围方法感知，整个事务依然回滚。
     */
    @Test
    public void testHasTranHasInnerExpTryCatchRequired(){
        protogationTest.transaction_required_required_exception_try();
    }

    /**
     * 条件：外部函数不加事务，两个子事务是required_new，并且在外围函数中抛出异常
     * 结果：两个函数都插入承过
     * 分析：外围方法没有事务，插入“张三”、“李四”方法都在自己的事务中独立运行,外围方法抛出异常回滚不会影响内部方法。
     */
    @Test
    public void testNoTranNoInnerExpRequiredNew(){
        protogationTest.notransaction_exception_requiresNew_requiresNew();
    }


    /**
     * 条件：外部函数不加事务，两个子事务是required_new，并且在一个子事务中抛出异常
     * 结果：抛出异常的那个事务没有插入成功，未抛出异常的事务插入成功了
     * 分析：外围方法没有开启事务，插入“张三”方法和插入“李四”方法分别开启自己的事务，插入“李四”方法抛出异常回滚，其他事务不受影响。
     */
    @Test
    public void testNoTranHasInnerExpRequiredNew(){
        protogationTest.notransaction_requiresNew_requiresNew_exception();
    }

    /**
     * 条件：外部函数加事务，一个子事务是required，两个子事务是required_new，并且在外部函数中抛出异常
     * 结果：required_new内部函数都插入成功，required的插入失败
     * 分析：因为required是加入外部事务，所以外部事物抛出异常它会回滚，而required_new是将外部事务挂起，所以未收到影响
     * （外围方法开启事务，插入“张三”方法和外围方法一个事务，插入“李四”方法、插入“王五”方法分别在独立的新建事务中，
     * 外围方法抛出异常只回滚和外围方法同一事务的方法，故插入“张三”的方法回滚。）
     */
    @Test
    public void testHasTranNoInnerExpRequiredNew(){
        protogationTest.transaction_exception_required_requiresNew_requiresNew();
    }

    /**
     * 条件：外部函数加事务，一个子事务是required，两个子事务是required_new，并且在一个required_new的内部函数中抛出异常
     * 结果：因为required是加入外部事务，外部事务接受到了某一个required_new抛出异常回滚所以插入失败，
     * 没有抛出异常的required_new插入成功，
     * 抛出异常的required_new回滚插入失败
     * 分析：required_new没有加入外围事务，但是回滚的话会向上抛出影响外围事务回滚
     * 分析：
     */
    @Test
    public void testHasTranHasInnerExpRequiredNew(){
        protogationTest.transaction_required_requiresNew_requiresNew_exception();
    }

    /**
     * 条件：外部函数加事务，一个子事务是required，两个子事务是required_new，并且在一个required_new的内部函数中抛出异常并catch
     * 结果：张三（插入），李四（插入），王五（未插入）
     * 分析：外围方法开启事务，插入“张三”方法和外围方法一个事务，插入“李四”方法、插入“王五”方法分别在独立的新建事务中。插入“王五”方法抛出异常，首先插入
     * “王五”方法的事务被回滚，异常被catch不会被外围方法感知，外围方法事务不回滚，故插入“张三”方法插入成功。
     */
    @Test
    public void testHasTranHasInnerExpTryRequiresNew() {
        protogationTest.transaction_required_requiresNew_requiresNew_exception_try();
    }


    /**
     *
     * 条件：外部函数不加事务，两个子事务是support，并且在一个子事务中抛出异常
     * 结果：张三（插入），李四（插入）
     * 分析：外围方法未开启事务，插入“张三”、“李四”方法也均未开启事务，因为不存在事务所以无论外围方法或者内部方法抛出异常都不会回滚。
     */
    @Test
    public void testNotranHasInnerExpSupport() {
        protogationTest.notransaction_supports_supports_exception();
    }

    /**
     * 条件：外围方法未开启事务，两个子事务是support，并且在外部事务中抛出异常
     * 结果：张三（插入），李四（插入）</br>
     * 分析：外围方法未开启事务，插入“张三”、“李四”方法由于是support所以也均未开启事务，因为不存在事务所以无论外围方法或者内部方法抛出异常都不会回滚。
     */
    @Test
    public void testNotranHasExpSupports() {
        protogationTest.notransaction_exception_supports_supports();
    }

    /**
     * 条件：外部函数加事务，两个子事务是support，并且在一个子事务中抛出异常
     * 结果：张三（未插入），李四（未插入）</br>
     * 分析:外围方法开启事务，插入“张三”、“李四”方法都在外围方法的事务中运行，加入外围方法事务，所以三个方法同一个事务。外围方法或内部方法抛出异常，
     * 整个事务全部回滚。
     */
    @Test
    public void testHasTranHasInnerExptSupport() {
        protogationTest.transaction_supports_supports_exception();
    }

    /**
     * 条件：外部函数不加事务，两个子事务是support，并且在外部事务中抛出异常
     * 结果：张三（未插入），李四（未插入）</br>
     * 分析：外围方法开启事务，插入“张三”、“李四”方法都在外围方法的事务中运行，加入外围方法事务，所以三个方法同一个事务。外围方法或内部方法抛出异常，
     * 整个事务全部回滚。
     */
    @Test
    public void testTranHasExptSupports() {
        protogationTest.transaction_exception_supports_supports();
    }

    //---------------------------------------------------------------------------------
    //REQUIRES_NEW标注方法无论外围方法是否开启事务，内部REQUIRES_NEW方法均会开启独立事务，且和外围方法也不在同一个事务中，内部方法和外围方法、内部方法之间均不相互干扰。
    // 当外围方法不开启事务的时候，REQUIRED和REQUIRES_NEW标注的内部方法效果相同。
    // ---------------------------------------------------------------------------------
    // REQUIRED和SUPPORTS在外围方法支持事务的时候没有区别，均加入外围方法的事务中。
    // 当外围方法不支持事务，REQUIRED开启新的事务而SUPPORTS不开启事务。
    // REQUIRED的事务一定和外围方法事务统一。如果外围方法没有事务，每一个内部REQUIRED方法都会开启一个新的事务，互不干扰。
    //

    /**
     * 条件：外部函数不加事务，两个子事务一个是notSupport，另一个是required，并且在外部事务中抛出异常
     * 结果：张三（插入），李四（插入）</br>
     * 分析：外围方法未开启事务，插入“张三”方法在自己的事务中运行，插入“李四”方法不在任何事务中运行。外围方法抛出异常，但是外围方法没有事务，
     * 所以其他内部事务方法不会被回滚，非事务方法更不会被回滚。
     *
     */
    @Test
    public void testNoTranHasExptNotSupported() {
        protogationTest.notransaction_exception_required_notSuppored();
    }

    /**
     * 条件：外部函数不加事务，两个子事务一个是notSupport，另一个是required，并且在一个内部事务中抛出异常
     * 结果：张三（插入），李四（插入）</br>
     * 外围方法未开启事务，插入“张三”方法在自己的事务中运行，插入“李四”方法不在任何事务中运行。
     * 分析：插入“李四”方法抛出异常，首先因为插入“李四”方法没有开启事务，所以“李四”方法不会回滚，外围方法感知异常，但是因为外围方法没有事务，
     * 所有外围方法也不会回滚。
     *
     */
    @Test
    public void testNoTranHasInnerExptNotSupported() {
        protogationTest.notransaction_required_notSuppored_exception();
    }

    /**
     * 条件：外部函数加事务，两个子事务一个是notSupport，另一个是required，并且在外部事务中抛出异常
     * 结果：张三（未插入），李四（插入）</br>
     * 分析：外围方法开启事务，因为插入“张三”方法传播为required，所以和外围方法同一个事务。插入“李四”方法不在任何事务中运行。
     * 外围方法抛出异常，外围方法所在的事务将会回滚，因为插入“张三”方法和外围方法同一个事务，所以将会回滚。
     *
     */
    @Test
    public void testHasTranHasExptNotSupported() {
        protogationTest.transaction_exception_required_notSuppored();
    }

    /**
     * 条件：外部函数加事务，两个子事务一个是notSupport，另一个是required，并且在一个notSupport的内部事务中抛出异常
     * 结果：张三（未插入），李四（插入）</br>
     * 外围方法开启事务，因为插入“张三”方法传播为required，所以和外围方法同一个事务。插入“李四”方法不在任何事务中运行。
     * 分析：插入“李四”方法抛出异常，因为此方法不开启事务，所以此方法不会被回滚，外围方法接收到了异常，所以外围事务需要回滚，因插入“张三”
     * 方法和外围方法同一事务，故被回滚。
     *
     */
    @Test
    public void testHasTranHasInnerExptNotSupported() {
        protogationTest.transaction_required_notSuppored_exception();
    }

    // ---------------------------------------------------------------------------------
    // NOT_SUPPORTED明确表示不开启事务。
    // ---------------------------------------------------------------------------------



    private boolean confirm(){
        if(userMapper.selectAll().isEmpty()){
            return false;
        }
        if("张三".equals(userMapper.selectAll().get(0).getName())){
            return true;
        }
        return false;
    }
    private boolean confirm1(){
        if(user1Mapper.selectAll().isEmpty()){
            return false;
        }
        if("李四".equals(user1Mapper.selectAll().get(0).getName())){
            return true;
        }
        return false;
    }
}
