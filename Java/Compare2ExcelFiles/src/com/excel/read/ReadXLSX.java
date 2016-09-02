package com.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.excel.write.WriteXLS;

public class ReadXLSX {

    private Map<String, Integer> reportFileMapIndex = new HashMap<String, Integer>();
    private Map<String, Integer> resultFileMapIndex = new HashMap<String, Integer>();

    private String reportFileName;
    private String resultFileName;

    private static final ReadXLSX readXlsx = new ReadXLSX();

    public void setInputFile(String inputFile, String expectedResultFile) {
        this.reportFileName = inputFile;
        this.resultFileName = expectedResultFile;
    }

    private ReadXLSX() {
    }

    public static ReadXLSX getInstance() {
        return readXlsx;
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

        ReadResultExcelFile.getInstance().preCheckExcelXLSXFile(
                reportExcelFile, reportFileMapIndex, true);
        ReadResultExcelFile.getInstance().preCheckExcelXLSXFile(
                resultExcelFile, resultFileMapIndex, false);

        ReadResultExcelFile.getInstance().getResultExcelXLSXData(
                resultExcelFile, resultFileMapIndex);
        writeColumnHeader();
        ReadResultExcelFile.getInstance().generateExcelXLSXFromReportData(
                reportExcelFile, reportFileMapIndex, resultExcelFile);

    }

    // Write Heading in new excel file
    private void writeColumnHeader() throws WriteException, IOException {
        WriteXLS.getInstance().writeColumnLabels();
    }

    // Get the specific Sheet from Excel file
    public XSSFSheet getSheetXLSX(File excelFile, int sheetNumber)
            throws BiffException, IOException {

        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));

        // Get first sheet from the workbook
        XSSFSheet sheet = wb.getSheetAt(sheetNumber);
        return sheet;
    }
}
