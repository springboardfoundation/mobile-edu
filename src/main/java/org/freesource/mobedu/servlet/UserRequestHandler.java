/**
 * 
 */
package org.freesource.mobedu.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.Users;
import org.freesource.mobedu.db.DBConnectionManager;
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
		txtWebResponse = new StringBuffer();
	}

	Users regUser;
	StringBuffer txtWebResponse;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// Get the Mobile number from the request parameters
		String mobileHash = request.getParameter(HTTP_PARAM_TXTWEB_MOBILE);
		log.debug("Mobile Hash passed:" + mobileHash);
		// What if there is no mobile hash?
		if (null == mobileHash || mobileHash.isEmpty()) {
			response.setContentType("text/html");
			ResponseMessageHandler.writeMessage(request, response,
					"Application Registration Message");
			return;
		}

		regUser.setMobileId(mobileHash);

		// Get the message from the request parameter
		String standard = request.getParameter(HTTP_PARAM_TXTWEB_MESSAGE);

		// Get the correct string to be stored in DB for the standard
		if (null == standard || 0 == standard.length()) {
			// Default will be 10th Standard
			txtWebResponse.append("No class given for registration. ");
			regUser.setRegStd(Utilities.getStdClass(TENTH));
			txtWebResponse.append(Utilities.getStdReplyMessage());
		} else {
			regUser.setRegStd(Utilities.getStdClass(standard));
			txtWebResponse.append(Utilities.getStdReplyMessage());
		}
		// If the Std is not set in the user object then the registration
		// standard given is invalid. Print the message and write the respond to
		// the user without continuing further
		if (regUser.getRegStd().isEmpty()) {
			txtWebResponse
					.append("Please select one from the given list:<br />"
							+ LIST_OF_SUPPORTED_STD);
			ResponseMessageHandler.writeMessage(request, response,
					txtWebResponse.toString());
			return;
		}
		try {
			DBConnectionManager dm = new DBConnectionManager(DB4_TYPE);
			dm.saveNewUser(regUser);
		} catch (MobileEduException e) {
			ResponseMessageHandler
					.writeMessage(request, response, errorMessage);
			e.printStackTrace();
		} catch (SQLException e) {
			ResponseMessageHandler
					.writeMessage(request, response, errorMessage);
			e.printStackTrace();
		}
		// create the appropriate registration message to be sent back
		txtWebResponse
				.append("<br />You will get regular examination preparation tips for ");
		txtWebResponse.append(regUser.getRegStd());
		txtWebResponse
				.append(".<br />To stop please SMS @sioguide stop to 92665 92665");
		ResponseMessageHandler.writeMessage(request, response,
				txtWebResponse.toString());
	}

}
