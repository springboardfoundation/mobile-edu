/**
 * 
 */
package org.freesource.mobedu.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.Message;
import org.freesource.mobedu.services.MessageHandlerService;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.ResponseMessageHandler;

/**
 * This servlet is used to send the message to registered users.
 */
@SuppressWarnings("serial")
public class MessageRequestHandler extends HttpServlet implements Constants {

	/**
	 * Initialize the class objects
	 */
	public MessageRequestHandler() {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			MessageHandlerService messageService = new MessageHandlerService();
			// Buffer to contain the response to be sent back to the user
			StringBuilder webResponse = new StringBuilder();

			// Get the message from the request
			String message = request.getParameter(HTTP_PARAM_MESSAGE);
			log.debug("Message: " + message);
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
			ResponseMessageHandler.getInstance(request, response).writeMessage(
					webResponse.toString());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

}
