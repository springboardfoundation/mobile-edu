/**
 * 
 */
package org.freesource.mobedu.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.freesource.mobedu.dao.Users;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.MobileEduException;
import org.freesource.mobedu.utils.Utilities;

/**
 *
 */
public class MySQLDataAccessor implements DBAccessor, Constants {

	private static MySQLDataAccessor thisObject = null;
	private static Connection thisConn = null;

	/**
	 * Private constructor to restrict the creating of only one DBA for MySQL
	 * server
	 */
	private MySQLDataAccessor() {
	}

	public static MySQLDataAccessor getInstance(Connection conn)
			throws MobileEduException {
		if (null == conn) {
			throw new MobileEduException(
					"DB connection passed to MySQLDataAccessor is null!");
		}
		if (null == thisObject) {
			thisObject = new MySQLDataAccessor();
			thisConn = conn;
		}
		return thisObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freesource.mobedu.db.DBAccessor#saveUser()
	 */
	public Users saveUser(Users user) throws SQLException, MobileEduException {
		int nextContextID = getNextContextID();
		if (-1 == nextContextID) {
			throw new MobileEduException(
					"MySQLDataAccessor: Unable to get the value for next CONTEXT_ID from table: USER_CONTEXT.");
		}

		// Allocate a "PreparedStatement" object in the Connection
		PreparedStatement pstmt = thisConn.prepareStatement(Q_INSERT_NEW_USER);
		log.debug("Statement got:" + Q_INSERT_NEW_USER);

		pstmt.setInt(1, nextContextID); // CONTEXT_ID
		pstmt.setString(2, user.getMobileId()); // MOBILE_HASH
		pstmt.setString(3, user.getRegStd()); // REG_STD

		String timestamp = Utilities.getCurrentTimestamp();
		pstmt.setString(4, timestamp); // REG_DATE
		pstmt.setBoolean(5, true); // IS_ACTIVE
		pstmt.setString(6, user.getLocation()); // LOCATION

		log.debug("INSERTing a record");
		int countUpdated = pstmt.executeUpdate();
		if (0 == countUpdated) {
			throw new MobileEduException(
					"MySQLDataAccessor: Unable to save to DB, reason unknown...!");
		}
		pstmt.close();
		return user;
	}

	private int getNextContextID() throws SQLException {
		Statement stmt = thisConn.createStatement();
		// Issue a SELECT to get the next context id
		String strSelect = Q_MAX_CONTEXTID;
		log.debug("The SQL query is: " + strSelect); // For debugging
		ResultSet rset = stmt.executeQuery(strSelect);
		while (rset.next()) {
			int contextId = rset.getInt("CONTEXT_ID");
			System.out.println("The CONTEXT_ID: " + contextId);
			return (contextId + 1);
		}
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freesource.mobedu.db.DBAccessor#getUser(java.lang.String)
	 */
	public Users getUser(String txtMobileHash) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		Users u = new Users();
		u.setMobileId("3594jv-3534-bv34c34c3-5343534");
		u.setRegStd("10th");

		MySQLDataAccessor d = new MySQLDataAccessor();
		try {
			d.saveUser(u);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MobileEduException e) {
			e.printStackTrace();
		}
	}
}
