/**
 * 
 */
package org.freesource.mobedu.db;

import java.sql.SQLException;

import org.freesource.mobedu.dao.Users;
import org.freesource.mobedu.utils.MobileEduException;

/**
 * The interface that has to be implemented by all the DB accessor methods
 * Always use this interface to access any DB functions
 */
public interface DBAccessor {
	Users saveUser(Users user) throws SQLException, MobileEduException;
	Users getUser(String txtMobileHash) throws SQLException, MobileEduException;
	Users updateUserDetails(Users user) throws SQLException, MobileEduException;
	Users updateUserStd(Users user) throws SQLException, MobileEduException;
	Users activateUser(Users user) throws SQLException, MobileEduException;
	Users deActivateUser(Users user) throws SQLException, MobileEduException;
}
