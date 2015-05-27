/*
 * Copyleft Engineers for Society, FreeSource.org, All rights unreserved.
 *
 * This is unpublished proprietary source code of FreeSource
 * The copyright notice does not evidence any actual or intended publication of such
 * source code.
 *
 * Created on: Jan 01, 2014
 *
 * Revision History:
 * --------------------------------------------------------------------
 */
package org.freesource.mobedu.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstraction for Logger
 */
public class Logger implements Constants {

	private static Map<String, Logger> listOfLoggers = new HashMap<String, Logger>();

	// private static Logger LOGGER = null;
	private String className;
	private String classlogLevel;
	private static String loglevel;

	private Logger() {
		// intentional
	}

	public static Logger getInstance(String moduleName) {

		if (null == loglevel || loglevel.isEmpty()) {
			loglevel = System.getProperty(LOG_LEVEL);
			if (null == loglevel || loglevel.isEmpty()) {
				loglevel = System.getenv(LOG_LEVEL);
			}
			// Default log level is set to error
			if (null == loglevel || loglevel.isEmpty()) {
				loglevel = LOG_TRACE;
			}
		}

		if (listOfLoggers.get(moduleName) == null) {
			synchronized (Logger.class) {
				if (listOfLoggers.get(moduleName) == null) {
					Logger thisLogger = new Logger();
					thisLogger.className = moduleName;
					thisLogger.setLogLevel(loglevel);
					listOfLoggers.put(moduleName, thisLogger);
				}
			}
		}

		return listOfLoggers.get(moduleName);
	}

	public void setLogLevel(String level) {
		classlogLevel = level;
	}

	public void debug(String s) {
		if (classlogLevel.equalsIgnoreCase(LOG_DEBUG)
				|| classlogLevel.equalsIgnoreCase(LOG_TRACE)
				|| classlogLevel.equalsIgnoreCase(LOG_INFO)) {
			print(s);
		}
	}

	public void trace(String s) {
		if (classlogLevel.equalsIgnoreCase(LOG_INFO)
				|| classlogLevel.equalsIgnoreCase(LOG_TRACE)) {
			print(s);
		}
	}

	public void info(String s) {
		if (classlogLevel.equalsIgnoreCase(LOG_INFO)) {
			print(s);
		}
	}

	public void error(String s) {
		print(s);
	}

	public void error(String s, Throwable t) {
		print(s);
		print(t);
	}

	private void print(String s) {
		System.out.println("[" + className + "]" + s);
	}

	private void print(Throwable t) {
		print("[" + className + "] Error trace------------");
		t.printStackTrace();
		print("[" + className + "] Error trace------------");
	}
}
