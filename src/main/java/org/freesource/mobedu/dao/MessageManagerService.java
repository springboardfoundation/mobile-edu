/**
 * 
 */
package org.freesource.mobedu.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.model.Message;
import org.freesource.mobedu.dao.model.User;

/**
 * 
 * 
 */
public interface MessageManagerService {

	/**
	 * Method to send message to all the users
	 * 
	 * @param req
	 * @param res
	 * @param messageObject
	 * @return
	 */
	public String sendMessageToAllUsers(HttpServletRequest req,
			HttpServletResponse res, Message messageObject);

	/**
	 * Method to send message to a particular user the users
	 * 
	 * @param req
	 * @param res
	 * @param messageObject
	 * @return
	 */
	public String sendMessageToUser(HttpServletRequest req,
			HttpServletResponse res, Message messageObject, User user);

	/**
	 * Save DB to the user and write the response of success out to the server
	 * 
	 * @param user
	 */
	public String saveMessageToDB(User user);

}
