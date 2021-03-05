package com.rest.soap.wrapper;

import com.rest.soap.wrapper.calcimpl.CalculatorServiceInterface;
import com.rest.soap.wrapper.entitiy.ParentLogEnt;
import com.rest.soap.wrapper.repository.ParentLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Autowired
    @Qualifier("calc2")
    CalculatorServiceInterface calculatorService;

    @Autowired
    ParentLogRepository parentLogRepository;

    @Override
    public void run(String... args) throws Exception {
//        System.out.println(calculatorService.add(1, 2));
//        System.out.println(calculatorService.subtract(5,1));
//        System.out.println(calculatorService.multiply(5,2));
//        System.out.println(calculatorService.divide(10,3));
//        ParentLogEnt logEnt = new ParentLogEnt();
//        logEnt.setInsertDate(new Date());
//        parentLogRepository.save(logEnt);
    }
}
