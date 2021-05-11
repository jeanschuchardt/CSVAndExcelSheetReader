package com.jb.readCSVandExcel;

import com.jb.readCSVandExcel.service.ReadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    ReadFileService readFileService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//
//        context.getBean(ReadFileService.class);
    }

    @Override
    public void run(String... args) {
        readFileService.init();
        //readFileService.excell();
    }

}
