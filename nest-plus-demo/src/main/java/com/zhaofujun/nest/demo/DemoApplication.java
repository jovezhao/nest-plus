package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.utils.LockUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DemoApplication   {

    public static int[] i = {0};

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }



}



