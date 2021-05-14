package com.jb.readCSVandExcel.service.excelAndCsv;

import com.opencsv.CSVReader;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Component
public class ReadFileService {

    private static final String DEFAULT_CSV_SEPARATOR = ",";

    public ReadFileService() {
        System.out.println("constructor");

    }

    public void init() {
        System.out.println("init");
        File initialFile = new File("C:\\Users\\jeans\\Desktop\\Parser examples\\csv.inventory_import_sample.csv");
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(initialFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getWorkbook(inputStream, "csv");

        Workbook workbook = new XSSFWorkbook();


    }

    public void excell() {

        File initialFile = new File("C:\\Users\\jeans\\Desktop\\Parser examples\\inventory_import_sample.xlsx");
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(initialFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Workbook xlsx = getWorkbook(inputStream, "xlsx");
        readWorkbook(xlsx);
        // new XSSFWorkbook(inputStream);

    }

    private void readWorkbook(Workbook workbook) {
        Sheet datatypeSheet = workbook.getSheetAt(0); // pega a planilha
        Iterator<Row> iterator = datatypeSheet.iterator(); //pega as linhas

        while (iterator.hasNext()) {

            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();

            while (cellIterator.hasNext()) {

                Cell currentCell = cellIterator.next();
                //getCellTypeEnum shown as deprecated for version 3.15
                //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                if (currentCell.getCellType() == CellType.STRING) {
                    System.out.print(currentCell.getStringCellValue() + "--");
                } else if (currentCell.getCellType() == CellType.NUMERIC) {
                    System.out.print(currentCell.getNumericCellValue() + "--");

                }

            }
            System.out.println();

        }
    }

    @SneakyThrows
    private Workbook getWorkbook(InputStream inputStream, String excelType) {
        System.out.println("test");
        Workbook workbook = null;
        CSVReader file = null;
        List<HashMap<String, Object>> listProducts = new ArrayList<>();
        if ("xls".equalsIgnoreCase(excelType)) {

            // workbook = new HSSFWorkbook(inputStream);
        } else if (excelType.equalsIgnoreCase("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelType.equalsIgnoreCase("csv")) {
            //TODO:  create something that brings and CSV return

            // Prepare.
            BufferedReader csvReader = null;
            List<List<String>> csvList = new ArrayList<List<String>>();
            String csvRecord = null;

            // Process records.
            try {
                csvReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                while ((csvRecord = csvReader.readLine()) != null) {
                    csvList.add(parseCsvRecord(csvRecord, ','));
                }

            } catch (IOException e) {
                throw new RuntimeException("Reading CSV failed.", e);
            } finally {
                if (csvReader != null)
                    try {
                        csvReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            workbook = createWorkBook(csvList);
        }

        return workbook;
    }

    private Workbook createWorkBook(List<List<String>> csvList) {
        Workbook workBook = new XSSFWorkbook();
        Sheet sheet = workBook.createSheet("data");
        int currentRowNumber = 0;

        List<String> headerList = csvList.get(0);
        csvList.remove(0);

        Row header = sheet.createRow(currentRowNumber);

        for (int i = 0; i < headerList.size() - 1; i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(headerList.get(i));

        }

        for (List item : csvList) {
            currentRowNumber++;
            Row line = sheet.createRow(currentRowNumber);
            for (int i = 0; i < headerList.size() - 1; i++) {
                //for (Object column : item) {
                Cell lineCell = line.createCell(i);
                lineCell.setCellValue((String) item.get(i));
            }
        }
        return workBook;
    }

    private static List<String> parseCsvRecord(String record, char csvSeparator) {

        // Prepare.
        boolean quoted = false;
        StringBuilder fieldBuilder = new StringBuilder();
        List<String> fields = new ArrayList<String>();

        // Process fields.
        for (int i = 0; i < record.length(); i++) {
            char c = record.charAt(i);
            fieldBuilder.append(c);

            if (c == '"') {
                quoted = !quoted; // Detect nested quotes.
            }

            if ((!quoted && c == csvSeparator) // The separator ..
                    || i + 1 == record.length()) // .. or, the end of record.
            {
                String field = fieldBuilder.toString() // Obtain the field, ..
                        .replaceAll(csvSeparator + "$", "") // .. trim ending separator, ..
                        .replaceAll("^\"|\"$", "") // .. trim surrounding quotes, ..
                        .replace("\"\"", "\""); // .. and un-escape quotes.
                fields.add(field.trim()); // Add field to List.
                fieldBuilder = new StringBuilder(); // Reset.
            }
        }

        return fields;
    }
}
