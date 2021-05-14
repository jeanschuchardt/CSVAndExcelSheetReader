package com.jb.readCSVandExcel.service.other;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AvgService {

    public void init() throws IOException {
        List<Integer> numbers = readFile();
        float result = calcAvg(numbers);
        System.out.println(result);

    }
    public List<Integer> readFile() throws FileNotFoundException {
        List<Integer> numbers = new ArrayList<>();
        String line = "";
        try {
            FileReader fr = new FileReader("src/main/resources/numbers.txt");
            BufferedReader br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                for (int i = 0; i <= line.length() - 2; i+=2) {
                    char[] chars = line.toCharArray();
                    int number = Integer.parseInt(String.valueOf(chars[i]) + String.valueOf(chars[i+1]));
                    numbers.add(number);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return numbers;
    }

    public float calcAvg(List<Integer> numbers) throws IOException {
        int sum = 0;
        for (int number: numbers){
            sum+= number;

        }
        return sum/(numbers.size());
    }


}
