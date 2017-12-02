package com.mycompany.core.framework.reports;

//import com.testng.listeners.TestExecutionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import org.testng.ITestResult;

public class HtmlWriter
{
  public void generateTestStepsPage_1(PrintWriter paramPrintWriter, String paramString)
    throws FileNotFoundException
  {
    paramPrintWriter.println("<!--\nTo change this template, choose Tools | Templates\nand open the template in the editor.\n-->\n<!DOCTYPE html>\n<html>\n    <head>\n        <title></title>\n        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"../../../other_files/styles/content.css\"  />\n        <link type=\"text/css\" rel=\"stylesheet\"   href=\"../../../other_files/styles/layout.css\"  />\n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"../../../other_files/styles/menu.css\"  />\n\n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"../../../other_files/styles/reportTable.css\" />\n\n        <style type=\"text/css\">\n\n            #resultTable{\n                background-color: white;\n\n                font-size: 15px;\n                font-family: sans-serif;\n\n                margin: 0 auto;\n                margin-top: 40px;\n                margin-bottom: 50px;\n                border-width: 5px;      \n                width: 92%;\n\n                zoom: 1;\n                filter: progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=0),\n                    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=90),\n                    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=180),\n                    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=270);\n\n                /* Shadow for boxes */\n                -moz-box-shadow: 0 0 10px #CCCCCC;\n                -ms-box-shadow: 0 0 10px #CCCCCC;\n                -webkit-box-shadow: 0 0 10px #CCCCCC;\n                box-shadow: 0 0 10px #CCCCCC;\n            }\n            #resultTable a img{\n                height: 28px;\n                width: 89px;\nborder:1px solid #B4E237;            }\n            #resultTable a img:hover{\n                height: 28px;\n                width: 89px;\nborder:1px solid #E50606;            }\n#resultTable tr:hover {background-color:#E5E3E3;}h3{color:#999 ;text-align: left;margin-left: 40px; margin-top: 30px;}#envTable{text-align:left;background-color: white;-moz-box-shadow: 0 0 10px #CCCCCC;                -ms-box-shadow: 0 0 10px #CCCCCC;                -webkit-box-shadow: 0 0 10px #CCCCCC;               box-shadow: 0 0 10px #CCCCCC;\t\t\t\tfont-size: 14px;\t\t\twidth : 92%;\t\t\t margin: 0 auto;   zoom: 1;filter:  progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=0),    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=90),    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=180),   progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=270);}#envTable td { border:0; padding-left:10px;}        </style>\n    </head>\n    <body>\n\n        <div id=\"container\">\n\n\n            <div id=\"header\">\n                <a id=\"logo\" href=\"../../../index.html\">\n                    <!--img src=\"../../../other_files/images/header.png\" /-->\n<h1 style=\"color:white;font-size:50px;margin-left:40px;margin-bottom:10px\">Automation Reports</h1>                </a>\n                <p>\n                </p>\n\n            </div>\n\n            <div id=\"content\">\n                <div id=\"menuholder\">\n                    <ul>\n                        <li id=\"first\"><a href=\"../../../index.html\" >Home</a></li>\n                        <li id=\"last\"><a href=\"../../completeReports.html\" >Complete Execution Reports</a></li>\n                    </ul>\n                </div>\n                <div id=\"mainContent\">\n                    <h3 style=\"color:Black;\">Test Case Name: " + paramString + "</h3>\n" + "                    <div id=\"resultTable\" >\n" + "                        <table border=\"1\">                           \n" + "                            <thead>\n" + "                                <tr>\n" + "                                    <th>Step No</th>\n" + "                                    <th>Action Performed</th>\n" + "                                    <th>Step Result</th>\n" + "                                    <th>Failure Cause</th>\n" + "                                    <th>Screen Shot</th>\n" + "                                </tr>\n" + "                            </thead>\n" + "                            <tbody>\n");
  }

