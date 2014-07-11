/**
 * 
 */
package org.freesource.mobedu.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.MessageManagerService;
import org.freesource.mobedu.dao.UserManagerService;
import org.freesource.mobedu.dao.model.Message;
import org.freesource.mobedu.dao.model.User;
import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Logger;
import org.freesource.mobedu.utils.MobileEduException;
import org.freesource.mobedu.utils.ResponseMessageHandler;
import org.freesource.mobedu.utils.Utilities;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 */
@Service
public class MessageHandlerService implements Constants, MessageManagerService {

	private Logger log = Logger.getInstance("MessageHandlerService");
	/**
	 * @throws MobileEduException
	 */
	public MessageHandlerService() throws MobileEduException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.freesource.mobedu.dao.MessageManagerService#sendMessageToAllUsers
	 * (HttpServletRequest, HttpServletResponse, Message)
	 */
	public String sendMessageToAllUsers(HttpServletRequest req,
			HttpServletResponse res, Message messageObject) {
		StringBuilder message = new StringBuilder();
		List<User> users = null;
		try {
			UserManagerService userManagerService = (UserManagerService) DBConnectionManager
					.getInstance().getUserBean("userHandlerService");
			log.debug("Getting list of all users from dB");
			users = userManagerService.getAllRegisteredUsers();
			log.debug("Got " + users.size() + " number of users from dB");
		} catch (Exception e) {
			log.error("MobileEduException occurred when getting list of users",
					e);
			message.append("MobileEduException occurred when getting list of users..."
					+ e.getMessage());
			return message.toString();
		}
		for (User user : users) {
			try {
				log.debug("Sending for user: " + user.getContextId());
				String response = ResponseMessageHandler.getInstance(req, res)
						.pushMessage(messageObject, user);
				message.append("Response=>" + response);

			} catch (MobileEduException e) {
				log.error(
						"MobileEduException occurred when processing the user:"
								+ user.getContextId(), e);
				message.append("MobileEduException occurred: " + e.getMessage()
						+ ", when processing the user:" + user.getContextId());
			}
		}
		log.debug("Message Handler response: " + message);
		return message.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freesource.mobedu.dao.MessageManagerService#sendMessageToUser(
	 * HttpServletRequest, HttpServletResponse, Message, User)
	 */
	public String sendMessageToUser(HttpServletRequest req,
			HttpServletResponse res, Message messageObject, User user) {
		StringBuilder message = new StringBuilder();
		log.debug("Inside sendMessageToUser");
		try {
			log.debug("sendMessageToUser: Sending for user: "
					+ user.getContextId());
			String response = ResponseMessageHandler.getInstance(req, res)
					.pushMessage(messageObject, user);
			message.append("Response=>" + response);
			log.debug("Final response from sendMessageToUser:"
					+ message.toString());
		} catch (MobileEduException e) {
			log.error("MobileEduException occurred when processing the user: "
					+ user.getContextId(), e);
			message.append("MobileEduException occurred: " + e.getMessage()
					+ " when processing the user: " + user.getContextId());
		}
		log.debug("sendMessageToUser: Message Handler response: " + message);
		return message.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.freesource.mobedu.dao.MessageManagerService#saveMessageToDB(User)
	 */
	public String saveMessageToDB(User user) {
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

}
