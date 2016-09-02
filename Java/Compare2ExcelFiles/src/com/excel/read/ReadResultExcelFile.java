package com.excel.read;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.read.biff.BiffException;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.excel.model.ReportHeader;
import com.excel.model.ReportModel;
import com.excel.model.ResultModel;
import com.excel.write.WriteXLS;

public class ReadResultExcelFile {

    private static final String COLUMN_POSTN = "Postn";
    private static final String COLUMN_CUSIP = "CUSIP";
    private static final String COLUMN_INSTMT_ID = "Instmt ID";
    private static final String COLUMN_TRADE_ID = "Trade ID";

    private static final String COLUMN_Notional = "Notional Amount";
    private static final String COLUMN_Struc = "Struc";
    private static final String COLUMN_Swptn_Type = "Swptn Type";
    private static final String COLUMN_Swptn_Setmt = "Swptn Setmt Date";
    private static final String COLUMN_Lockout = "Lockout End Date";
    private static final String COLUMN_Optn = "Optn Mty Date";
    private static final String COLUMN_Termination = "Termination Setmt_date";
    private static final String COLUMN_Expiration = "Expiration Date";
    private static final String COLUMN_Physical = "Physical Delivery date";
    private static final String COLUMN_Sec481 = "Sec 481 Basis";
    private static final String COLUMN_Cash_Paid = "Cash Paid/(Rec) to enter Swaptn";
    private static final String COLUMN_CY_MTM = "CY MTM";
    private static final String COLUMN_PY_MTM = "PY MTM";
    private static final String COLUMN_CY_MIN = "CY MTM Change";
    private static final String COLUMN_BOY_MIN = "BOY MTM Gain(Loss) to Amort";
    private static final String COLUMN_EOY_MIN = "EOY MTM gain(Loss) to Amort";
    private static final String COLUMN_BOY_CUM = "BOY Cum MTM Gain(Loss) Amort";
    private static final String COLUMN_CY_GAIN = "CY MTM Gain(Loss) Amort";
    private static final String COLUMN_EOY_Gain = "EOY Cum MTM Gain(Loss) Amort";
    private static final String COLUMN_EOY_Unrealize = "EOY Unrealized MTM Gain(Loss)";
    private static final String COLUMN_BOY_Tax = "BOY Tax Basis";
    private static final String COLUMN_EOY_Tax = "EOY Tax Basis";
    private static final String COLUMN_Termination_TAX = "Termination Tax Basis";
    private static final String COLUMN_Expiration_Tax = "Expiration Tax basis";
    private static final String COLUMN_Delivery = "Delivery Tax Basis";

    private List<ResultModel> listResultModels = new ArrayList<ResultModel>();

    private List<String> cellValue = null;

    private ReadResultExcelFile() {
    }

    private static final ReadResultExcelFile readResult = new ReadResultExcelFile();

    public static ReadResultExcelFile getInstance() {
        return readResult;
    }

    // Get data from Result excel file and store in ReportModel class.
    public void getResultExcelXLSData(File expectedResultExcelFile,
            Map<String, Integer> resultFileMapIndex) throws BiffException,
            IOException {

        Sheet sheet = ReadXLS.getInstance()
                .getSheet(expectedResultExcelFile, 0);

        for (int rows = 1; rows < sheet.getRows(); rows++) {
            listResultModels
                    .add(setResultModel(sheet, rows, resultFileMapIndex));
        }
        Collections.sort(listResultModels);
    }

