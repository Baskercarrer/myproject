package com.mycompany.core.framework.utils;

import java.io.*;
import java.awt.*;  
import java.awt.image.*;
import javax.imageio.*;
import java.util.Date;

public class testResult 
{
	static String sStatus;
	static String sStep;
	static String sDescription;
	static String sConcatenate;
	static BufferedWriter out;
	static BufferedReader in;
	static StringBuilder fileContent = new StringBuilder();
	static String line;
	static String sScriptName;
	static int iReportOffset;
	static int iSummaryOffset;
	static int iLeftOffset;
	static int iRightOffset;
	static int iOffset;
	static boolean bFailUpdated=false;
	static boolean bResultGenerated=false;
	static String sPrevScriptName="";	
	static String sScreenShot="";
	static int iPassCount=0;
	static int iFailCount=0;
	static int iBlockedCount=0;
	static int iReportUpdate;
	static StringBuilder scriptResult;
	static int iStepNo;
	static String dDate;
	static String sOS; 
	static String sBrowserType;
	public testResult()
	{}
	//Method to generate the Result Summary
	public static void summary()
	{
		try{
			
			//Get the pass and fail count to generate the summary
			if (!Global.sScriptName.equals(sPrevScriptName))
			{
				bFailUpdated=false;
				if (sStatus.equalsIgnoreCase("Fail"))
				{
					iFailCount = iFailCount+1;
					bFailUpdated =true;
				}
				else if(sStatus.equalsIgnoreCase("Blocked"))
					iBlockedCount = iBlockedCount+1;
				else
					iPassCount = iPassCount+1;
			}
			else
			{
				if (sStatus.equalsIgnoreCase("Fail"))
				{
			           if (bFailUpdated==false)
			           {
			        	   iFailCount=iFailCount+1;
			               if (iPassCount !=0)
			            	   iPassCount=iPassCount-1;
			               //Make this True to get only one failure represented in graph per test case
			               bFailUpdated = true;
			           }
			   		        	   
				}
			}
			//Create the pie- chart
			sConcatenate ="<div";
			iLeftOffset = fileContent.indexOf(sConcatenate);
			sConcatenate ="p.render(\"pieCanvas\", \"Pie Graph\")</script>";
			iRightOffset =fileContent.indexOf(sConcatenate);
//			fileContent.replace(iLeftOffset, iRightOffset+sConcatenate.length(), "<div id=\"pieCanvas\" style=\"overflow: auto; position:relative;height:350px;width:380px;\"></div><script type=\"text/javascript\">var p = new pie();p.add(\"Pass\","+iPassCount+");p.add(\"Fail\","+iFailCount+");p.add(\"Blocked\","+iBlockedCount+");p.render(\"pieCanvas\", \"Pie Graph\")</script>");
			fileContent.replace(iLeftOffset, iRightOffset+sConcatenate.length(), "<div id=\"pieCanvas\" style=\"overflow: auto; position:relative;height:350px;width:380px;\"></div><script type=\"text/javascript\">var p = new pie();p.add(\"Pass\","+iPassCount+");p.add(\"Fail\","+iFailCount+");p.render(\"pieCanvas\", \"Pie Graph\")</script>");
			sConcatenate ="<table border=\"1\" align=\"center\">";			
			iLeftOffset = fileContent.indexOf(sConcatenate);
			sConcatenate ="</td></tr>";
			iRightOffset =fileContent.indexOf(sConcatenate);
			fileContent.replace(iLeftOffset, iRightOffset+sConcatenate.length(),"<table border=\"1\" align=\"center\"><tr bgcolor=\"#C2DFFF\"><th>Total No.of Test Cases</th><th>Test Cases Passed</th><th>Test Cases Failed</th></tr><tr align=\"center\"><td>"+(iPassCount+iFailCount+iBlockedCount)+"</td><td>"+iPassCount+"</td><td>"+iFailCount+"</td></tr>");
			
			//Update the pie-chart for new script
			if (!Global.sScriptName.equals(sPrevScriptName))
			{
				sConcatenate = "</table></table>";
				iSummaryOffset = fileContent.indexOf(sConcatenate);
				if(sStatus.equalsIgnoreCase("Fail"))
					fileContent.insert(iSummaryOffset,"<tr bgcolor=\"#C2DFFF\"><th align=\"left\" colspan=\"2\"><a href=\"#"+Global.sScriptName+"\">"+Global.sScriptName+"</a></th><th colspan=\"2\">Fail</th></tr>");
				else if(sStatus.equalsIgnoreCase("Blocked"))
					fileContent.insert(iSummaryOffset,"<tr bgcolor=\"#C2DFFF\"><th align=\"left\" colspan=\"2\"><a href=\"#"+Global.sScriptName+"\">"+Global.sScriptName+"</a></th><th colspan=\"2\">Blocked</th></tr>");
				else
					fileContent.insert(iSummaryOffset,"<tr bgcolor=\"#C2DFFF\"><th align=\"left\" colspan=\"2\"><a href=\"#"+Global.sScriptName+"\">"+Global.sScriptName+"</a></th><th colspan=\"2\">Pass</th></tr>");
				
			}
			//Update the pie-chart with the status of the same script
			else
			{
				sConcatenate="<tr bgcolor=\"#C2DFFF\"><th align=\"left\" colspan=\"2\"><a href=\"#"+Global.sScriptName+"\">"+Global.sScriptName+"</a></th><th colspan=\"2\">Pass</th></tr>";
				iSummaryOffset = fileContent.indexOf(sConcatenate);
				if (iSummaryOffset!=-1)
				{
					if (bFailUpdated==true)
					{
						fileContent.replace(iSummaryOffset,iSummaryOffset+sConcatenate.length(),"<tr bgcolor=\"#C2DFFF\"><th align=\"left\" colspan=\"2\"><a href=\"#"+Global.sScriptName+"\">"+Global.sScriptName+"</a></th><th colspan=\"2\">Fail</th></tr>");
					}
				}
				
			}
		}
		catch (Exception e){e.getMessage();}
		try{
			out = new BufferedWriter(new FileWriter(Global.sFilePath));
			out.write(fileContent.toString());
			out.close();
		}
		catch (IOException e){System.out.println(e.getMessage());}
		//Assign the script name to the previous script name
    	sPrevScriptName= Global.sScriptName;
	}
	
