/**
 * 
 */
package org.freesource.mobedu.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.freesource.mobedu.dao.Users;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.MobileEduException;

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

		pstmt.setInt(1, nextContextID); // CONTEXT_ID
		pstmt.setString(2, user.getMobileId()); // MOBILE_HASH
		pstmt.setString(3, user.getRegStandard()); // REG_STD
		pstmt.setString(4, user.getRegSubject()); // REG_SUB

		pstmt.setString(5, user.getRegDate()); // REG_DATE
		if (user.isActive())// IS_ACTIVE
			pstmt.setBoolean(6, true);
		else
			pstmt.setBoolean(6, false);
		pstmt.setString(7, user.getLocation()); // LOCATION
		pstmt.setString(8, user.getProtocol()); // PROTOCOL

		log.debug("Statement object:" + pstmt.toString());
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
		log.debug("The max select SQL query is: " + strSelect); // For debugging
		ResultSet rset = stmt.executeQuery(strSelect);
		if (rset.next()) {
			int contextId = rset.getInt("CONTEXT_ID");
			stmt.close();
			return (contextId + 1);
		}
		stmt.close();
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freesource.mobedu.db.DBAccessor#getUser(java.lang.String)
	 */
	public Users getUser(String txtMobileHash) throws SQLException {
		Users user = new Users();
		Statement stmt = thisConn.createStatement();
		String strSelect = Q_SELECT_USER_WITH_MOBILEHASH + "'" + txtMobileHash
				+ "'";
		log.debug("The select SQL query is: " + strSelect);
		ResultSet rset = stmt.executeQuery(strSelect);
		if (rset.next()) {
			user.setContextId(rset.getInt("CONTEXT_ID"));
			user.setMobileId(rset.getString("MOBILE_HASH"));
			user.setRegStandard(rset.getString("REG_STD"));
			user.setRegSubject(rset.getString("REG_SUB"));
			user.setRegDate(rset.getString("REG_DATE"));
			if (rset.getBoolean("IS_ACTIVE")) {
				user.activateUser();
			} else {
				user.deActivateUser();
			}
			user.setLocation(rset.getString("LOCATION"));
			user.setProtocol(rset.getString("PROTOCOL"));
		} else {
			stmt.close();
			return null;
		}
		stmt.close();
		return user;
	}

	public static void main(String[] args) {
		Users u = new Users();
		u.setMobileId("3594jv-3534-bv34c34c3-5343534");
		u.setRegStandard("10th");

		MySQLDataAccessor d = new MySQLDataAccessor();
		try {
			d.saveUser(u);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MobileEduException e) {
			e.printStackTrace();
		}
	}

	public Users updateUserDetails(Users user) throws SQLException,
			MobileEduException {
		PreparedStatement pstmt = thisConn
				.prepareStatement(Q_UPDATE_USER_WITH_ID);

		pstmt.setString(1, user.getRegStandard()); // REG_STD
		pstmt.setString(2, user.getRegSubject()); // REG_SUB
		pstmt.setString(3, user.getRegDate()); // REG_DATE
		if (user.isActive())// IS_ACTIVE
			pstmt.setBoolean(4, true);
		else
			pstmt.setBoolean(4, false);

		pstmt.setString(5, user.getLocation()); // LOCATION
		pstmt.setString(6, user.getProtocol()); // PROTOCOL

		pstmt.setInt(7, user.getContextId()); // CONTEXT_ID

		log.debug("Statement object:" + pstmt.toString());
		log.debug("UPDATEing a record");

		int countUpdated = pstmt.executeUpdate();
		if (0 == countUpdated) {
			throw new MobileEduException(
					"MySQLDataAccessor: Unable to save to DB, reason unknown...!");
		}
		pstmt.close();
		return user;
	}

	public List<Users> getListOfRegisteredUsers() throws SQLException,
			MobileEduException {
		List<Users> allUsers = new ArrayList<Users>();
		Statement stmt = thisConn.createStatement();
		String strSelect = Q_SELECT_ALL_ACTIVE_USERS;
		log.debug("All users select SQL query is: " + strSelect);

		// Execute the query and iterate full result set to get all users, add
		// all uses to the list, exception thrown if no users found
		ResultSet rset = stmt.executeQuery(strSelect);
		while (rset.next()) {
			Users user = new Users();
			user.setContextId(rset.getInt("CONTEXT_ID"));
			user.setMobileId(rset.getString("MOBILE_HASH"));
			user.setRegStandard(rset.getString("REG_STD"));
			user.setRegSubject(rset.getString("REG_SUB"));
			user.setRegDate(rset.getString("REG_DATE"));
			if (rset.getBoolean("IS_ACTIVE")) {
				user.activateUser();
			} else {
				user.deActivateUser();
			}
			user.setLocation(rset.getString("LOCATION"));
			user.setProtocol(rset.getString("PROTOCOL"));
			allUsers.add(user);
		}
		stmt.close();
		if(allUsers.isEmpty()){
			throw new MobileEduException(
					"Getting List of Users: No registered users found");
		}
		return allUsers;
	}

}
