/**
 * 
 */
package org.freesource.mobedu.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.MessageManagerService;
import org.freesource.mobedu.dao.UserManagerService;
import org.freesource.mobedu.dao.model.Message;
import org.freesource.mobedu.dao.model.User;
import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.services.MessageHandlerService;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Logger;
import org.freesource.mobedu.utils.MobileEduException;
import org.freesource.mobedu.utils.ResponseMessageHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This servlet is used to send the message to registered users.
 */
@Controller
@RequestMapping(value = { "message" })
@SuppressWarnings("serial")
public class MessageRequestHandlerServlet extends HttpServlet implements
		Constants {

	private Logger log = Logger.getInstance("MessageRequestHandlerServlet");
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Buffer to contain the response to be sent back to the user
			StringBuilder webResponse = new StringBuilder();
			MessageManagerService messageService = (MessageHandlerService) DBConnectionManager
					.getInstance().getUserBean("messageHandlerService");

			// Get the message from the request
			String message = request.getParameter(HTTP_PARAM_MESSAGE);
			log.debug("Sending Message: " + message);
			// What if there is no mobile hash?
			// Means the request is from txt-web to register the app
			if (null == message || message.isEmpty()) {
				webResponse
						.append("Empty message, nothing submitted to registered users.");
			} else {
				Message m = new Message();
				m.setMessage(message);
				webResponse.append(messageService.sendMessageToAllUsers(
						request, response, m));
			}
			log.debug("Writing the response: " + webResponse.toString());
			ResponseMessageHandler.getInstance(request, response).writeMessage(
					webResponse.toString());
		} catch (Exception e) {
			log.debug(e.getMessage());
			String errMsg = "MobileEduException thrown in the server:"
					+ e.getMessage();
			log.debug(errMsg);
			ResponseMessageHandler.getInstance(request, response).writeMessage(
					errMsg);
		}
	}

	@RequestMapping(value = "sendSMSToUsers.this", method = RequestMethod.POST)
	public @ResponseBody
	String sendMessageToUser(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		log.debug("Inside: sendMessageToUser()");
		// Get the request parameters
		String message = req.getParameter(HTTP_PARAM_MESSAGE);
		String userId = req.getParameter(HTTP_PARAM_USER);
		log.debug("Message:" + message + ", User ID:" + userId);

		// Check for the validity of the param arguments passed
		if (null == userId || userId.isEmpty()) {
			return "User ID has not been passed and hence don't know whom to send the message to!";
		}
		if (null == message || message.isEmpty()) {
			return "Empty message, nothing submitted to send to the user:"
					+ userId;
		}
		try {
			UserManagerService userService = (UserManagerService) DBConnectionManager
					.getInstance().getUserBean("userHandlerService");
			log.debug("Got userService bean:" + userService);
			MessageManagerService messageService = (MessageHandlerService) DBConnectionManager
					.getInstance().getUserBean("messageHandlerService");
			log.debug("Got messageService bean:" + messageService);

			User currUser = userService.getUserById(Integer.parseInt(userId));
			log.debug("Got the User:" + currUser);
			Message m = new Message();
			m.setMessage(message);
			if (null == messageService) {
				throw new MobileEduException(
						"Message Handler Service did not get loaded!");
			}
			String result = messageService.sendMessageToUser(req, res, m,
					currUser);
			log.debug("Message Sending result:" + result);
			return result;
		} catch (MobileEduException e) {
			String errMsg = "MobileEduException thrown in the server:"
					+ e.getMessage();
			log.debug(errMsg);
			return errMsg;
		} catch (Exception e) {
			String errMsg = "Exception thrown in the messaging server:" + e.getMessage();
			log.debug(errMsg);
			return errMsg;
		}
	}
}