	//Method to generate the report
	public static void Report(String Step,String Status,String Description)
	{
		sStatus = Status;
		sStep = Step;
		sDescription = Description;
		//Generate the basic html skeleton for the first time
		if (bResultGenerated==false)
		{
			generateHTML();
			
		}
		//Make it true once the result file is created for the test suite
		
		try{
			if (!Global.sScriptName.equals(sPrevScriptName))
			{
				fileContent = new StringBuilder();
				in = new BufferedReader(new FileReader(Global.sFilePath));	
				while ((line = in.readLine()) != null)
				{
					fileContent.append(line);
				}
				in.close();
				scriptResult=new StringBuilder();
	        	sConcatenate = "</table></body>";
	        	iReportOffset = fileContent.indexOf(sConcatenate);
				//fileContent.insert(iReportOffset,"</table>");
				iStepNo =1;				
			}
			iReportUpdate = scriptResult.length();
			
			if (sStatus.equals("Fail"))
			{
				//Capture the screen shot in case of failure
				captureScreen();
				scriptResult.append("<tr style=\"color:#E66C2C\"><td>"+iStepNo+"</td><td>"+(new Date().toString())+"</td><td>"+sDescription+"</td><td>"+sStatus+"</td><td>"+sStep+"</td><td><a href=\""+sScreenShot+"\" target=\"_blank\">"+sScreenShot+"</a></td></tr>");
//				scriptResult.append("<tr style=\"color:#E66C2C\"><td>"+iStepNo+"</td><td>"+(new Date().toString())+"</td><td>"+sDescription+"</td><td>"+sStatus+"</td><td>"+sStep+"</td><td>&nbsp;</td></tr>");
				Global.bAssertion = false;
			}
			else if(sStatus.equals("Pass"))
				scriptResult.append("<tr style=\"color:#348017\"><td>"+iStepNo+"</td><td>"+(new Date().toString())+"</td><td>"+sDescription+"</td><td>"+sStatus+"</td><td>"+sStep+"</td><td>&nbsp;</td></tr>");
			else
				scriptResult.append("<tr style=\"color:#330099\"><td>"+iStepNo+"</td><td>"+(new Date().toString())+"</td><td>"+sDescription+"</td><td>"+sStatus+"</td><td>"+sStep+"</td><td>&nbsp;</td></tr>");
			iStepNo =iStepNo+1;			
			//Write the steps to the file for new script
			if (!Global.sScriptName.equals(sPrevScriptName))
			{
				dDate = new Date().toString();
				fileContent.insert(iReportOffset,"<tr bgcolor=\"#FFF8C6\"><th colspan=\"3\"><a name=\""+Global.sScriptName+"\">"+Global.sScriptName+"</a></th><th colspan=\"3\">"+dDate+"</th></tr><tr bgcolor=\"#C2DFFF\"><th>Step No.</th><th>Time of Execution</th><th>Step Name</th><th>Status</th><th>Step Description</th><th>Comments</th></tr>"+scriptResult.toString());
				
			}
			//Update the steps for the same script
			else
			{				
				sConcatenate ="<tr bgcolor=\"#FFF8C6\"><th colspan=\"3\"><a name=\""+Global.sScriptName+"\">"+Global.sScriptName+"</a></th><th colspan=\"3\">"+dDate+"</th></tr><tr bgcolor=\"#C2DFFF\"><th>Step No.</th><th>Time of Execution</th><th>Step Name</th><th>Status</th><th>Step Description</th><th>Comments</th></tr>";
				iReportOffset = fileContent.indexOf(sConcatenate);
				iOffset = sConcatenate.length();
				fileContent.replace(iReportOffset,iReportOffset+iOffset,"<tr bgcolor=\"#FFF8C6\"><th colspan=\"3\"><a name=\""+Global.sScriptName+"\">"+Global.sScriptName+"</a></th><th colspan=\"3\">"+dDate+"</th></tr><tr bgcolor=\"#C2DFFF\"><th>Step No.</th><th>Time of Execution</th><th>Step Name</th><th>Status</th><th>Step Description</th><th>Comments</th></tr>");
				fileContent.delete(iReportOffset+iOffset, iReportOffset+iOffset+iReportUpdate);
				fileContent.insert(iReportOffset+iOffset,scriptResult.toString());
				sConcatenate ="<tr bgcolor=\"#FFF8C6\"><th colspan=\"3\"><a name=\""+Global.sScriptName+"\">"+Global.sScriptName+"</a></th><th colspan=\"3\">"+dDate+"</th></tr><tr bgcolor=\"#C2DFFF\"><th>Step No.</th><th>Time of Execution</th><th>Step Name</th><th>Status</th><th>Step Description</th><th>Comments</th></tr>";
				iReportOffset = fileContent.indexOf(sConcatenate);
				iOffset = sConcatenate.length();
			}
			//Write the content to the file
			out = new BufferedWriter(new FileWriter(Global.sFilePath));
	        out.write(fileContent.toString());
	        out.close();
		
	    } catch (Exception e) {e.getMessage();
	    }
	    
	    //Create summary of the results
		summary();
	}
	//Method to generate the HTML skeleton of the result file
	public static void generateHTML()
	{
		try
		{
			bResultGenerated = true;
			createFile();
			sOS = Utility.getOS();
			sOS = sOS + " OS";
			sBrowserType = Global.gBrowserType + " Browser";
			
			//String sSystemDetails="System Details:" + sOS + ":" + sBrowserType;
			out = new BufferedWriter(new FileWriter(Global.sFilePath));
//			out.write("<html><h1 align=\"center\">Automation Suite Results</h1><script type=\"text/javascript\" src=\"wz_jsgraphics.js\"></script><script type=\"text/javascript\" src=\"pie.js\"></script><body><table align=\"center\"><div id=\"pieCanvas\" style=\"overflow: auto; position:relative;height:350px;width:380px;\"></div><script type=\"text/javascript\">var p = new pie();p.add(\"Pass\","+iPassCount+");p.add(\"Fail\","+iFailCount+");p.add(\"Blocked\","+iBlockedCount+");p.render(\"pieCanvas\", \"Pie Graph\")</script><h3 align=\"center\">Test Execution Summary</h3><table border=\"1\" align=\"center\"><tr bgcolor=\"#C2DFFF\"><th>Total No.of Test Cases</th><th>Test Cases Passed</th><th>Test Cases Failed</th></tr><tr align=\"center\"><td>"+(iPassCount+iFailCount+iBlockedCount)+"</td><td>"+iPassCount+"</td><td>"+iFailCount+"</td></tr></table></table><br/><br/><br/><br/><br/><br/><br/><br/><table border=\"1\"></table></body></html>");
			out.write("<html><h1 align=\"center\">ED Pulse Check Test Automation Suite Results</h1><script type=\"text/javascript\" src=\"wz_jsgraphics.js\"></script><script type=\"text/javascript\" src=\"pie.js\"></script><body><table align=\"center\"><div id=\"pieCanvas\" \"></div><script type=\"text/javascript\">var p = new pie();p.add(\"Pass\","+iPassCount+");p.add(\"Fail\","+iFailCount+");;p.render(\"pieCanvas\", \"Pie Graph\")</script><h3 align=\"center\">Test Execution Summary</h3><table border=\"1\" align=\"center\"><tr bgcolor=\"#C2DFFF\"><th>Total No.of Test Cases</th><th>Test Cases Passed</th><th>Test Cases Failed</th></tr><tr align=\"center\"><td>"+(iPassCount+iFailCount+iBlockedCount)+"</td><td>"+iPassCount+"</td><td>"+iFailCount+"</td></tr></table></table><br/><br/><br/><br/><br/><br/><br/><br/><table border=\"1\"></table></body></html>");
//			out.write("<html><h1 align=\"center\">Automation Suite Results</h1><script type=\"text/javascript\" src=\"wz_jsgraphics.js\"></script><script type=\"text/javascript\" src=\"pie.js\"></script></div><script type=\"text/javascript\">var p = new pie();p.add(\"Pass\","+iPassCount+");p.add(\"Fail\","+iFailCount+");p.add(\"Blocked\","+iBlockedCount+");p.render(\"pieCanvas\", \"Pie Graph\")</script><h3 align=\"center\">Test Execution Summary</h3><table border=\"1\" align=\"center\"><tr bgcolor=\"#C2DFFF\"><th>Total No.of Test Cases</th><th>Test Cases Passed</th><th>Test Cases Failed</th></tr><tr align=\"center\"><td>"+(iPassCount+iFailCount+iBlockedCount)+"</td><td>"+iPassCount+"</td><td>"+iFailCount+"</td></tr></table></table><br/><br/><br/><br/><br/><br/><br/><br/><table border=\"1\"></table></body></html>");
			
			out.close();
		}
		catch (IOException e){e.getMessage();}
	}
	
