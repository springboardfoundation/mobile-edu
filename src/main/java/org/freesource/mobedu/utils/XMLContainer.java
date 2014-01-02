/**
 * 
 */
package org.freesource.mobedu.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Container for XML, this has to work independently to handle XMLs
 */
public class XMLContainer implements Constants {

	// Using DOM parser to parse the XML
	DocumentBuilderFactory dbf;
	DocumentBuilder db;
	Document doc;
	NodeList childNodes;

	public XMLContainer(InputStream in) throws ParserConfigurationException,
			SAXException, IOException {
		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		doc = db.parse(in);
		childNodes = doc.getChildNodes();
	}

	private static String getTagValue(String sTag, Element eElement) {
		try {
			NodeList nodeList = eElement.getElementsByTagName(sTag).item(0)
					.getChildNodes();
			Node node = nodeList.item(0);
			return node.getNodeValue();
		} catch (Exception e) {// If sTag does not exist, return null
			return null;
		}
	}

	public String getTagValue(String tag) {
		String result = "";
		for (int index = 0; index < childNodes.getLength(); index++) {
			Node childNode = childNodes.item(index);
			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) childNode;
				result = getTagValue(tag, element);
				if (null != result) {
					break;
				}
			}
		}
		return result;
	}
}
