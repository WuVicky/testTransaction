package com.transaction.test;

import com.transaction.test.util.SpringUtil;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication(scanBasePackages = "com.transaction.test.*")
@MapperScan("com.transaction.test.dao")
public class Application {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
//
//    applicationContext = SpringApplication.run(Application.class);
//    SpringUtil.setApplicationContextStat(applicationContext);

    }
}
