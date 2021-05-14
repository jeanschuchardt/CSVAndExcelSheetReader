package com.jb.readCSVandExcel;

import com.jb.readCSVandExcel.service.other.AvgService;
import com.jb.readCSVandExcel.service.usingStream.DemoSet;
import com.jb.readCSVandExcel.service.excelAndCsv.ReadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    DemoSet demoSet;

    @Autowired
    ReadFileService readFileService;
    @Autowired
    AvgService avgService;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//
//        context.getBean(ReadFileService.class);
    }

    @Override
    public void run(String... args) throws IOException {
        //readFileService.init();
        //readFileService.excell();
        //avgService.init();
        demoSet.init();

    }

}
