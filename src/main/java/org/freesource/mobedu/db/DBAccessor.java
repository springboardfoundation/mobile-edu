/**
 * 
 */
package org.freesource.mobedu.db;

import java.sql.SQLException;
import java.util.List;

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
	List<Users> getListOfRegisteredUsers()  throws SQLException, MobileEduException;
}
