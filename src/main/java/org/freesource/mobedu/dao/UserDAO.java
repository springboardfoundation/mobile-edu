/**
 * 
 */
package org.freesource.mobedu.dao;

import java.util.List;

import org.freesource.mobedu.dao.model.User;

/**
 * DAO for user accessing functions
 * 
 */
public interface UserDAO {

	void insertUser(User user);
	
	void update(User user);

	User getUserById(int userId);
	
	User getUserByMobileHash(String mobileHash);

	long getNumberOfUser();
	
	int getMaxContextId();

	List<User> getUsers();
}
