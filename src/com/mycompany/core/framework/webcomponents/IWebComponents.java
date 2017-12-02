package com.mycompany.core.framework.webcomponents;

import com.mycompany.core.framework.webdriver.SeWebElement;

 
/**
 *  
 *
 * @author UST-TACOE 
 * @version 1.0
 */
public interface IWebComponents
    
{
    //~ Methods --------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    public void click();


    /**
     * DOCUMENT ME!
     */
    public Boolean isExist();


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
   
    public String getProperty(String prp);
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
  
    public String getFindBy();
    
   
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public void CaptureScreenshot(String filepath);
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getLocator();
    

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Boolean isEnabled();
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
 
    public  SeWebElement getSeWebElement();
}
