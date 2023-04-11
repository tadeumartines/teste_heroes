package br.com.gubee.interview.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
//@EnableTransactionManagement
@EntityScan(basePackages = {"br.com.gubee.interview.model"})
public class Application {

    public static void main(String[] args) {
        
        SpringApplication.run(Application.class, args);
    }
}


