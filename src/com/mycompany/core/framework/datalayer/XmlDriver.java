package com.mycompany.core.framework.datalayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mycompany.core.framework.utils.XmlReader;


/**
 * Driver class that extracts the data from "xml" file
 * 
 * @author UST-TACOE
 * 
 */
public class XmlDriver {

	XmlReader xmlReader = null;

	public XmlDriver(String filePath) {
		this(new File(filePath));
	}

	public XmlDriver(File file) {
		xmlReader = new XmlReader(file);
	}

	public Map<String, List<String>> getData() {
		return this.getData(false);
	}

	public Map<String, List<String>> getData(Boolean executeFlag) {

		HashMap<String, List<String>> data = new HashMap<>();
		int rowCount = xmlReader.getNoOfRows();
		int zeroRowColumns = xmlReader.getNoOfColumns();
		List<Integer> rowsToSkip = new LinkedList<>();

		for (int column = 0; column < zeroRowColumns; column++) {
			String key = xmlReader.getColumnName(0, column);
			List<String> valueList = new ArrayList<>();

			for (int row = 0; row < rowCount; row++) {
				if (rowsToSkip.isEmpty() || !rowsToSkip.contains(row)) {
					String value = xmlReader.getColumnValue(row, column);
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
		int rowNum  = xmlReader.getRowNo(ColumnName, ColumnValue); 
		int columns = xmlReader.getNoOfColumns();
		
		for (int column = 0; column < columns; column++) {
			String key = xmlReader.getColumnValue(0, column);

			List<String> valueList = new ArrayList<>();

				String value = xmlReader.getColumnValue(rowNum, column);
				 
						valueList.add(value);
					 
				 
			 
			data.put(key, valueList);
		}
		 
		// TODO Auto-generated method stub
		return data;
	}
	
	/**
	 * it will get the data to multi dimension array can be used for TestNG Data provide
	 * 
	 * @return
	 */
	public String[][] getDataSet() {

        int rowCount = xmlReader.getNoOfRows();
        int zeroRowColumns = xmlReader.getNoOfColumns();
        List<Integer> rowsToConsider = new LinkedList<>();

        for (int row = 1; row < rowCount; row++) {
               if (xmlReader.getColumnValue(row, 0).equalsIgnoreCase("true")) {
                     rowsToConsider.add(row);
               }
        }

        String valueList[][] = new String[rowsToConsider.size()][zeroRowColumns - 1];

        for (int row = 1; row <= rowsToConsider.size(); row++) {
               for (int col = 1; col < zeroRowColumns; col++) {
                     String value = xmlReader.getColumnValue(rowsToConsider.get(row - 1),
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

        int rowCount = xmlReader.getNoOfRows();
        int zeroRowColumns = xmlReader.getNoOfColumns();
        List<Integer> rowsToConsider = new LinkedList<>();

        for (int row = 1; row < rowCount; row++) {
        	//String value = xmlReader.getColumnValue(row, 0);
               if (executeFlag ) {
                     rowsToConsider.add(row);
               }
        }

        String valueList[][] = new String[rowsToConsider.size()][zeroRowColumns - 1];

        for (int row = 1; row <= rowsToConsider.size(); row++) {
               for (int col = 1; col < zeroRowColumns; col++) {
                     String value = xmlReader.getColumnValue(rowsToConsider.get(row - 1),
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

		int rowCount  = xmlReader.getRowNo(ColumnName, ColumnValue); 
		int zeroRowColumns = xmlReader.getNoOfColumns();
		

        String valueList[][] = new String[rowCount][zeroRowColumns - 1];

       
               for (int col = 1; col < zeroRowColumns; col++) {
                     String value = xmlReader.getColumnValue(rowCount,
                                   col);
                     valueList[rowCount- 1][col - 1] = value;
               }
       

        return valueList;

	}
}
