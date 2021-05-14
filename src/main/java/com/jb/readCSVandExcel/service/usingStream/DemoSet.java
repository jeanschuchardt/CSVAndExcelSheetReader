package com.jb.readCSVandExcel.service.usingStream;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class DemoSet {

    public void init() {
        useSet();
    }

    public void useSet() {
        Set<String> myset = new HashSet<>();
        myset.add("oi");
        myset.add("test");
        myset.add("a");
        myset.add("bdfsd");
        myset.add("c");
        myset.add("d");
        myset.add("e");

        int index = 0;


        AtomicInteger ai = new AtomicInteger();
        List<Set<String>> myList = new ArrayList<>(
                myset.stream()
                        .collect(Collectors.groupingBy(
                                s -> ai.getAndIncrement() / 2,
                                Collectors.toSet()))
                        .values());


    }
}