  public void generateTestStepsPage_2(PrintWriter paramPrintWriter, String paramString, ITestResult paramITestResult, boolean paramBoolean)
    throws FileNotFoundException
  {
    paramPrintWriter.println("</tbody>\n                        </table>                 \n\n                    </div>\n\n");
    if (paramBoolean)
    {
      paramPrintWriter.print("<h3 style=\"color:Black;\">Method Parameters </h3>");
      paramPrintWriter.print("<table id=\"envTable\">");
      paramPrintWriter.print("<tr><td>");
      for (Object localObject : paramITestResult.getParameters())
        paramPrintWriter.print("&nbsp;\"" + localObject.toString() + "\"&nbsp;");
      paramPrintWriter.print("</td></tr>");
      paramPrintWriter.print("</table>");
    }
    paramPrintWriter.println("<h3 style=\"color:Black;\">Environment Details</h3>");
    paramPrintWriter.print("<p id=\"env\">");
    paramPrintWriter.print("<table id=\"envTable\">");
    paramPrintWriter.print("<tr><td><h4 style=\"color: #3B6C8E;\">Operating System:</h4> " + getAttribute(paramITestResult, "Operating System") + "</td>");
    if (paramITestResult.getStatus() != 3)
      paramPrintWriter.print("<td><h4 style=\"color: #3B6C8E;\">Browser Name:</h4> " + getAttribute(paramITestResult, "Browser Name") + "</td>");
    paramPrintWriter.print("<td><h4 style=\"color: #3B6C8E;\">WebDriver Version:</h4> " + getAttribute(paramITestResult, "WebDriver Version").toString() + "</td></tr>");
    paramPrintWriter.print("<tr><td><h4 style=\"color: #3B6C8E;\">OS Architecture:</h4> " + getAttribute(paramITestResult, "OS Architecture") + "</td>");
    if (paramITestResult.getStatus() != 3)
      paramPrintWriter.print("<td><h4 style=\"color: #3B6C8E;\">Browser Version:</h4> " + getAttribute(paramITestResult, "Browser Version") + "</td>");
    paramPrintWriter.print("<td><h4 style=\"color: #3B6C8E;\">Java Version:</h4> " + getAttribute(paramITestResult, "Java Version") + "</td></tr>");
    paramPrintWriter.print("</p>");
    paramPrintWriter.print("</table>");
    paramPrintWriter.println("\n" + "                </div>\n" + "            </div>\n" + "\n"            + "\n" + "        </div>\n" + "    </body>\n" + "</html>\n" + "\n" + "\n" + "");
    paramPrintWriter.flush();
    paramPrintWriter.close();
  }

  private String getAttribute(ITestResult paramITestResult, String paramString)
  {
    String str = "";
    try
    {
      str = paramITestResult.getAttribute(paramString).toString();
    }
    catch (Exception localException)
    {
      str = "Info UnAvailable";
    }
    return str;
  }

