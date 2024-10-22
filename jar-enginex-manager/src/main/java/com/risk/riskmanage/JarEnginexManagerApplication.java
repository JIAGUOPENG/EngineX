package com.risk.riskmanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.risk.riskmanage.*.mapper")
@ComponentScan(basePackages = "com.risk.riskmanage.**")
public class JarEnginexManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JarEnginexManagerApplication.class, args);
    }

}
