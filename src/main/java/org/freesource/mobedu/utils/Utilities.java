/**
 * 
 */
package org.freesource.mobedu.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * All methods here are for general utility purpose All methods in this class
 * have to be public static and final
 */
public class Utilities implements Constants {
	private static String classMsg = "";
	private static Properties stdProp = null;
	private static List<String> listOfClass = new ArrayList<String>();
	private static Logger log = Logger.getInstance("Utilities");

	// Static block to populate the list of supported classes from the
	// properties file.
	static {
		String strClass = getStdProperty(SUPPORTED_STR_LIST);
		String[] arrClass = strClass.split(",");
		for (String string : arrClass) {
			listOfClass.add(string.trim().toLowerCase());
		}
	}

	/**
	 * For verifying and getting the class for which the user registers
	 * 
	 * @param cl
	 * @return
	 */
	public static final String getStdClass(String cl) {
		String value = "";
		if (null == cl || cl.isEmpty()) {
			String defaultStd = getStdProperty(DEFAULT_CLASS);
			classMsg = "No class given for registration, taking " + defaultStd + " std as default.<br />";
			return defaultStd;
		}
		String normalized = cl.trim().toLowerCase();
		if (listOfClass.contains(normalized)) {
			value = normalized;
			classMsg = "Registered for " + cl + " Std successfully.<br />";
		} else {
			value = "";
			classMsg = "Invalid standard. ";
		}
		log.debug(classMsg);
		return value;
	}

	public static final String getStdReplyMessage() {
		return classMsg;
	}

	public static final Date getCurrentTimestamp() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
		// .format(c.getTime());
		Date d;
		try {
			d = formatter.parse(formatter.format(c.getTime()));
		} catch (ParseException e) {
			log.debug("Error occurred when executing: getCurrentTimestamp");
			e.printStackTrace();
			d = new Date();
		}
		return d;
	}

	private static void loadDBProperties() {
		stdProp = new Properties();
		try {
			stdProp.load(Utilities.class.getClassLoader().getResourceAsStream(STD_PROP_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getStdProperty(String key) {
		if (stdProp == null) {
			loadDBProperties();
		}
		return stdProp.getProperty(key);
	}

	public static String getListOfSupportedClass() {
		return getStdProperty(SUPPORTED_STR_LIST);
	}

	public static String getDefaultStdClass() {
		return getStdProperty(DEFAULT_CLASS);
	}

	public static void printXML(Document doc, OutputStream out) throws IOException {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;

			transformer = tf.newTransformer();

			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
		} catch (TransformerConfigurationException e) {
			log.debug("Unable to print the XML");
			e.printStackTrace();
		} catch (TransformerException e) {
			log.debug("Unable to print the XML");
			e.printStackTrace();
		}
	}

}
