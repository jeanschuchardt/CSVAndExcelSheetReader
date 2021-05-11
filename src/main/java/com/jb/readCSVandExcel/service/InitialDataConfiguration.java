package com.jb.readCSVandExcel.service;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class InitialDataConfiguration {

    @PostConstruct
    public void postConstruct() {
        System.out.println("Started after Spring boot application !");
    }

}