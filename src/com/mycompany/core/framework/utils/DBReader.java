package com.mycompany.core.framework.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author UST-TACOE
 * 
 */

public class DBReader {

	protected String JDBC_DRIVER = null; // "com.mysql.jdbc.Driver";
	protected String DB_URL = null; // "jdbc:mysql://localhost/EMP";

	protected String USER = null; // "username";
	protected String PASS = null; // "password";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	ResultSetMetaData rsMetadata = null;
	long Rows = 0;
	int Columns = 0;

	/**
	 * 
	 * @param dbDriver
	 * @param dbURL
	 * @param user
	 * @param pwd
	 */

	public DBReader(String dbDriver, String dbURL, String user, String pwd) {
		setDBDriver(dbDriver);
		this.DB_URL = dbURL;
		this.USER = user;
		this.PASS = pwd;
	}

	/**
	 * 
	 * @param dbDriver
	 */
	protected void setDBDriver(String dbDriver) {

		switch (dbDriver.toLowerCase()) {
		case "mysql":
			this.JDBC_DRIVER = "com.mysql.jdbc.Driver";
			break;
		case "oracle":
			this.JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
			break;
		case "sqlserver":
			this.JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			break;

		}

	}

	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
	}

	/**
	 * 
	 * @param sqlStatement
	 * @throws SQLException
	 */
	public void getResultSet(String sqlStatement) throws SQLException {
		rs = stmt.executeQuery(sqlStatement);
		this.rsMetadata = rs.getMetaData();
		int i = 0;
		rs.first();
		while (!rs.isLast()) {

			i++;
			rs.next();
		}

		Rows = i;
		Columns = rsMetadata.getColumnCount();

	}

	/**
	 * Get the number of rows in the said db result set
	 * 
	 * @return Return no of rows present in the said db result set
	 * @throws SQLException
	 */
	public long getNoOfRows() {
		return Rows;
	}

	/**
	 * Return the number of column in the first row of the DB file
	 * 
	 * @return return the no. of column in the first row of the DB file
	 * @throws SQLException
	 */
	public int getNoOfColumns() throws SQLException {

		return Columns;

	}

	/**
	 * 
	 * @param ComlumnName
	 * @return
	 * @throws SQLException
	 */
	public int getColumnNo(String ComlumnName) throws SQLException {

		for (int i = 0; i < getNoOfColumns(); i++) {

			if (rsMetadata.getColumnName(i).equalsIgnoreCase(ComlumnName))
				return i + 1;
		}

		return 0;
	}

	/**
	 * 
	 * @param ColumnName
	 * @param ColumnVal
	 * @return
	 * @throws SQLException
	 */
	public int getRowNo(String ColumnName, String ColumnVal)
			throws SQLException {
		int ColNo = getColumnNo(ColumnName);
		long rows = getNoOfRows();

		for (int i = 1; i < rows; i++) {

			if (rsMetadata.getColumnName(ColNo).equalsIgnoreCase(ColumnVal)) {
				return i;
			}

		}
		return -1;
	}

	/**
	 * Gets the data from the currently opened sheet based on row and column
	 * number
	 * 
	 * @param row
	 *            Row no. from which the value has to be fetched
	 * @param column
	 *            Respective column no. in the row from which the value has to
	 *            be fetched
	 * @return The data present in the respective row & column. If no value is
	 *         found it returns and empty String.
	 */
	public String getData(int row, int column) {
		String data = "";
		try {

			long Nrows = getNoOfRows();
			rs.first();
			for (int i = 1; i <= Nrows; i++) {
				if (i == row)
					break;
				rs.next();

			}

			data = rs.getString(column);

			if (data == null) {
				data = "";
			}
			return data;
		} catch (Exception e) {

			data = "";
			return data;
		}

	}

}
