package com.mycompany.core.framework.datalayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mycompany.core.framework.utils.DBReader;


/**
 * Data will be loaded from Db tables to Hash Map
 * 
 * @author UST-TACOE
 * 
 */
public class DBDriver {

	DBReader dbReader = null;

	public DBDriver(String dbDriver, String dbURL, String user, String pwd,
			String sqlStatement) throws ClassNotFoundException, SQLException {
		dbReader = new DBReader(dbDriver, dbURL, user, pwd);
		dbReader.getConnection();
		dbReader.getResultSet(sqlStatement);
	}

	/**
	 * Returns the result set as Map
	 * 
	 * @return Hash map Map<String, List<String>>
	 * @throws SQLException
	 */
	public Map<String, List<String>> getData() throws SQLException {

		HashMap<String, List<String>> data = new HashMap<>();
		long rowCount = dbReader.getNoOfRows();
		int zeroRowColumns = dbReader.getNoOfColumns();
		List<Integer> rowsToSkip = new LinkedList<>();

		for (int column = 0; column < zeroRowColumns; column++) {
			String key = dbReader.getData(0, column);

			List<String> valueList = new ArrayList<>();

			for (int row = 1; row < rowCount; row++) {
				if (rowsToSkip.isEmpty() || !rowsToSkip.contains(row)) {
					String value = dbReader.getData(row, column);

					valueList.add(value);
				}
			}

			data.put(key, valueList);
		}
		// TODO Auto-generated method stub
		return data;
	}

	/**
	 * Returns the the Row Data of result set
	 * 
	 * @param ColumnName
	 * @param ColumnValue
	 * @return Hash map Map<String, List<String>>
	 * @throws SQLException
	 */

	public Map<String, List<String>> getRowData(String ColumnName,
			String ColumnValue) throws SQLException {

		HashMap<String, List<String>> data = new HashMap<>();
		int rowNum = dbReader.getRowNo(ColumnName, ColumnValue);
		int columns = dbReader.getNoOfColumns();

		for (int column = 0; column < columns; column++) {
			String key = dbReader.getData(0, column);

			List<String> valueList = new ArrayList<>();

			String value = dbReader.getData(rowNum, column);

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
	 * @throws SQLException
	 */
	public String[][] getDataSet() throws SQLException {

		long rowCount = dbReader.getNoOfRows();
		int zeroRowColumns = dbReader.getNoOfColumns();
		List<Integer> rowsToConsider = new LinkedList<>();

		for (int row = 1; row < rowCount; row++) {
			if (dbReader.getData(row, 0).equalsIgnoreCase("true")) {
				rowsToConsider.add(row);
			}
		}

		String valueList[][] = new String[rowsToConsider.size()][zeroRowColumns - 1];

		for (int row = 1; row <= rowsToConsider.size(); row++) {
			for (int col = 1; col < zeroRowColumns; col++) {
				String value = dbReader.getData(rowsToConsider.get(row - 1),
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
	 * @throws SQLException
	 */
	public String[][] getDataSet(Boolean executeFlag) throws SQLException {

		long rowCount = dbReader.getNoOfRows();
		int zeroRowColumns = dbReader.getNoOfColumns();
		List<Integer> rowsToConsider = new LinkedList<>();

		for (int row = 1; row < rowCount; row++) {
			//String value = dbReader.getData(row, 0);
			if (executeFlag) {
				rowsToConsider.add(row);
			}
		}

		String valueList[][] = new String[rowsToConsider.size()][zeroRowColumns - 1];

		for (int row = 1; row <= rowsToConsider.size(); row++) {
			for (int col = 1; col < zeroRowColumns; col++) {
				String value = dbReader.getData(rowsToConsider.get(row - 1),
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
	 * @throws SQLException
	 */
	public String[][] getDataSet(String ColumnName, String ColumnValue)
			throws SQLException {

		int rowCount = dbReader.getRowNo(ColumnName, ColumnValue);
		int zeroRowColumns = dbReader.getNoOfColumns();

		String valueList[][] = new String[rowCount][zeroRowColumns - 1];

		for (int col = 1; col < zeroRowColumns; col++) {
			String value = dbReader.getData(rowCount, col);
			valueList[rowCount - 1][col - 1] = value;
		}

		return valueList;

	}

}
