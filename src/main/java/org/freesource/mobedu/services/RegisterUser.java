/**
 * 
 */
package org.freesource.mobedu.services;

import org.freesource.mobedu.dao.Users;
import org.freesource.mobedu.utils.Constants;

/**
 * The class will do all the required verifications and then store the user to
 * the DB
 */
public class RegisterUser implements Constants {

	Users regUser;

	public RegisterUser(Users rUser) {
		regUser = rUser;
	}

}