    // Get data from Result excel file and store in ReportModel class.
    public void getResultExcelXLSXData(File resultExcelFile,
            Map<String, Integer> resultFileMapIndex) throws BiffException,
            IOException {

        XSSFSheet sheet = ReadXLSX.getInstance().getSheetXLSX(resultExcelFile,
                0);

        Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet
                .rowIterator();
        while (rowIterator.hasNext()) {
            org.apache.poi.ss.usermodel.Row rows = rowIterator.next();
            if (rows.getRowNum() == 0) {
                // Column Header part, skip it
                continue;
            }
            int index = resultFileMapIndex.get(COLUMN_POSTN);
            org.apache.poi.ss.usermodel.Cell cell = rows.getCell(index);
            cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            if (cell == null || cell.getStringCellValue().isEmpty()) {
                break; // Already read all Data.
            }

            ResultModel resultModel = new ResultModel();

            resultModel.setIndex(rows.getRowNum());

            resultModel.setPostn(cell.getStringCellValue());

            index = resultFileMapIndex.get(COLUMN_CUSIP);
            cell = rows.getCell(index);
            resultModel.setCusip(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_INSTMT_ID);
            cell = rows.getCell(index);
            resultModel.setInstmt_id(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_TRADE_ID);
            cell = rows.getCell(index);
            resultModel.setTrade_id(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Notional);
            cell = rows.getCell(index);
            resultModel.setNotionalAmount(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Struc);
            cell = rows.getCell(index);
            resultModel.setStruc(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Swptn_Type);
            cell = rows.getCell(index);
            resultModel.setSwptnType(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Swptn_Setmt);
            cell = rows.getCell(index);
            resultModel.setSwptnSetmtDate(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Lockout);
            cell = rows.getCell(index);
            resultModel.setLockoutEndDate(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Optn);
            cell = rows.getCell(index);
            resultModel.setOptnMtyDate(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Termination);
            cell = rows.getCell(index);
            resultModel.setTerminationSetmtDate(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Expiration);
            cell = rows.getCell(index);
            resultModel.setExpirationDate(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Physical);
            cell = rows.getCell(index);
            resultModel.setPhysicalDeliveryDate(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Sec481);
            cell = rows.getCell(index);
            resultModel.setSec481Basis(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Cash_Paid);
            cell = rows.getCell(index);
            resultModel.setCashPaidRecToEnterSwaptn(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_CY_MTM);
            cell = rows.getCell(index);
            resultModel.setCyMTM(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_PY_MTM);
            cell = rows.getCell(index);
            resultModel.setPyMTM(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_CY_MIN);
            cell = rows.getCell(index);
            resultModel.setCyMTMChange(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_BOY_MIN);
            cell = rows.getCell(index);
            resultModel.setBoyMTMGainToAmort(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_EOY_MIN);
            cell = rows.getCell(index);
            resultModel.setEoyMTMGainToAmort(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_BOY_CUM);
            cell = rows.getCell(index);
            resultModel.setBoyCumMTMGainAmort(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_CY_GAIN);
            cell = rows.getCell(index);
            resultModel.setCyMTMGainAmort(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_EOY_Gain);
            cell = rows.getCell(index);
            resultModel.setEoyCumMTMGainAmort(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_EOY_Unrealize);
            cell = rows.getCell(index);
            resultModel.setEoyUnrealizedMTMGain(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_BOY_Tax);
            cell = rows.getCell(index);
            resultModel.setBoyTaxBasis(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_EOY_Tax);
            cell = rows.getCell(index);
            resultModel.setEoyTaxBasis(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Termination_TAX);
            cell = rows.getCell(index);
            resultModel.setTerminationTaxBasis(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Expiration_Tax);
            cell = rows.getCell(index);
            resultModel.setExpirationTaxBasis(getCellValue(cell));

            index = resultFileMapIndex.get(COLUMN_Delivery);
            cell = rows.getCell(index);
            resultModel.setDeliveryTaxBasis(getCellValue(cell));

            listResultModels.add(resultModel);
        }
        Collections.sort(listResultModels);
    }

