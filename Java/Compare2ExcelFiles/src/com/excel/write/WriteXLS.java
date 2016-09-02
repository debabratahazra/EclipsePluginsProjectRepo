package com.excel.write;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.excel.model.ReportHeader;

public class WriteXLS {

    private static final WriteXLS write = new WriteXLS();

    private WriteXLS() {
    }

    public static WriteXLS getInstance() {
        return write;
    }

    private static final String SHEET_NAME = "Final Report";

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private String inputFile;
    private WritableSheet excelSheet;
    private WritableWorkbook workbook;

    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void writeColumnLabels() throws IOException, WriteException {
        File file = new File(inputFile);
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet(SHEET_NAME, 0);
        excelSheet = workbook.getSheet(0);

        ReportHeader header = ReportHeader.getInstance();
        createLabel(excelSheet, header.getReportHeader());
    }

    // Finally close the excel file
    public void close() throws Exception {
        workbook.write();
        workbook.close();
    }

    public void createLabel(WritableSheet sheet, String... labels)
            throws WriteException {
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);

        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(
                WritableFont.TIMES, 10, WritableFont.BOLD, false);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        cv.setAutosize(true);

        // Write a headers in new excel file
        for (int columnIndex = 0; columnIndex < labels.length; columnIndex++) {
            String labelName = labels[columnIndex];
            addCaption(sheet, columnIndex, 0, labelName);
        }
    }

    // Write row data in new excel file
    public void createContent(WritableSheet sheet, int rowNumber,
            String... contents) throws WriteException, RowsExceededException {

        // Write whole row data to new excel file
        for (int j = 0; j < contents.length; j++) {
            String string = contents[j];
            addRowData(sheet, j, rowNumber, string);
        }
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    private void addRowData(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }

    public WritableSheet getExcelSheet() {
        return excelSheet;
    }
}