  public void generateTestCaseReportsPage_1(PrintWriter paramPrintWriter)
    throws FileNotFoundException
  {
    paramPrintWriter.println("<!--\nTo change this template, choose Tools | Templates\nand open the template in the editor.\n-->\n<!DOCTYPE html >\n<html>\n    <head>\n        <title></title>\n        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"../../other_files/styles/content.css\"  />\n        <link type=\"text/css\" rel=\"stylesheet\"   href=\"../../other_files/styles/layout.css\"  />\n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"../../other_files/styles/menu.css\"  />\n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"../../other_files/styles/ie.css\"  />\n  <link rel=\"stylesheet\" type=\"text/css\" href=\"../../other_files/styles/jqplot.css\" />\n<!--[if lte IE 9]><script language=\"javascript\" type=\"text/javascript\" src=\"../../other_files/js/excanvas.js\"></script><![endif]-->\n<script type=\"text/javascript\" src=\"../../other_files/js/jquery.min.js\"></script>\n<script type=\"text/javascript\" src=\"../../other_files/js/jquery.jqplot.min.js\"></script>\n<script type=\"text/javascript\" src=\"../../other_files/js/jqplot.pieRenderer.min.js\"></script>        <script src=\"testCaseReport.js\" type=\"text/javascript\" ></script>\n        \n        \n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"../../other_files/styles/reportTable.css\"  />\n        <style type=\"text/css\">\n            #resultTable{\n                background-color: white;\n                font-size: 15px;\n                font-family: sans-serif;\n\n                margin-left: 35px;\n                margin-top: 105px;\n                border-width: 5px;      \n                width: 48%;\n                /* Shadow for boxes */\n                -moz-box-shadow: 0 0 10px #CCCCCC;\n                -ms-box-shadow: 0 0 10px #CCCCCC;\n                -webkit-box-shadow: 0 0 10px #CCCCCC;\n                box-shadow: 0 0 10px #CCCCCC;\n\n\n            }\n\n            #resultTable table \n            {              \n\n                border-collapse:collapse;\n                width:100%;\n                height: 100%;\n                margin-bottom: 50px;\n            }\nh3{color:#999 ;text-align: left;margin-left: 40px; margin-top: 30px;}#envTable{border-collapse:collapse;text-align:left;background-color: white;-moz-box-shadow: 0 0 10px #CCCCCC;                -ms-box-shadow: 0 0 10px #CCCCCC;                -webkit-box-shadow: 0 0 10px #CCCCCC;               box-shadow: 0 0 10px #CCCCCC;\t\t\t\tfont-size: 14px;position: fixed !important;top: 630px;right: 11%;width: 450px;\t\t\t margin: 0 auto;   zoom: 1;filter:  progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=0),    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=90),    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=180),   progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=270);}#envTable td { border:0; padding-left:10px;} #resultTable tr:hover a { color:#DC143C;}#resultTable tr:hover{font-weight:600; color:#DC143C; }        </style>\n\n<!--[if !IE]> --> <style type=\"text/css\">            #holder {\n                -moz-border-radius: 2px;\n                -moz-box-shadow: 0 1px 3px #666;\n\n                -webkit-border-radius: 2px;\n                -webkit-box-shadow: 0 1px 3px #666;\n\n\n                /* Shadow for boxes */\n                -moz-box-shadow: 0 0 8px #CCCCCC;\n                -ms-box-shadow: 0 0 8px #CCCCCC;\n                -webkit-box-shadow: 0 0 8px #CCCCCC;\n                box-shadow: 0 0 8px #CCCCCC;\n\n\n                zoom: 1;\n                filter: progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=0),\n                    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=90),\n                    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=180),\n                    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=270);\n\n                background-color: white;\n\n                margin-bottom: 50px;\n                position: fixed !important;\n                \n                top: 198px;\n                right: 12%;\n\n                width: 450px;\n                height: 300px;\n                margin-top: 100px;\n            }\n </style><!-- <![endif]-->\n\n <!--[if IE ]><style type=\"text/css\"> #holder {              background-color: white;             margin-bottom: 30px;              width: 450px;            height: 300px;     }</style><![endif]-->\n    </head>\n    <body>\n\n        <div id=\"container\">\n\n\n            <div id=\"header\">\n                <a id=\"logo\" href=\"../../index.html\">\n                    <!--img src=\"../../other_files/images/header.png\" /-->\n<h1 style=\"color:white;font-size:50px;margin-left:40px;margin-bottom:10px\">Automation Reports</h1>                </a>             \n\n            </div>\n\n            <div id=\"content\">\n                <div  id=\"menuholder\">\n                    <ul>\n                        <li id=\"first\"><a href=\"../../index.html\" >Home</a></li>\n                        <li id=\"last\"><a href=\"../completeReports.html\" >Complete Execution Reports</a></li>\n                    </ul>\n                </div>\n                <div id=\"mainContent\">\n\n");
    paramPrintWriter.print("                    <div id=\"resultTable\">\n                        <table border=\"1\">\n                            <thead>\n                                <tr>\n                                    <th>S.No</th>\n                                    <th>Test Case Name</th>\n                                    <th>Result</th>\n                                    <th>Execution Time</th>\n                                </tr>\n                            </thead>\n                            <tbody>\n");
  }