    private String getCellValue(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null) {
            return "";
        }
        cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
        switch (cell.getCellType()) {
        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
            return String.valueOf(cell.getNumericCellValue());
        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
            return cell.getStringCellValue();
        default:
            return "";
        }
    }

    private ResultModel setResultModel(Sheet sheet, int rows,
            Map<String, Integer> resultFileMapIndex) {
        ResultModel resultModel = new ResultModel();

        resultModel.setIndex(rows);

        int index = resultFileMapIndex.get(COLUMN_POSTN);
        Cell cell = sheet.getCell(index, rows);
        resultModel.setPostn(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_CUSIP);
        cell = sheet.getCell(index, rows);
        resultModel.setCusip(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_INSTMT_ID);
        cell = sheet.getCell(index, rows);
        resultModel.setInstmt_id(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_TRADE_ID);
        cell = sheet.getCell(index, rows);
        resultModel.setTrade_id(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Notional);
        cell = sheet.getCell(index, rows);
        resultModel.setNotionalAmount(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Struc);
        cell = sheet.getCell(index, rows);
        resultModel.setStruc(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Swptn_Type);
        cell = sheet.getCell(index, rows);
        resultModel.setSwptnType(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Swptn_Setmt);
        cell = sheet.getCell(index, rows);
        resultModel.setSwptnSetmtDate(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Lockout);
        cell = sheet.getCell(index, rows);
        resultModel.setLockoutEndDate(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Optn);
        cell = sheet.getCell(index, rows);
        resultModel.setOptnMtyDate(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Termination);
        cell = sheet.getCell(index, rows);
        resultModel.setTerminationSetmtDate(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Expiration);
        cell = sheet.getCell(index, rows);
        resultModel.setExpirationDate(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Physical);
        cell = sheet.getCell(index, rows);
        resultModel.setPhysicalDeliveryDate(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Sec481);
        cell = sheet.getCell(index, rows);
        resultModel.setSec481Basis(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Cash_Paid);
        cell = sheet.getCell(index, rows);
        resultModel.setCashPaidRecToEnterSwaptn(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_CY_MTM);
        cell = sheet.getCell(index, rows);
        resultModel.setCyMTM(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_PY_MTM);
        cell = sheet.getCell(index, rows);
        resultModel.setPyMTM(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_CY_MIN);
        cell = sheet.getCell(index, rows);
        resultModel.setCyMTMChange(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_BOY_MIN);
        cell = sheet.getCell(index, rows);
        resultModel.setBoyMTMGainToAmort(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_EOY_MIN);
        cell = sheet.getCell(index, rows);
        resultModel.setEoyMTMGainToAmort(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_BOY_CUM);
        cell = sheet.getCell(index, rows);
        resultModel.setBoyCumMTMGainAmort(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_CY_GAIN);
        cell = sheet.getCell(index, rows);
        resultModel.setCyMTMGainAmort(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_EOY_Gain);
        cell = sheet.getCell(index, rows);
        resultModel.setEoyCumMTMGainAmort(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_EOY_Unrealize);
        cell = sheet.getCell(index, rows);
        resultModel.setEoyUnrealizedMTMGain(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_BOY_Tax);
        cell = sheet.getCell(index, rows);
        resultModel.setBoyTaxBasis(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_EOY_Tax);
        cell = sheet.getCell(index, rows);
        resultModel.setEoyTaxBasis(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Termination_TAX);
        cell = sheet.getCell(index, rows);
        resultModel.setTerminationTaxBasis(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Expiration_Tax);
        cell = sheet.getCell(index, rows);
        resultModel.setExpirationTaxBasis(cell.getContents());

        index = resultFileMapIndex.get(COLUMN_Delivery);
        cell = sheet.getCell(index, rows);
        resultModel.setDeliveryTaxBasis(cell.getContents());

        return resultModel;
    }

    // Check all columns are there in Excel file or not.
    // Update Map<Integer, String> with Column index and Column name.
    public void preCheckExcelXLSFile(File excelFile,
            Map<String, Integer> resultFileMapIndex, boolean isReportExcelFIle)
            throws Exception {

        Sheet sheet = ReadXLS.getInstance().getSheet(excelFile, 0);

        ReportHeader reportHeader = ReportHeader.getInstance();

        // Get the all columns labels only
        for (int columns = 0; columns < sheet.getColumns(); columns++) {
            Cell cell = sheet.getCell(columns, 0); // Get Column Header only

            String columnName = cell.getContents();
            int index = columns;
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_POSTN.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_POSTN, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_CUSIP.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_CUSIP, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_INSTMT_ID.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_INSTMT_ID, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_TRADE_ID.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_TRADE_ID, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Notional.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Notional, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Struc.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Struc, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Swptn_Type.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Swptn_Type, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Swptn_Setmt.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Swptn_Setmt, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Lockout.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Lockout, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Optn.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Optn, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Termination.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Termination, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Expiration.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Expiration, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Physical.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Physical, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Sec481.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Sec481, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Cash_Paid.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Cash_Paid, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_CY_MIN.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_CY_MIN, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_CY_MTM.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_CY_MTM, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_PY_MTM.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_PY_MTM, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_BOY_MIN.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_BOY_MIN, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_EOY_MIN.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_EOY_MIN, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_BOY_CUM.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_BOY_CUM, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_CY_GAIN.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_CY_GAIN, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_EOY_Gain.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_EOY_Gain, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_EOY_Unrealize.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_EOY_Unrealize, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_BOY_Tax.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_BOY_Tax, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_EOY_Tax.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_EOY_Tax, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Termination_TAX.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Termination_TAX, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Expiration_Tax.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Expiration_Tax, index);
            }
            if (columnName.trim().toLowerCase()
                    .contains(COLUMN_Delivery.toLowerCase())) {
                resultFileMapIndex.put(COLUMN_Delivery, index);
            }

            if (isReportExcelFIle) {
                reportHeader.addReportHeaders(columnName);
            }
        }

        if (resultFileMapIndex.isEmpty()) {
            throw new Exception("All Columns in Excel files are not found.");
        }
    }

    // Check all columns are there in Excel file or not.
    // Update Map<Integer, String> with Column index and Column name.
    public void preCheckExcelXLSXFile(File excelFile,
            Map<String, Integer> resultFileMapIndex, boolean isReportExcelFIle)
            throws Exception {

        XSSFSheet sheet = ReadXLSX.getInstance().getSheetXLSX(excelFile, 0);

        ReportHeader reportHeader = ReportHeader.getInstance();

        Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet
                .rowIterator();
        while (rowIterator.hasNext()) {
            org.apache.poi.ss.usermodel.Row row = rowIterator.next();
            if (row.getRowNum() == 0) {
                // Column Header part
                Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row
                        .cellIterator();

                while (cellIterator.hasNext()) {
                    org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                    cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                    String columnName = cell.getStringCellValue();

                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_POSTN.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_POSTN,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_CUSIP.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_CUSIP,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_INSTMT_ID.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_INSTMT_ID,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_TRADE_ID.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_TRADE_ID,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Notional.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Notional,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Struc.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Struc,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Swptn_Type.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Swptn_Type,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Swptn_Setmt.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Swptn_Setmt,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Lockout.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Lockout,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Optn.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Optn,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Termination.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Termination,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Expiration.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Expiration,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Physical.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Physical,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Sec481.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Sec481,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Cash_Paid.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Cash_Paid,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_CY_MIN.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_CY_MIN,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_CY_MTM.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_CY_MTM,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_PY_MTM.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_PY_MTM,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_BOY_MIN.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_BOY_MIN,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_EOY_MIN.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_EOY_MIN,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_BOY_CUM.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_BOY_CUM,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_CY_GAIN.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_CY_GAIN,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_EOY_Gain.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_EOY_Gain,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_EOY_Unrealize.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_EOY_Unrealize,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_BOY_Tax.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_BOY_Tax,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_EOY_Tax.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_EOY_Tax,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Termination_TAX.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Termination_TAX,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Expiration_Tax.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Expiration_Tax,
                                cell.getColumnIndex());
                    }
                    if (columnName.trim().toLowerCase()
                            .contains(COLUMN_Delivery.toLowerCase())) {
                        resultFileMapIndex.put(COLUMN_Delivery,
                                cell.getColumnIndex());
                    }

                    if (isReportExcelFIle) {
                        reportHeader.addReportHeaders(columnName);
                    }
                }
                break;
            }
        }

        if (resultFileMapIndex.isEmpty()) {
            throw new Exception("All Columns in Excel files are not found.");
        }
    }

    // Get Data from Report excel file and match with Result excel file
    // and if match, copy Report Data into new Excel file.
    public void generateExcelXLSXFromReportData(File reportExcelFile,
            Map<String, Integer> reportFileMapIndex, File resultExcelFile)
            throws Exception {

        XSSFSheet sheet = ReadXLSX.getInstance().getSheetXLSX(reportExcelFile,
                0);
        int rowNumber = 1;

        // Iterate through each rows from first sheet
        Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet
                .rowIterator();
        while (rowIterator.hasNext()) {
            org.apache.poi.ss.usermodel.Row row = rowIterator.next();
            if (row.getRowNum() == 0) {
                // Column Header part, skip it
                continue;
            }

            ReportModel reportModel = new ReportModel();

            int index = reportFileMapIndex.get(COLUMN_POSTN);
            org.apache.poi.ss.usermodel.Cell cell = row.getCell(index);
            cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            if (cell == null || cell.getStringCellValue().isEmpty()) {
                break; // All data processing done.
            }
            reportModel.setPostn(getCellValue(cell));

            index = reportFileMapIndex.get(COLUMN_CUSIP);
            cell = row.getCell(index);
            reportModel.setCusip(getCellValue(cell));

            index = reportFileMapIndex.get(COLUMN_INSTMT_ID);
            cell = row.getCell(index);
            reportModel.setInstmt_id(getCellValue(cell));

            index = reportFileMapIndex.get(COLUMN_TRADE_ID);
            cell = row.getCell(index);
            reportModel.setTrade_id(Long.parseLong(getCellValue(cell)));

            boolean isMatch = listResultModels.contains(reportModel);
            if (isMatch) {
                // Write total row in new file which is match to Result excel
                // file
                ResultModel resultModel = null;
                for (Iterator<ResultModel> iterator = listResultModels
                        .iterator(); iterator.hasNext();) {
                    resultModel = (ResultModel) iterator.next();
                    if (resultModel.getIndex() == ReportModel.MATCHED_INDEX) {
                        break;
                    }
                }
                if (resultModel == null) {
                    // Not found actual object
                    throw new Exception("Parder Result excel data error.");
                }

                cellValue = new ArrayList<String>();
                XSSFSheet sheet_result = ReadXLSX.getInstance().getSheetXLSX(
                        resultExcelFile, 0);

                Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row
                        .cellIterator();

                int columnIndex = 0;
                while (cellIterator.hasNext()) {

                    org.apache.poi.ss.usermodel.Cell reportCellData = cellIterator
                            .next();
                    reportCellData
                            .setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);

                    org.apache.poi.ss.usermodel.Row result_row = sheet_result
                            .getRow(resultModel.getIndex());

                    org.apache.poi.ss.usermodel.Cell resultCellData = result_row
                            .getCell(columnIndex);
                    resultCellData
                            .setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);

                    String reportCellContent = getCellValue(reportCellData);
                    String resultCellContent = getCellValue(resultCellData);
                    if (columnIndex > 3) {
                        // Compare two String here
                        if (reportCellContent
                                .equalsIgnoreCase(resultCellContent)) {
                            addReportData("true");
                        } else {
                            // Re-check the value again between two cell values
                            boolean valueMatch = compareCellValues(
                                    reportCellContent, resultCellContent);
                            if (valueMatch) {
                                addReportData("true");
                            } else {
                                addReportData("false " + "Report: "
                                        + reportCellContent + " Result: "
                                        + resultCellContent);
                            }
                        }
                    } else {
                        addReportData(reportCellContent);
                    }

                    columnIndex++;
                }

                WriteXLS.getInstance().createContent(
                        WriteXLS.getInstance().getExcelSheet(), rowNumber++,
                        getReportData());
            }
        }

    }

    private boolean compareCellValues(String reportCellContent,
            String resultCellContent) {

        reportCellContent = reportCellContent.replace(",", "");
        resultCellContent = resultCellContent.replace(",", "");
        try {
            double reportValue = Double.parseDouble(reportCellContent);
            double resultValue = Double.parseDouble(resultCellContent);
            long reportVal = (long) reportValue;
            long resultVal = (long) resultValue;
            if (reportVal == resultVal) {
                return true;
            }
            return false;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    // Get Data from Report excel file and match with Result excel file
    // and if match, copy Report Data into new Excel file.
    public void generateExcelFromReportData(File reportExcelFile,
            File resultExcelFile, Map<String, Integer> reportFileMapIndex)
            throws Exception {

        Sheet sheet = ReadXLS.getInstance().getSheet(reportExcelFile, 0);
        int rowNumber = 1;

        for (int rows = 1; rows < sheet.getRows(); rows++) {
            ReportModel reportModel = new ReportModel();

            int index = reportFileMapIndex.get(COLUMN_POSTN);
            Cell cell = sheet.getCell(index, rows);
            reportModel.setPostn(cell.getContents());

            index = reportFileMapIndex.get(COLUMN_CUSIP);
            cell = sheet.getCell(index, rows);
            reportModel.setCusip(cell.getContents());

            index = reportFileMapIndex.get(COLUMN_INSTMT_ID);
            cell = sheet.getCell(index, rows);
            reportModel.setInstmt_id(cell.getContents());

            index = reportFileMapIndex.get(COLUMN_TRADE_ID);
            cell = sheet.getCell(index, rows);
            reportModel.setTrade_id(Long.parseLong(cell.getContents()));

            boolean isMatch = listResultModels.contains(reportModel);
            if (isMatch) {
                ResultModel resultModel = null;
                for (Iterator<ResultModel> iterator = listResultModels
                        .iterator(); iterator.hasNext();) {
                    resultModel = (ResultModel) iterator.next();
                    if (resultModel.getIndex() == ReportModel.MATCHED_INDEX) {
                        break;
                    }
                }
                if (resultModel == null) {
                    // Not found actual object
                    throw new Exception("Parder Result excel data error.");
                }

                // Write total row from Report excel file to new file which is
                // match
                // to Result excel file
                cellValue = new ArrayList<String>();
                Sheet sheet_result = ReadXLS.getInstance().getSheet(
                        resultExcelFile, 0);

                for (int column = 0; column < sheet.getColumns(); column++) {
                    Cell reportCellData = sheet.getCell(column, rows);
                    Cell resultCellData = sheet_result.getCell(column,
                            resultModel.getIndex());

                    String reportCellContent = reportCellData.getContents();
                    String resultCellContent = resultCellData.getContents();
                    if (column > 3) {
                        // Need to compare
                        if (reportCellContent
                                .equalsIgnoreCase(resultCellContent)) {
                            addReportData("true");
                        } else {
                            // Re-check the value again between two cell values
                            boolean valueMatch = compareCellValues(
                                    reportCellContent, resultCellContent);
                            if (valueMatch) {
                                addReportData("true");
                            } else {
                                addReportData("false " + "Report: "
                                        + reportCellContent + " Result: "
                                        + resultCellContent);
                            }
                        }
                    } else
                        addReportData(reportCellContent);
                }
                WriteXLS.getInstance().createContent(
                        WriteXLS.getInstance().getExcelSheet(), rowNumber++,
                        getReportData());
            }
        }
    }

    // Add all row data in a variable
    private void addReportData(String cellData) {
        cellValue.add(cellData);
    }

    // Return full row value
    private String[] getReportData() {
        String[] cellData = new String[cellValue.size()];
        cellData = cellValue.toArray(cellData);
        return cellData;
    }

    public List<ResultModel> getListResultModels() {
        return listResultModels;
    }
}
