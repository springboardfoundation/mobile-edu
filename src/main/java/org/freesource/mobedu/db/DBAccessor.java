/**
 * 
 */
package org.freesource.mobedu.db;

import java.sql.SQLException;
import java.util.List;

import org.freesource.mobedu.dao.model.User;
import org.freesource.mobedu.utils.MobileEduException;

/**
 * The interface that has to be implemented by all the DB accessor methods
 * Always use this interface to access any DB functions
 */
public interface DBAccessor {
	User saveUser(User user) throws SQLException, MobileEduException;
	User getUser(String txtMobileHash) throws SQLException, MobileEduException;
	User updateUserDetails(User user) throws SQLException, MobileEduException;
	List<User> getListOfRegisteredUsers()  throws SQLException, MobileEduException;
}