  public void generateTestCaseReportsPage_2(PrintWriter paramPrintWriter, String paramString)
  {
    paramPrintWriter.println("\n                            </tbody>\n                        </table>\n\n                    </div>\n\n" + "                    <div id=\"holder\"></div>\n" + "\n");
    if (TestExecutionListener.configFail)
    {
      paramPrintWriter.print("<p id=\"env\">");
      paramPrintWriter.print("<table id=\"envTable\">");
      paramPrintWriter.print("<caption style=\"padding-bottom:1px; font-size:15px\" ><h3>Configuration Method Failures</h3></caption>");
      paramPrintWriter.print("<tr><td>Configuration Type</td><td>Method Name</td></tr>");
      Iterator localIterator = TestExecutionListener.configFailures.iterator();
      while (localIterator.hasNext())
      {
        ITestResult localITestResult = (ITestResult)localIterator.next();
        paramPrintWriter.print("<tr><td>" + getAttribute(localITestResult, "configAttrib") + "</td><td>" + localITestResult.getName() + "</td></tr>");
      }
      paramPrintWriter.print("</table>");
      paramPrintWriter.print("</p");
    }
    //paramPrintWriter.print("                </div>\n            </div>\n\n            <div id=\"footer\">\n                <p>\n                    Copy \n                    &nbsp; <a href=\"#\" >Privacy Terms</a>\n                    &#x7C; <a href=\"#\" >Terms of Use</a>\n                    <br/>\n                    <br/>\n                    Note: Best viewed in Google Chrome, Apple Safari, Opera and Mozilla Firefox\n                </p>\n                <p>\n\n                </p>\n            </div>\n\n        </div>\n    </body>\n</html>\n");
    paramPrintWriter.print("                </div>\n            </div>\n\n                  </div>\n    </body>\n</html>\n");
    paramPrintWriter.flush();
    paramPrintWriter.close();
  }

