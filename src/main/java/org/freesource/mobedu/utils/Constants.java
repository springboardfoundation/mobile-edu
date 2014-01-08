/**
 * 
 */
package org.freesource.mobedu.utils;

/**
 * Interface to store all the constants needed for the project
 */
public interface Constants {

	// Logger for logging
	Logger log = Logger.getInstance("Mobile Education");
	int TENTH = 10;

	String APPKEY_NAME = "txtweb-appkey";
	String APPKEY_CONTENT = "42c5f773-6bd7-47f7-81cc-3a9dd4367683";

	String LOCATION_API_URL = "http://api.txtweb.com/v1/location/get";

	String HTTP_PARAM_TXTWEB_MOBILE = "txtweb-mobile";
	String HTTP_PARAM_TXTWEB_MESSAGE = "txtweb-message";
	String HTTP_PARAM_TXTWEB_ID = "txtweb-id";

	String SUCCESS_CODE = "0";

	String DB_TYPE = "db_type";

	String DB4_TYPE = "db4free";
	String DB4_NAME = "db4free.database";
	String DB4_USER = "db4free.user";
	String DB4_PASS = "db4free.pass";
	String DB4_HOST = "db4free.host";
	String DB4_PORT = "db4free.port";

	String DB_PROP_FILE = "db.properties";
	String PROPERTIES_FILE_EXTENSION = "properties";
	String XML_FILE_EXTENSION = "xml";
	String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	/*
	 * DB query constants. Prefix with Q_ to mark as query
	 */
	String Q_MAX_CONTEXTID = "SELECT MAX(CONTEXT_ID) CONTEXT_ID FROM USER_CONTEXT";
	String Q_INSERT_NEW_USER = "INSERT INTO USER_CONTEXT (CONTEXT_ID, MOBILE_HASH, REG_STD, REG_DATE, IS_ACTIVE, LOCATION) VALUES (?, ?, ?, ?, ?, ?)";
}

/**
 * What are the parameters that the platform sends to your application?
 * There are 5 parameters that the platform sends to an application viz:
 * txtweb-mobile: The mobile number of the end user in hash format
 * txtweb-message: Message sent by the end user
 * txtweb-id: Unique identifier for the message
 * txtweb-verifyid: The id used to verify the source. Check API documentation for more details
 * txtweb-protocol: The protocol through which the message was received:
 *   SMS: 1000
 *   USSD: 1001
 *   WEB: 200x
 *   EMULATOR: 2100
 *   INSTANT MESSENGER: 220x
 *   MISSED CALL:1002
 *   ANDROID: 3000
*/