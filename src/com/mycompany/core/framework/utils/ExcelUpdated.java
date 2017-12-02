package com.mycompany.core.framework.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


//import com.utils.apputils.ExcelQueries;
//import com.utils.apputils.TOSheets;

public class ExcelUpdated {
	Connection con = null;
	Statement st = null;
	static int intRowCount=0;
	public static int getExcelRowCount()
	{
		return intRowCount;
	}
	public ArrayList<HashMap<String,String>> getExcelData (String excelPath, String SheetName,String Fields, String Filter )
			throws ClassNotFoundException, SQLException 
	{
		
		String query;
		ResultSet rsRecords;
		ResultSet rsRecords1;
		String myDB;
	
		String queryMyCount;
		ArrayList<HashMap<String,String>> Records = new ArrayList<HashMap<String,String>>();
		myDB = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=c:/EDTestScenario.xls;DriverID=22;READONLY=false";
//		myDB = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="
//		+ excelPath + ";" + "DriverID=22;READONLY=false";
		query  = "Select " + Fields + " from [" + SheetName + "$]";
		
		queryMyCount  = "Select * from [" + SheetName + "$]";
		
		if (Filter != null)
		{
			query = query + " where " + Filter ; 
		}
		
		// fetching values from database
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con = DriverManager.getConnection(myDB, "", "");
		st = con.createStatement();
		Statement sr = con.createStatement();
		//rsRecords = st.executeQuery(query);
		rsRecords1=sr.executeQuery(queryMyCount);
		while (rsRecords1.next()) {
			intRowCount=intRowCount+1;
		}
		//return rsRecords;
		sr.close();	
		// Reading result set values into Array list
		rsRecords = st.executeQuery(query);
		ResultSetMetaData rsColumnsNames;
		rsColumnsNames = rsRecords.getMetaData();
		while (rsRecords.next()) {
			//Object objTest[] = new Object [1];
			String tmpVal = rsRecords.getString(1);
			
			if (tmpVal==null)
				continue;
			
			//HashMap<String, String> hmStoreData = new HashMap<String, String>();
			HashMap<String, String> hmStoreData = new HashMap<String, String>();
			hmStoreData.put(rsColumnsNames.getColumnLabel(1), tmpVal);
			for (int i = 2; i <= rsColumnsNames.getColumnCount(); i++) 
			{
				tmpVal = rsRecords.getString(i);
				if ( tmpVal == null)
				{
					continue;
				}
			
				hmStoreData.put(rsColumnsNames.getColumnLabel(i), tmpVal);
			}
			//objTest [0] = hmStoreData;
			Records.add(hmStoreData);
		}

		return Records;
	}
	


	public void closeConnection() {
		try {
			intRowCount=0;
			st.close();
			con.close();
		} catch (Exception e) {
		}
	}

}