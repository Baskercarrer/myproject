package com.mycompany.core.framework.reports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReportsUtilities
{
  public String elapsedTime(long paramLong1, long paramLong2)
  {
    long l1 = paramLong2 - paramLong1;
    long l2 = l1;
    l1 /= 1000L;
    int i = (int)(l1 % 60L);
    int j = (int)(l1 % 3600L / 60L);
    int k = (int)(l1 / 3600L);
    if ((k == 0) && (j == 0) && (i == 0))
      return l2 + " milli sec";
    if ((k == 0) && (j == 0))
      return i + " sec";
    if (k == 0)
      return j + " min " + i + " sec";
    return k + " hrs" + j + " min " + i + " sec";
  }

  public void loadPropertyFile(Properties paramProperties, String paramString)
  {
    try
    {
      paramProperties.load(new FileReader(paramString));
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
    }
    catch (IOException localIOException)
    {
    }
  }

  public void closePropertyFile(Properties paramProperties, String paramString)
  {
    try
    {
      paramProperties.store(new FileOutputStream(paramString), null);
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
    }
    catch (IOException localIOException)
    {
    }
  }
}