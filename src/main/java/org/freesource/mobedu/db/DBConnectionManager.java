/**
 * 
 */
package org.freesource.mobedu.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.freesource.mobedu.dao.Users;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.MobileEduException;

/**
 * The DB handler class that has to save, retrieve db entities as required. This
 * needs to be written in such a way that any DB change will required a very
 * minimal change here
 */
public class DBConnectionManager implements Constants {

	private static final String DB_CONFIG_FILE = DB_PROP_FILE;
	private static Properties dbProp = null;
	private Connection conn = null;
	private DBAccessor theDBA = null;

	/**
	 * The constructor is kept public to allow for multiple instances of it so
	 * that we can work with more than one DB at the same time
	 * 
	 * @throws MobileEduException
	 */
	public DBConnectionManager(String type) throws MobileEduException {
		if (type.equals(DB4_TYPE)) {
			String dburi = getDB4_URI();
			if (!connect(dburi, MYSQL_DRIVER_CLASS, getDBProperty(DB4_USER),
					getDBProperty(DB4_PASS))) {
				throw new MobileEduException("Unable to get DB connection!");
			}
			theDBA = MySQLDataAccessor.getInstance(conn);
			System.out.println("Success in connecting to the DB");
		}
	}

	private String getDB4_URI() {
		StringBuffer dbURI = new StringBuffer("jdbc:mysql://");
		dbURI.append(getDBProperty(DB4_HOST));
		dbURI.append(":");
		dbURI.append(getDBProperty(DB4_PORT));
		dbURI.append("/");
		dbURI.append(getDBProperty(DB4_NAME));
		return dbURI.toString();
	}

	private boolean connect(String uri, String dbClass, String user, String pass) {
		boolean done = false;
		// load driver
		try {
			Class.forName(dbClass).newInstance();
			System.out.println("driver loaded");
		} catch (ClassNotFoundException ex) {
			System.err.println(ex);
		} catch (InstantiationException ex) {
			System.err.println(ex);
		} catch (IllegalAccessException ex) {
			System.err.println(ex);
		}
		// Connection
		try {
			if (null == conn || !conn.isValid(TENTH)) {
				conn = DriverManager.getConnection(uri, user, pass);
				System.out.println("connected");
				done = true;
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		}
		return done;
	}

	private static void loadDBProperties() {
		dbProp = new Properties();
		try {
			dbProp.load(DBConnectionManager.class.getClassLoader()
					.getResourceAsStream(DB_CONFIG_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getDBProperty(String key) {
		if (dbProp == null) {
			loadDBProperties();
		}
		return dbProp.getProperty(key);
	}

	public Users saveNewUser(Users newUser) throws SQLException,
			MobileEduException {
		return theDBA.saveUser(newUser);
	}

	public Users searchForUser(String mobileId) throws SQLException,
			MobileEduException {
		return theDBA.getUser(mobileId);
	}

	public Users updateUser(Users existingUser) throws SQLException,
			MobileEduException {
		return theDBA.updateUserDetails(existingUser);
	}

	/**
	 * Destructor method to close the DB connection if still open when the
	 * object is garbage collected. This may or maynot be called
	 */
	protected void finalize() {
		log.debug("Finalize called.");
		try {
			if (conn != null && !conn.isClosed()) {
				log.error("Connection is still open! Calling close()");
				close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() throws SQLException {
		if (!conn.isClosed()) {
			conn.close();
			conn = null;
		}
	}
}
