package com.transaction.test.service.impl;

import com.transaction.test.entry.User;
import com.transaction.test.entry.User1;
import com.transaction.test.service.TransactionProtogationTest;
import com.transaction.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TransactionProtogationTestImpl implements TransactionProtogationTest {
    @Autowired
    private com.transaction.test.service.UserService userService;
    @Resource
    private com.transaction.test.service.User1Service user1Service;


    @Override
    public void truncated() {
        userService.truncate();
        user1Service.truncate();
    }


    @Override
    public void notransaction_exception_notransaction_notransaction(){
        User user=new User();
        user.setName("张三");
        userService.add(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.add(user1);
        throw new RuntimeException();
    }


    @Override
    public void notransaction_notransaction_notransaction_exception(){
        User user=new User();
        user.setName("张三");
        userService.add(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addException(user1);
    }

    @Override
    @Transactional
    public void transaction_exception_notransaction_notransaction(){
        User user=new User();
        user.setName("张三");
        userService.add(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.add(user1);
        throw new RuntimeException();
    }

    /**
     * 对User数据源开启事务
     */
    @Override
    @Transactional(value="UserTM")
    public void transaction_exception_notransaction_notransaction_user(){
        User user=new User();
        user.setName("张三");
        userService.add(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.add(user1);
        throw new RuntimeException();
    }

    /**
     * 对User1数据源开启事务
     */
    @Override
    @Transactional(value="User1TM")
    public void transaction_exception_notransaction_notransaction_user1(){
        User user=new User();
        user.setName("张三");
        userService.add(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.add(user1);
        throw new RuntimeException();
    }


    @Override
    @Transactional
    public void transaction_notransaction_notransaction_exception(){
        User user=new User();
        user.setName("张三");
        userService.add(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addException(user1);
    }

    /**
     * 没有事务注解。
     */
    @Override
    public void notransaction_required_required(){
        User user=new User();
        user.setName("张三");
        userService.addRequired(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addRequired(user1);
    }

    /**
     * 方法本身抛出异常
     */
    @Override
    public void notransaction_exception_required_required(){
        User user=new User();
        user.setName("张三");
        userService.addRequired(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addRequired(user1);

        throw new RuntimeException();
    }


    /**
     * 调用方法抛出异常
     */
    @Override
    public void notransaction_required_required_exception(){
        User user=new User();
        user.setName("张三");
        userService.addRequired(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addRequiredException(user1);
    }

    /**
     * 方法本身抛出异常
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_exception_required_required(){
        User user=new User();
        user.setName("张三");
        userService.addRequired(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addRequired(user1);

        throw new RuntimeException();
    }


    /**
     * 调用方法抛出异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transaction_required_required_exception(){
        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addRequiredException(user1);

        User user=new User();
        user.setName("张三");
        userService.addRequired(user);

    }


    @Override
    public void notransaction_supports_supports_exception(){
        User1 user=new User1();
        user.setName("张三");
        user1Service.addSupports(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addSupportsException(user1);
    }

    @Override
    public void notransaction_exception_supports_supports(){
        User1 user=new User1();
        user.setName("张三");
        user1Service.addSupports(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addSupports(user1);
        throw new RuntimeException();

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_supports_supports_exception(){
        User user=new User();
        user.setName("张三");
        userService.addSupports(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addSupportsException(user1);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_exception_supports_supports(){
        User user=new User();
        user.setName("张三");
        userService.addSupports(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addSupports(user1);

        throw new RuntimeException();
    }



    @Override
    public void notransaction_requiresNew_requiresNew_exception(){
        User user=new User();
        user.setName("张三");
        userService.addRequiresNew(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addRequiresNewException(user1);
    }

    @Override
    public void notransaction_exception_requiresNew_requiresNew(){
        User user=new User();
        user.setName("张三");
        userService.addRequiresNew(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addRequiresNew(user1);
        throw new RuntimeException();

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_required_requiresNew_requiresNew_exception(){
        User user=new User();
        user.setName("张三");
        userService.addRequired(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addRequiresNew(user1);

        User1 user3=new User1();
        user3.setName("王五");
        user1Service.addRequiresNewException(user3);
    }

    @Transactional
    @Override
    public void transaction_required_required_exception_try() {
        User user = new User();
        user.setName("张三");
        userService.addRequired(user);

        User1 user2 = new User1();
        user2.setName("李四");
        try {
            user1Service.addRequiredException(user2);
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_required_requiresNew_requiresNew_exception_try(){
        User user=new User();
        user.setName("张三");
        userService.addRequired(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addRequiresNew(user1);
        User1 user2=new User1();
        user2.setName("王五");
        try {
            user1Service.addRequiresNewException(user2);
        } catch (Exception e) {
            System.out.println("回滚");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_exception_required_requiresNew_requiresNew(){
        User user=new User();
        user.setName("张三");
        userService.addRequired(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addRequiresNew(user1);

        User1 user3=new User1();
        user3.setName("王五");
        user1Service.addRequiresNew(user3);

        throw new RuntimeException();
    }


    @Override
    public void notransaction_exception_required_notSuppored(){
        User user=new User();
        user.setName("张三");
        userService.addRequired(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addNotSupported(user1);
        throw new RuntimeException();
    }

    @Override
    public void notransaction_required_notSuppored_exception(){
        User user=new User();
        user.setName("张三");
        userService.addRequired(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addNotSupportedException(user1);
    }

    @Transactional
    @Override
    public void transaction_exception_required_notSuppored(){
        User user=new User();
        user.setName("张三");
        userService.addRequired(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addNotSupported(user1);
        throw new RuntimeException();
    }

    @Transactional
    @Override
    public void transaction_required_notSuppored_exception(){
        User user=new User();
        user.setName("张三");
        userService.addRequired(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addNotSupportedException(user1);
    }


    @Override
    public void notransaction_mandatory(){
        User user=new User();
        user.setName("张三");
        userService.addMandatory(user);
    }

    @Transactional
    @Override
    public void transaction_exception_mandatory_mandatory(){
        User user=new User();
        user.setName("张三");
        userService.addMandatory(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addMandatory(user1);
        throw new RuntimeException();
    }


    @Transactional
    @Override
    public void transaction_mandatory_mandatory_exception(){
        User user=new User();
        user.setName("张三");
        userService.addMandatory(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addMandatoryException(user1);
    }



    @Override
    public void notransaction_exception_never_never(){
        User user=new User();
        user.setName("张三");
        userService.addNever(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addNever(user1);
        throw new RuntimeException();
    }

    @Override
    public void notransaction_never_never_exception(){
        User user=new User();
        user.setName("张三");
        userService.addNever(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addNeverException(user1);
    }

    @Transactional
    @Override
    public void transaction_never(){
        User user=new User();
        user.setName("张三");
        userService.addNever(user);
    }


    @Transactional
    @Override
    public void transaction_exception_nested_nested(){
        User user=new User();
        user.setName("张三");
        userService.addNested(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addNested(user1);
        throw new RuntimeException();
    }

    @Transactional
    @Override
    public void transaction_nested_nested_exception(){
        User user=new User();
        user.setName("张三");
        userService.addNested(user);

        User1 user1=new User1();
        user1.setName("李四");
        user1Service.addNestedException(user1);
    }

    @Transactional
    @Override
    public void transaction_nested_nested_exception_try(){
        User user=new User();
        user.setName("张三");
        userService.addNested(user);

        User1 user1=new User1();
        user1.setName("李四");
        try {
            user1Service.addNestedException(user1);
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }

//    @Transactional
//    @Override
//    public void transaction_required_required_exception_try(){
//        User user=new User();
//        user.setName("张三");
//        userService.addRequired(user);
//
//        User1 user1=new User1();
//        user1.setName("李四");
//        try {
//            user1Service.addRequiredException(user1);
//        } catch (Exception e) {
//            System.out.println("方法回滚");
//        }
//    }

    @Transactional
    @Override
    public void transaction_noTransaction_noTransaction_exception_try(){
        User user=new User();
        user.setName("张三");
        userService.add(user);

        User1 user1=new User1();
        user1.setName("李四");
        try {
            user1Service.addException(user1);
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }
}
