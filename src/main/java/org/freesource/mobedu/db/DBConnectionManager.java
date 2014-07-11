/**
 * 
 */
package org.freesource.mobedu.db;

import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Logger;
import org.freesource.mobedu.utils.MobileEduException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The DB handler class that has to save, retrieve db entities as required. This
 * needs to be written in such a way that any DB change will required a very
 * minimal change here
 */
public class DBConnectionManager implements Constants {

	private Logger log = Logger.getInstance("DBConnectionManager");

	ApplicationContext ctx = null;
	private static DBConnectionManager thisObj;

	/**
	 * The constructor is kept public to allow for multiple instances of it so
	 * that we can work with more than one DB at the same time
	 * 
	 * @throws MobileEduException
	 */
	private DBConnectionManager() throws MobileEduException {
		log.debug("DBConnectionManager: Trying to load Application Context");
		ctx = new ClassPathXmlApplicationContext("SpringBeans.xml");
		log.debug("Success in connecting to the DB");
	}

	public static DBConnectionManager getInstance() throws MobileEduException {
		if (thisObj == null) {
			synchronized (DBConnectionManager.class) {
				if (thisObj == null) {
					thisObj = new DBConnectionManager();
				}
			}
		}
		return thisObj;
	}

	public Object getUserBean(String beanName) {
		return ctx.getBean(beanName);
	}

}
