/**
 * 
 */
package org.freesource.mobedu.services;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.Message;
import org.freesource.mobedu.dao.Users;
import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.MobileEduException;
import org.freesource.mobedu.utils.ResponseMessageHandler;
import org.freesource.mobedu.utils.Utilities;

/**
 *
 */
public class MessageHandlerService implements Constants {

	DBConnectionManager dm;

	/**
	 * @throws MobileEduException
	 * 
	 */
	public MessageHandlerService() throws MobileEduException {
		dm = new DBConnectionManager();
	}

	/**
	 * Method to send message to all the users
	 * 
	 * @param req
	 * @param res
	 * @param messageObject
	 * @return
	 */
	public String sendMessageToAllUsers(HttpServletRequest req,
			HttpServletResponse res, Message messageObject) {
		StringBuilder message = new StringBuilder();
		List<Users> users = null;
		try {
			// log.debug("Getting list of all users from dB");
			users = dm.getAllRegisteredUsers();
			log.debug("Got " + users.size() + " number of users from dB");
		} catch (SQLException e) {
			// e.printStackTrace();
			message.append("SQLException occurred when getting list of users..."
					+ e.getMessage());
			return message.toString();
		} catch (MobileEduException e) {
			// e.printStackTrace();
			message.append("MobileEduException occurred when getting list of users..."
					+ e.getMessage());
			return message.toString();
		}
		for (Users user : users) {
			try {
				// log.debug("Sending for user: " + user.getContextId());
				String response = ResponseMessageHandler.getInstance(req, res)
						.pushMessage(messageObject, user);
				message.append("Response=>" + response);
				message.append("<br />");

			} catch (MobileEduException e) {
				// e.printStackTrace();
				message.append("MobileEduException occurred: " + e.getMessage()
						+ " when processing the user: " + user.getContextId());
				message.append("<br />");
			}
		}
		// log.debug("Message Handler response: " + message);
		return message.toString();
	}

	/**
	 * Save DB to the user and write the response of success out to the server
	 * 
	 * @param user
	 */
	public String saveMessageToDB(Users user) {
		StringBuilder message = new StringBuilder();
		// Need to save the message to DB and respond
		message.append(Utilities.getStdReplyMessage());

		// create the appropriate registration message to be sent back
		message.append("You will get regular examination preparation tips for "
				+ user.getRegStandard()
				+ " std.<br />To stop please SMS @sioguide stop to "
				+ TXTWEB_MOBILE_NUMBER);
		return message.toString();
	}

	public void closeConnections() throws MobileEduException {
		try {
			dm.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new MobileEduException(e.getErrorCode() + "", e.getMessage());
		}
	}

}
