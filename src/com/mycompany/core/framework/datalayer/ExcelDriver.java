package com.mycompany.core.framework.datalayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mycompany.core.framework.utils.ExcelReader;


/**
 * Driver class that extracts the data from "xls" or "xlsx" file
 * 
 * @author UST-TACOE
 * 
 */
public class ExcelDriver {

	ExcelReader excelReader = null;

	public ExcelDriver(String filePath) {
		this(new File(filePath));
	}

	public ExcelDriver(File file) {
		try {
			excelReader = new ExcelReader(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ExcelDriver(String filePath, int sheetNo) {
		this(new File(filePath), sheetNo);
	}

	public ExcelDriver(File file, int sheetNo) {
		try {
			excelReader = new ExcelReader(file, sheetNo);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Map<String, List<String>> getData() {
		return this.getData(false);
	}

	public Map<String, List<String>> getData(Boolean executeFlag) {
		HashMap<String, List<String>> data = new HashMap<>();
		int rowCount = excelReader.getNoOfRows();
		int zeroRowColumns = excelReader.getNoOfColumns();
		List<Integer> rowsToSkip = new LinkedList<>();

		for (int column = 0; column < zeroRowColumns; column++) {
			String key = excelReader.getData(0, column);

			List<String> valueList = new ArrayList<>();

			for (int row = 1; row < rowCount; row++) {
				if (rowsToSkip.isEmpty() || !rowsToSkip.contains(row)) {
					String value = excelReader.getData(row, column);
					if (executeFlag && column == 0
							&& value.trim().equalsIgnoreCase("false")) {
						rowsToSkip.add(row);
					} else {
						valueList.add(value);
					}
				}
			}
			data.put(key, valueList);
		}
		// TODO Auto-generated method stub
		return data;
	}

	/**
	 * Returns the the Row Data
	 * @param ColumnName
	 * @param ColumnValue
	 * @return Hash map
	 */

	public Map<String, List<String>> getRowData(String ColumnName,String ColumnValue) {
		HashMap<String, List<String>> data = new HashMap<>();
		int rowNum  = excelReader.getRowNo(ColumnName, ColumnValue); 
		int columns = excelReader.getNoOfColumns();

		for (int column = 0; column < columns; column++) {
			String key = excelReader.getData(0, column);

			List<String> valueList = new ArrayList<>();

			String value = excelReader.getData(rowNum, column);

			valueList.add(value);



			data.put(key, valueList);
		}

		// TODO Auto-generated method stub
		return data;
	}

	/**
	 * it will get the data to multi dimension array
	 * 
	 * @return
	 */
	public String[][] getDataSet() {

		int rowCount = excelReader.getNoOfRows();
		int zeroRowColumns = excelReader.getNoOfColumns();
		List<Integer> rowsToConsider = new LinkedList<>();

		for (int row = 1; row < rowCount; row++) {
			if (excelReader.getData(row, 0).equalsIgnoreCase("true")) {
				rowsToConsider.add(row);
			}
		}

		String valueList[][] = new String[rowsToConsider.size()][zeroRowColumns - 1];

		for (int row = 1; row <= rowsToConsider.size(); row++) {
			for (int col = 1; col < zeroRowColumns; col++) {
				String value = excelReader.getData(rowsToConsider.get(row - 1),
						col);
				valueList[row - 1][col - 1] = value;
			}
		}

		return valueList;

	}

	/**
	 * it will get the data to multi dimension array
	 * 
	 * @return
	 */
	public String[][] getDataSet(Boolean executeFlag) {

		int rowCount = excelReader.getNoOfRows();
		int zeroRowColumns = excelReader.getNoOfColumns();
		List<Integer> rowsToConsider = new LinkedList<>();

		for (int row = 1; row < rowCount; row++) {
			//String value = excelReader.getData(row, 0);
			if (executeFlag ) {
				rowsToConsider.add(row);
			}
		}

		String valueList[][] = new String[rowsToConsider.size()][zeroRowColumns - 1];

		for (int row = 1; row <= rowsToConsider.size(); row++) {
			for (int col = 1; col < zeroRowColumns; col++) {
				String value = excelReader.getData(rowsToConsider.get(row - 1),
						col);
				valueList[row - 1][col - 1] = value;
			}
		}

		return valueList;

	}


	/**
	 * it will get the data to multi dimension array
	 * 
	 * @return
	 */
	public String[][] getDataSet(String ColumnName, String ColumnValue) {

		int rowCount  = excelReader.getRowNo(ColumnName, ColumnValue); 
		int zeroRowColumns = excelReader.getNoOfColumns();


		String valueList[][] = new String[rowCount][zeroRowColumns - 1];


		for (int col = 1; col < zeroRowColumns; col++) {
			String value = excelReader.getData(rowCount,col);
			valueList[rowCount- 1][col - 1] = value;
		}


		return valueList;

	}


}
