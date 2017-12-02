package com.mycompany.core.framework.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.imageio.stream.FileImageOutputStream;

public class ReportFiles
{
  private InputStream input = null;
  private OutputStream output = null;
  private FileImageOutputStream imageOutputStream = null;
  private File file = null;
  private PrintWriter out;

  public void init(String paramString)
  {
    if (!hasDirectory(paramString + System.getProperty("file.separator") + "other_files"))
    {
      createRequiredDirectories(paramString + System.getProperty("file.separator") + "other_files");
      if (!hasDirectory(paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "images"))
        createRequiredDirectories(paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "images");
      if (!hasDirectory(paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "js"))
        createRequiredDirectories(paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "js");
      if (!hasDirectory(paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "styles"))
        createRequiredDirectories(paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "styles");
      copyFiles(paramString);
    }
    if (!hasDirectory(paramString + System.getProperty("file.separator") + "executions"))
      createRequiredDirectories(paramString + System.getProperty("file.separator") + "executions");
    if (!new File(paramString + System.getProperty("file.separator") + "report.properties").exists())
    {
      this.file = new File(paramString + System.getProperty("file.separator") + "report.properties");
      try
      {
        this.file.createNewFile();
        this.out = new PrintWriter(this.file);
        this.out.println("executionCount = 0");
        this.out.println("passedSet = ");
        this.out.println("failedSet = ");
        this.out.println("skippedSet = ");
        this.out.println("executionTime = ");
        this.out.println("#Can display upto Max 8 Executions , default is 5 executions");
        this.out.println("maxBarChartResults = 5");
        this.out.println("writeLineChart = false");
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
      finally
      {
        if (this.out != null)
          this.out.close();
      }
    }
    if (!new File(paramString + System.getProperty("file.separator") + "index.html").exists())
    {
      HtmlWriter localHtmlWriter = new HtmlWriter();
      try
      {
        this.out = new PrintWriter(paramString + System.getProperty("file.separator") + "index.html");
        localHtmlWriter.generateIndexPage(this.out);
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
      }
    }
  }

  private void copyFiles(String paramString)
  {
    copyFile("js/excanvas.js", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "js");
    copyFile("js/jqplot.barRenderer.min.js", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "js");
    copyFile("js/jqplot.categoryAxisRenderer.min.js", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "js");
    copyFile("js/jqplot.pointLabels.min.js", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "js");
    copyFile("js/jquery.jqplot.min.js", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "js");
    copyFile("js/jquery.min.js", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "js");
    copyFile("js/jqplot.pieRenderer.min.js", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "js");
    copyFile("styles/content.css", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "styles");
    copyFile("styles/jqplot.css", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "styles");
    copyFile("styles/layout.css", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "styles");
    copyFile("styles/menu.css", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "styles");
    copyFile("styles/reportTable.css", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "styles");
    copyImage("images/yes.png", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "images");
    copyImage("images/no.png", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "images");
    copyImage("images/bg.png", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "images");
    copyImage("images/header.png", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "images");
    copyImage("images/skip.png", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "images");
    copyImage("images/bg1.png", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "images");
    copyImage("images/bg2.png", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "images");
    copyImage("images/bg3.png", paramString + System.getProperty("file.separator") + "other_files" + System.getProperty("file.separator") + "images");
  }

  public boolean hasDirectory(String paramString)
  {
    this.file = new File(paramString);
    if ((this.file.exists()) && (this.file.isDirectory()))
    {
      this.file = null;
      return true;
    }
    this.file = null;
    return false;
  }

  public void createRequiredDirectories(String paramString)
  {
    this.file = new File(paramString);
    this.file.mkdirs();
    this.file = null;
  }

  public void copyImage(String paramString1, String paramString2)
  {
    this.file = new File(paramString1);
    this.input = getClass().getClassLoader().getResourceAsStream(paramString1);
    try
    {
      this.imageOutputStream = new FileImageOutputStream(new File(paramString2 + System.getProperty("file.separator") + this.file.getName()));
      int i = 0;
      while ((i = this.input.read()) >= 0)
        this.imageOutputStream.write(i);
      this.imageOutputStream.close();
    }
    catch (Exception localException2)
    {
    }
    finally
    {
      try
      {
        this.input.close();
        this.imageOutputStream.close();
        this.file = null;
      }
      catch (Exception localException4)
      {
        this.input = null;
        this.imageOutputStream = null;
        this.file = null;
      }
    }
  }

  private void copyFile(String paramString1, String paramString2)
  {
    this.file = new File(paramString1);
    this.input = getClass().getClassLoader().getResourceAsStream(paramString1);
    try
    {
      this.output = new FileOutputStream(paramString2 + System.getProperty("file.separator") + this.file.getName());
      int i = 0;
      while ((i = this.input.read()) >= 0)
        this.output.write(i);
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
    }
    catch (IOException localIOException)
    {
    }
    finally
    {
      try
      {
        this.input.close();
        this.output.close();
        this.file = null;
      }
      catch (Exception localException4)
      {
        this.input = null;
        this.output = null;
        this.file = null;
      }
    }
  }
}