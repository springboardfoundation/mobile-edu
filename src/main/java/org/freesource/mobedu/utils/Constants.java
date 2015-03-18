/**
 * 
 */
package org.freesource.mobedu.utils;

/**
 * Interface to store all the constants needed for the project
 */
public interface Constants {

	// Application level logger for logging
	Logger appLogger = Logger.getInstance("Mobile Education");
	String LOG_LEVEL = "LOGLEVEL";
	String LOG_TRACE = "trace";
	String LOG_DEBUG = "debug";
	String LOG_ERROR = "error";
	String LOG_INFO = "info";
	
	String CLASS_STR_LIST = "class_string_list";
	String CLASS_INT_LIST = "class_number_list";
	String SUPPORTED_STR_LIST = "supported_class_string_list";
	String DEFAULT_CLASS = "default_class";
	// List<String> (LIST_OF_SUPPORTED_STD = new
	// ArrayList<String>()).add(TENTH_CLASS);
	String UNREGISTER = "STOP";
	String ERROR_MSG = "ERROR";

	String TXTWEB_MOBILE_NUMBER = "92665 92665";
	String TXT_APPKEY_NAME = "txtweb-appkey";
	String TXT_WEBID_NAME = "txtweb-id";
	String TXT_PUBLISHERID_NAME = "txtweb-pubkey";
	// String APPKEY_CONTENT = "42c5f773-6bd7-47f7-81cc-3a9dd4367683";
	String TXT_SERVICE_APP_KEY = "89e2b13a-073f-4814-8ffc-b7e40d632550";
	String TXT_PUBLISHER_ID = "0a20a054-7183-4a9d-b856-0c84342eb531";

	String LOCATION_API_URL = "http://api.txtweb.com/v1/location/get";
	String PUSH_MSG_URL = "http://api.txtweb.com/v1/push";
	String LOCAL_PUSH_MSG_URL = "http://localhost:8080/test";

	String HTTP_PARAM_TXTWEB_MOBILE = "txtweb-mobile";
	String HTTP_PARAM_TXTWEB_MESSAGE = "txtweb-message";
	String HTTP_PARAM_TXTWEB_ID = "txtweb-id";
	String HTTP_PARAM_TXTWEB_PROTOCOL = "txtweb-protocol";
	String HTTP_PARAM_PUBLISHER_KEY = "txtweb-pubkey";
	String HTTP_PARAM_MESSAGE = "message";
	String HTTP_PARAM_USER = "userid";
	String EMULATOR = "2100";

	String SUCCESS_CODE = "0";

	String DB_TYPE = "db_type";
	int DB_VALID_CHECK_TIMEOUT = 30;

	// Standard Error Message reply
	String DEFAULT_REGISTRATION_ERR_MSG = "Registration Failed, please try after sometime";

	String DB4_TYPE = "db_type";
	String DB4_NAME = ".database";
	String DB4_USER = ".user";
	String DB4_PASS = ".pass";
	String DB4_HOST = ".host";
	String DB4_PORT = ".port";

	String DB_PROP_FILE = "db.properties";
	String STD_PROP_FILE = "std.properties";
	String PROPERTIES_FILE_EXTENSION = "properties";
	String XML_FILE_EXTENSION = "xml";
	String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	/*
	 * DB query constants. Prefix with Q_ to mark as query
	 */
	String Q_MAX_CONTEXTID = "SELECT MAX(CONTEXT_ID) CONTEXT_ID FROM USER_CONTEXT";
	String Q_INSERT_NEW_USER = "INSERT INTO USER_CONTEXT (CONTEXT_ID, MOBILE_HASH, REG_STD, REG_SUB, REG_DATE, IS_ACTIVE, LOCATION, PROTOCOL) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	String Q_SELECT_USER_WITH_MOBILEHASH = "SELECT CONTEXT_ID, MOBILE_HASH, REG_STD, REG_SUB, REG_DATE, IS_ACTIVE, LOCATION, PROTOCOL FROM USER_CONTEXT WHERE MOBILE_HASH = ";
	String Q_SELECT_ALL_USERS = "SELECT CONTEXT_ID, MOBILE_HASH, REG_STD, REG_SUB, REG_DATE, IS_ACTIVE, LOCATION, PROTOCOL FROM USER_CONTEXT";
	String Q_SELECT_ALL_ACTIVE_USERS = "SELECT CONTEXT_ID, MOBILE_HASH, REG_STD, REG_SUB, REG_DATE, IS_ACTIVE, LOCATION, PROTOCOL FROM USER_CONTEXT " +
			"WHERE IS_ACTIVE = true AND CONTEXT_ID NOT IN (SELECT CONTEXT_ID FROM USER_CONTEXT WHERE PROTOCOL = '2100')";
	String Q_UPDATE_USER_WITH_ID = "UPDATE USER_CONTEXT SET REG_STD = ?, REG_SUB = ?, REG_DATE = ?, IS_ACTIVE = ?, LOCATION = ?, PROTOCOL = ? WHERE CONTEXT_ID = ?";
}