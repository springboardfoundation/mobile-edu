/**
 * 
 */
package org.freesource.mobedu.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

/**
 * All methods here are for general utility purpose All methods in this class
 * have to be public static and final
 */
public class Utilities implements Constants {
	private static String classMsg = "";
	private static Properties stdProp = null;
	private static List<String> listOfClass = new ArrayList<String>();

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
			classMsg = "No class given for registration, taking " + defaultStd
					+ "std as default.<br />";
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

	public static final String getCurrentTimestamp() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String d = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss").format(c
				.getTime());
		return d;
	}

	private static void loadDBProperties() {
		stdProp = new Properties();
		try {
			stdProp.load(Utilities.class.getClassLoader().getResourceAsStream(
					STD_PROP_FILE));
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
		String s = getStdProperty(SUPPORTED_STR_LIST);
		log.debug("SUPPORTED_STR_LIST:" + s);
		return s;
	}

	public static String getDefaultStdClass() {
		String s = getStdProperty(DEFAULT_CLASS);
		log.debug("DEFAULT_CLASS:" + s);
		return s;
	}

}
