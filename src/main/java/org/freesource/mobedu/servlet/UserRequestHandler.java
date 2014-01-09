/**
 * 
 */
package org.freesource.mobedu.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.Users;
import org.freesource.mobedu.services.UserHandlerService;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.MobileEduException;
import org.freesource.mobedu.utils.ResponseMessageHandler;
import org.freesource.mobedu.utils.Utilities;

/**
 * This servlet is used to store the information of any user who registers to
 * the service. The default registration is for 10th.
 */
@SuppressWarnings("serial")
public class UserRequestHandler extends HttpServlet implements Constants {

	/**
	 * Initialize the class objects
	 */
	public UserRequestHandler() {
		regUser = new Users();
	}

	Users regUser;
	HttpServletRequest request;
	HttpServletResponse response;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {

		UserHandlerService useService;
		try {
			useService = new UserHandlerService(req);
		} catch (MobileEduException e) {
			e.printStackTrace();
			ResponseMessageHandler.writeMessage(request, response,
					DEFAULT_ERR_MSG);
			return;
		}

		// Assign to global variables so that other fucntions can use it
		request = req;
		response = res;
		response.setContentType("text/html");

		useService.populateUser(regUser);
		// Buffer to contain the response to be sent back to the user
		StringBuffer txtWebResponse = new StringBuffer();

		// What if there is no mobile hash?
		String mobileHash = request.getParameter(HTTP_PARAM_TXTWEB_MOBILE);
		if (null == mobileHash || mobileHash.isEmpty()) {
			respondToRegisterRequest();
			return;
		}

		if (useService.searchUser(mobileHash, regUser)) {
			txtWebResponse
					.append("You are already registered to this service for getting tips of:");
			txtWebResponse.append(regUser.getRegStd());
			txtWebResponse
					.append("<br />To stop please SMS @sioguide stop to 92665 92665");
			ResponseMessageHandler.writeMessage(request, response,
					txtWebResponse.toString());
			return;
		}
		String standard = request.getParameter(HTTP_PARAM_TXTWEB_MESSAGE);
		// Get the correct string to be stored in DB for the standard
		if (null == standard || 0 == standard.length()) {
			// Default will be 10th Standard
			txtWebResponse.append("No class given for registration. ");
		}
		txtWebResponse.append(Utilities.getStdReplyMessage());

		// The user has requested to register to the std, check for its validity
		if (regUser.getRegStd().isEmpty()) {
			replyForValidStd(txtWebResponse);
			return;
		}
		// Finally Save to the DB
		String reply = useService.saveUserToDB(txtWebResponse, regUser);
		ResponseMessageHandler.writeMessage(request, response, reply);
	}

	/**
	 * If the Std is not set in the user object then the registration standard
	 * given is invalid. Print the message and write the respond to the user
	 * without continuing further
	 * 
	 * @param txtWebResponse
	 * @throws IOException
	 */
	private void replyForValidStd(StringBuffer txtWebResponse)
			throws IOException {

		txtWebResponse.append("Please select one from the given list:<br />"
				+ LIST_OF_SUPPORTED_STD);
		try {
			ResponseMessageHandler.writeMessage(request, response,
					txtWebResponse.toString());
		} catch (IOException e) {
			ResponseMessageHandler.writeMessage(request, response,
					DEFAULT_ERR_MSG);
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Respond to the app registration message that is to be sent to txt-web for
	 * its response
	 * 
	 * @param mobileHash
	 * @throws IOException
	 */
	private void respondToRegisterRequest() throws IOException {
		try {
			ResponseMessageHandler.writeMessage(request, response,
					"Application Registration Message");
		} catch (IOException e) {
			ResponseMessageHandler.writeMessage(request, response,
					DEFAULT_ERR_MSG);
			e.printStackTrace();
		}
		return;
	}

}
