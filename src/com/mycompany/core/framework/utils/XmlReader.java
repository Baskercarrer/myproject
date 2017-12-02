package com.mycompany.core.framework.utils;

import java.io.File;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlReader {

	public Document document = null;
	String rowTag = "";

	public XmlReader(String filePath) {
		this(new File(filePath));
	}

	public XmlReader(File file) {
		this.readXmlFile(file);
	}

	public void readXmlFile(File file) {

		DocumentBuilderFactory factory;
		DocumentBuilder builder = null;
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();

			// setting document object for reusability
			this.document = builder.parse(file);

		} catch (Exception e) {

			System.out.println("Exception occured while parsing xml file.");
			e.printStackTrace();
		}
	}

	/**
	 * Get the number of rows in the said xml file. Default parameter to be used
	 * <code>testdatarow</code>
	 * 
	 * @return Return no of rows present in the said xml file
	 */
	public int getNoOfRows() {
		return document.getElementsByTagName("testdata").getLength();
	}

	/**
	 * Return the number of column in the first row of the xml file
	 * 
	 * @return return the no. of column in the first row of the xml file
	 */
	public int getNoOfColumns() {
		return this.getNoOfColumns(0);
	}

	/**
	 * Returns the number of the column of the said row number passed
	 * 
	 * @param rowNo
	 *            - row number for which the number columns needs to be
	 *            returned.
	 * @return return the no. of column in the specified row of the csv file
	 */
	public int getNoOfColumns(int rowNo) {
		Element element = (Element) document.getElementsByTagName("testdata")
				.item(rowNo);
		return element.getElementsByTagName("column").getLength();
	}

	public String getColumnName(int row, int col) {
		Element element = (Element) document.getElementsByTagName("testdata")
				.item(row);
		return element.getElementsByTagName("column").item(col).getAttributes()
				.getNamedItem("name").getTextContent();
	}

	public String getColumnValue(int row, int col) {
		Element element = (Element) document.getElementsByTagName("testdata")
				.item(row);
		return element.getElementsByTagName("column").item(col)
				.getTextContent();
	}

	public int getColumnNo(String ComlumnName) {

		Element element = (Element) document.getElementsByTagName("testdata")
				.item(0);

		for (int i = 0; i < getNoOfColumns(); i++) {

			if (element.getElementsByTagName("column").item(i).getNodeName()
					.equalsIgnoreCase(ComlumnName)) {
				return i;
			}

		}

		return -1;
	}

	public int getRowNo(String ColumnName, String ColumnVal) {
		int ColNo = getColumnNo(ColumnName);
		int rown = getNoOfRows();

		for (int i = 0; i < rown; i++) {
			Element element = (Element) document.getElementsByTagName(
					"testdata").item(i);
			if (element.getElementsByTagName("column").item(ColNo)
					.getNodeValue().equalsIgnoreCase(ColumnVal)) {
				return i;
			}

		}
		return -1;
	}

	/**
	 * Reads data from the xml file and stores in a properties object to return
	 * 
	 * @param node
	 * @param nodeName
	 * @return Properties
	 */
	public Properties getProperties(String node, String nodeName) {

		// Get a list of all suites in the file
		NodeList list = document.getElementsByTagName(node);

		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);

			if (element.getAttribute("name").equals(nodeName)) {
				return getNodeInfo(list.item(i));
			}
		}

		return null;
	}

	/**
	 * Reads and stores all the child elements and returns as a properties
	 * object
	 * 
	 * @param elem
	 *            - DOM Node
	 * 
	 * @return Properties
	 */

	@SuppressWarnings("static-method")
	private Properties getNodeInfo(final Node elem) {
		Node kid;
		final Properties suiteProp = new Properties();

		if ((elem != null) && elem.hasChildNodes()) {
			for (kid = elem.getFirstChild(); kid != null; kid = kid
					.getNextSibling()) {
				if (kid.getNodeType() == 1) {
					suiteProp.setProperty(kid.getNodeName(),
							kid.getTextContent());
				}
			}
		}

		return suiteProp;
	}

}
