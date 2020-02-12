package com.redhat.springbootconfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan(basePackages = {"com.redhat.springbootconfiguration.*"})
//@SpringBootConfiguration
public class Application {

 //   public static void main(String[] args) {
 //       SpringApplication.run(Application.class, args);
 //   }

    @Bean
    public PersonService personService() {
        return new PersonServiceImpl();
    }
}