  public void generateCompleteReportsPage_1(PrintWriter paramPrintWriter)
    throws FileNotFoundException
  {
    paramPrintWriter.println("<!--\nTo change this template, choose Tools | Templates\nand open the template in the editor.\n-->\n<!DOCTYPE html>\n<html>\n    <head>\n        <title></title>\n        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"../other_files/styles/jqplot.css\"  />\n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"../other_files/styles/content.css\"  />\n        <link type=\"text/css\" rel=\"stylesheet\"   href=\"../other_files/styles/layout.css\"  />\n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"../other_files/styles/menu.css\"  />\n\n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"../other_files/styles/reportTable.css\" />\n\n\n        <!--[if lte IE 9]><script language=\"javascript\" type=\"text/javascript\" src=\"../other_files/js/excanvas.js\"></script><![endif]-->\n        <script  type=\"text/javascript\" src=\"../other_files/js/jquery.min.js\"></script>\n\n\n        <script  type=\"text/javascript\" src=\"../other_files/js/jquery.jqplot.min.js\"></script>\n        <script  type=\"text/javascript\" src=\"../other_files/js/jqplot.barRenderer.min.js\"></script>\n        <script  type=\"text/javascript\" src=\"../other_files/js/jqplot.categoryAxisRenderer.min.js\"></script>\n        <script  type=\"text/javascript\" src=\"../other_files/js/jqplot.pointLabels.min.js\"></script>\n        <script type=\"text/javascript\" src=\"completeReports.js\"></script>\n\n        <style type=\"text/css\">\n            #chartSize{\n\n                height: 300px;\n                margin: 0 auto;\n\n                margin-top: 50px;\n                width: 85%;\n\n\nborder-style: solid;\nborder-width: 1px;\nborder-color: #B0B0B0;\n\n                /* Shadow for boxes */\n                -moz-box-shadow: 0 0 10px #CCCCCC;\n                -ms-box-shadow: 0 0 10px #CCCCCC;\n                -webkit-box-shadow: 0 0 10px #CCCCCC;\n                box-shadow: 0 0 10px #CCCCCC;\n\n                zoom: 1;\n                filter: progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=0),\n                    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=90),\n                    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=180),\n                    progid:DXImageTransform.Microsoft.Shadow(Color=#cccccc, Strength=2, Direction=270);\n\n                background-color: white;\n            }\n#resultTable tr:hover a { color:#DC143C;}#resultTable tr:hover{ color:#DC143C; }          </style>\n\n    </head>\n    <body>\n\n        <div id=\"container\">\n\n\n            <div id=\"header\">\n                <a id=\"logo\" href=\"../index.html\">\n<h1 style=\"color:white;font-size:50px;margin-left:40px;margin-bottom:10px\">Automation Reports</h1>                </a>\n                <p>\n                </p>\n\n            </div>\n\n            <div id=\"content\">\n                <div id=\"menuholder\">\n                    <ul>\n                        <li id=\"first\"><a href=\"../index.html\" >Home</a></li>\n                        <li id=\"last\"><a href=\"completeReports.html\" >Complete Execution Reports</a></li>\n                    </ul>\n                </div>\n                <div id=\"mainContent\">\n\n                    <div id=\"chartSize\">\n                        <div id=\"chart\" style=\"height: 270px; width: 100%; margin: 0 auto;margin-top: 20px;\"></div>\n                    </div>\n\n\n\n                    <div id=\"resultTable\" style=\"width:85%\">\n                        <table border=\"1\">\n                            <thead>\n                                <tr>\n                                    <th>S.No</th>\n                                    <th>Execution</th>\n<th>Execution Time</th>                                    <th>Total Test Cases</th>\n                                    <th>Total Passed</th>\n                                    <th>Total Failed</th>\n                                    <th>Total Not Run</th>\n                                    <th>Execution Date</th>\n                                </tr>\n                            </thead>\n                            <tbody>\n");
  }

  public void generateCompleteReportsPage_2(PrintWriter paramPrintWriter)
  {
    paramPrintWriter.println("                            </tbody>\n                        </table>                 \n\n                    </div>\n\n\n                </div>\n            </div>\n\n                   </div>\n    </body>\n</html>\n\n");
    paramPrintWriter.flush();
    paramPrintWriter.close();
  }

  public void generateIndexPage(PrintWriter paramPrintWriter)
    throws FileNotFoundException
  {
    paramPrintWriter.println("<!--\nTo change this template, choose Tools | Templates\nand open the template in the editor.\n-->\n<!DOCTYPE html>\n<html>\n    <head>\n        <title></title>\n        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"other_files/styles/content.css\"  />\n        <link type=\"text/css\" rel=\"stylesheet\"   href=\"other_files/styles/layout.css\"  />\n        <link type=\"text/css\" rel=\"stylesheet\"  href=\"other_files/styles/menu.css\"  />\n\t\t<!--[if lt IE 9]> <style type=\"text/css\"> #mainContent{color:blue;height:550px;} </style> <![endif]-->    </head>\n    <body>\n\n        <div id=\"container\">\n\n\n            <div id=\"header\">\n                <a id=\"logo\" href=\"index.html\">\n<h1 style=\"color:white;font-size:50px;margin-left:40px;margin-bottom:10px\">Automation Reports</h1>                </a>             \n\n            </div>\n\n            <div id=\"content\">\n                <div  id=\"menuholder\">\n                    <ul>\n                        <li id=\"first\"><a href=\"index.html\">Home</a></li>\n                        <li id=\"last\"><a href=\"executions/completeReports.html\" >Complete Execution Reports</a></li>\n                    </ul>\n                </div>\n                <div id=\"mainContent\">\n\n\n                </div>\n            </div>\n\n            </div>\n    </body>\n</html>\n");
    paramPrintWriter.flush();
    paramPrintWriter.close();
  }
}