package com.mycompany.core.framework.webcomponents;

import java.util.List;

import com.mycompany.core.framework.webdriver.SeWebDriver;
import com.mycompany.core.framework.webdriver.SeWebElement;
 
/**
 
 * @author UST-TACOE
 *
 */
public class WebTable implements IWebComponents{
	
	SeWebDriver Sedriver = null;
	SeWebElement element=null;
	String findby;
	String locator;
	int rows;
	int columns;
	
	public WebTable(SeWebDriver driver,String by, String using)
	{
		this.Sedriver = driver;
		this.findby = by;
		this.locator = using;
		Sedriver.waitForElementVisible(by,using);	
		element = Sedriver.findElement(by, using);
		  
		   List<SeWebElement> elements =  Sedriver.findElements(this.findby,this.locator +"/tr");
		   this.rows = elements.size();
		   elements =  Sedriver.findElements(this.findby,this.locator +"/tr[1]/td");
		   this.columns=elements.size();
		  
		  
	}
	 @Override
	public String getFindBy()
	{
		return findby;
	}
	 @Override
	public String getLocator()
	{
		return locator;
	}
	 @Override
	 public Boolean isExist()
	 {
		 return element.isAvailableForAction();
		 
	 }
	 @Override
	 public String getProperty(String prp)
	 {
		 return element.getAttribute(prp);
	 }
	 
	 public int getRowCount()
	 {
				return this.rows; 
	 }

		public  int getColumnCount(){
			
			return this.columns; 	 
		}
		
		public String getCellText(int row,int col)
		{
			SeWebElement ele = Sedriver.findElement(this.findby,this.locator + "/tr["+row+"]/td[" + col + "]");
			String sTr=  ele.getText();
			return sTr;
		}
		
		public int[] getRowAndColumnNumberWithCellText(String text)
		{
			int r[] = new int[2];
			r[0] = 0;
			r[1] = 0;
			boolean Flag = false;
			
			for(int i=1; i<=this.rows && Flag == false; i++)
				for(int j=1; j<=this.columns; j++)
				{
					 
					String sTr=  getCellText(i,j);
					 
					if(sTr.equalsIgnoreCase(text))
					{ 
						r[0]=i; r[1]=j;
						Flag = true;
						break;
					
					}
				}
			
			
				return r; 	 
		}
		
		public int[][] getMultipleRowAndColumnNumberWithCellText(String text)
		{
			
			int r[][] = new int[this.rows][this.columns];
			 
			boolean Flag = false;
			int vR=0,vC=0;
			
			for(int i=1; i<=this.rows && Flag == false; i++)
			{
				for(int j=1; j<=this.columns; j++)
				{
					 
					String sTr=  getCellText(i,j);;
					 
					if(sTr.equalsIgnoreCase(text))
					{ 
						r[vR][0] = i;
						r[vR][vC++] = j;
					
					}
					
				}
				vR++;
			}
				return r; 	 
		}

		 

		@Override
		public void click() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void CaptureScreenshot(String filepath) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Boolean isEnabled() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public  SeWebElement getSeWebElement()
		{
			return element;
		}
}
