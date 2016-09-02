package com.excel.read;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import com.excel.write.WriteXLS;

public class ReadXLS {

    private String reportFileName;
    private String resultFileName;

    private Map<String, Integer> resultFileMapIndex = new HashMap<String, Integer>();
    private Map<String, Integer> reportFileMapIndex = new HashMap<String, Integer>();

    private static final ReadXLS read = new ReadXLS();

    private ReadXLS() {
    }

    public void setInputFile(String inputFile, String expectedResultFile) {
        this.reportFileName = inputFile;
        this.resultFileName = expectedResultFile;
    }

    public static ReadXLS getInstance() {
        return read;
    }

    // Read both input excel files and
    // Write matched data in new excel file
    public void readAndWrite() throws Exception {
        File reportExcelFile = new File(reportFileName);
        if (!reportExcelFile.exists()) {
            throw new FileNotFoundException(
                    "Report Excel file not found in path.");
        }

        File resultExcelFile = new File(resultFileName);
        if (!resultExcelFile.exists()) {
            throw new FileNotFoundException(
                    "Expected Result Excel file not found in path.");
        }

        ReadResultExcelFile.getInstance().preCheckExcelXLSFile(reportExcelFile,
                reportFileMapIndex, true);
        ReadResultExcelFile.getInstance().preCheckExcelXLSFile(resultExcelFile,
                resultFileMapIndex, false);

        ReadResultExcelFile.getInstance().getResultExcelXLSData(
                resultExcelFile, resultFileMapIndex);
        writeColumnHeader();
        ReadResultExcelFile.getInstance().generateExcelFromReportData(
                reportExcelFile, resultExcelFile, reportFileMapIndex);

    }

    // Write Heading in new excel file
    private void writeColumnHeader() throws WriteException, IOException {
        WriteXLS.getInstance().writeColumnLabels();
    }

    // Get the specific Sheet from Excel file
    public Sheet getSheet(File excelFile, int sheetNumber)
            throws BiffException, IOException {

        Workbook reportWorkBook;

        reportWorkBook = Workbook.getWorkbook(excelFile);

        // Get the first sheet
        return reportWorkBook.getSheet(sheetNumber);
    }
}