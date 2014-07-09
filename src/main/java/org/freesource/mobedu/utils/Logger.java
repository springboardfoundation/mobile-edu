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

/**
 * Abstraction for Logger
 * 
 */
public class Logger {

	private static Logger LOGGER = null;
	private static String className;

	private Logger() {
		// intentional

	}

	public static Logger getInstance(String moduleName) {
		if (LOGGER == null) {
			LOGGER = new Logger();
			className = moduleName;
		}
		return new Logger();
	}

	public void debug(String s) {

//		if ("true".equalsIgnoreCase(System.getProperty("VERBOSE"))) {
			print(s);
//		}

	}

	public void info(String s) {
		print(s);
	}

	public void error(String s) {
		print(s);
	}

	public void error(String s, Throwable t) {
		print(s);
		print(t);
	}

	private static void print(String s) {
		System.out.println("["+ className +"]" + s);
	}

	private static void print(Throwable t) {
		print("[" +className +"] Error trace------------");
		t.printStackTrace();
		print("[" +className +"] Error trace------------");
	}
}
