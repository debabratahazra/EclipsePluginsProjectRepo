package com.odcgroup.translation.generation.tests;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelSheetReader {

	private List<List<HSSFCell>> cellDataList = null;
	private FileInputStream fileInputStream = null;
	private POIFSFileSystem fsFileSystem = null;

	/**
	 * This method is used to read the data's from an excel file.
	 * 
	 * @param fileName
	 *            - Name of the excel file.
	 */
	public void readExcelFile(String fileName) {
		/**
		 * Create a new instance for cellDataList
		 */
		cellDataList = new ArrayList<List<HSSFCell>>();
		try {
			/**
			 * Create a new instance for FileInputStream class
			 */
			fileInputStream = new FileInputStream(fileName);

			/**
			 * Create a new instance for POIFSFileSystem class
			 */
			fsFileSystem = new POIFSFileSystem(fileInputStream);

			/*
			 * Create a new instance for HSSFWorkBook Class
			 */
			HSSFWorkbook workBook = new HSSFWorkbook(fsFileSystem);
			HSSFSheet hssfSheet = workBook.getSheetAt(0);

			/**
			 * Iterate the rows and cells of the spreadsheet to get all the
			 * datas.
			 */
			@SuppressWarnings("unchecked")
			Iterator<HSSFRow> rowIterator = hssfSheet.rowIterator();

			while (rowIterator.hasNext()) {
				HSSFRow hssfRow = rowIterator.next();
				@SuppressWarnings("unchecked")
				Iterator<HSSFCell> iterator = hssfRow.cellIterator();
				List<HSSFCell> cellTempList = new ArrayList<HSSFCell>();
				while (iterator.hasNext()) {
					HSSFCell hssfCell = (HSSFCell) iterator.next();
					cellTempList.add(hssfCell);
				}
				cellDataList.add(cellTempList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * Call the printToConsole method to print the cell data in the console.
		 */
	}

	public String getContent(int[] rowColumn) {
		String result = null;
		List<HSSFCell> cellTempList = (List<HSSFCell>) cellDataList.get(rowColumn[0]);
		HSSFCell hssfCell = (HSSFCell) cellTempList.get(rowColumn[1]);
		result = hssfCell.toString();
		return result;
	}

	public void close() {
		try {
			fileInputStream.close();
			fileInputStream = null;
			fsFileSystem = null;
			cellDataList = null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
