/**
 * 
 */
package org.freesource.mobedu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * Class to hold the configuration given in a file. This class has to
 * independently handle .properties, .xml, .csv files
 * 
 */
public class ConfigContainer implements Constants {
	private static ConfigContainer c = null;
	private Properties prop;
	private String extension;

	private ConfigContainer() {
		// Intentional private constructor
	}

	private void loadPropertiesFile(Reader inStream) throws IOException {
		prop = new Properties();
		prop.load(inStream);
	}

	private void loadXmlPropertiesFile(InputStream in) throws IOException {
		prop = new Properties();
		prop.loadFromXML(in);
	}

	public static ConfigContainer getConfigContainer(String fileName)
			throws FileNotFoundException, IOException {
		if (c == null) {
			c = new ConfigContainer();
			File f = new File(fileName);
			if (!f.exists() || f.isDirectory()) {
				System.out
						.println("Given file is a directory or does not exist");
				return null;
			}
			c.extension = fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length());
			if (c.extension.equalsIgnoreCase(PROPERTIES_FILE_EXTENSION)) {
				c.loadPropertiesFile(new FileReader(f));
			} else if (c.extension.equalsIgnoreCase(XML_FILE_EXTENSION)) {
				c.loadXmlPropertiesFile(new FileInputStream(f));
			}
		}
		return c;
	}

	public String getPropertyValue(String key) {
		return prop.getProperty(key);
	}

}
