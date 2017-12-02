package com.mycompany.core.framework.utils;

import java.util.Calendar;
import java.util.Map;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("unused")
public class Application 
{
	/**
	 * Purpose:Create Unique report Name by appending timeStamp
	 * @param String sReportName
	 * @return String
	 * @author UST-TACOE 
	 * Authored Date:
	 * Modification Date:
	 */
	public static String getUniqueReportName(String sReportName) {
		Calendar rightNow = Calendar.getInstance();
		if (sReportName == "") {
			return sReportName;
		} else {
			String sNewReportName = sReportName + rightNow.get(Calendar.YEAR)+"-"
					+ (rightNow.get(Calendar.MONTH)+1)+"-"
					+ rightNow.get(Calendar.DAY_OF_MONTH)+"-"
					+ rightNow.get(Calendar.HOUR)+"-"
					+ rightNow.get(Calendar.MINUTE)+"-"
					+ rightNow.get(Calendar.SECOND);
			return sNewReportName;
		}
	}
}