	//Method to create the file if not already existing
	public static void createFile()
	{
		try {
	        File file = new File(Global.sFilePath);
	    
	        // Create file if it does not exist
	        boolean success = file.createNewFile();
	        if (success) {
	            // File did not exist and was created
	        } else {
	            // File already exists
	        }
	    } 
		catch (IOException e) {
			System.out.println(e.getMessage());
	    }

	}
	
	//Method to capture the screenshot in case of failures
	static void captureScreen()
	{
		
		try  
		{  
		       //Get the screen size  
		       Toolkit toolkit = Toolkit.getDefaultToolkit();  
		       Dimension screenSize = toolkit.getScreenSize();  
		       Rectangle rect = new Rectangle(0, 0, screenSize.width, screenSize.height);  
		       Robot robot = new Robot();  
		       BufferedImage image = robot.createScreenCapture(rect);  
		       File file;  
		     
		       /*//Save the screenshot as a png  
		       file = new File(sScriptName+".png");  
		       ImageIO.write(image, "png", file); */ 
		     
		       //Save the screenshot as a jpg
		       if (sStatus.equals("Fail"))
		       {
		    	   String sFileName=Global.sScriptName+(new Date().getTime());
		    	   
		    	   sScreenShot= Constants.sReportFolderPath + Utility.getSlashType() +sFileName+".jpg";
		       file = new File(sScreenShot);  
		       ImageIO.write(image, "jpg", file);
		       
		       sScreenShot=sFileName+".jpg";
		       
		       }
		       else
		    	   sScreenShot="&nbsp;";
		 }  
		 catch (Exception e)  
		 {    
		 }
	}

}
