/**
 * 
 */
package org.freesource.mobedu.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.Users;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Utilities;

/**
 * This servlet is used to store the information of any user who registers to
 * the service. The default registration is for 10th.
 */
@SuppressWarnings("serial")
public class RegistrationHandler extends HttpServlet implements Constants {

	/**
	 * 
	 */
	public RegistrationHandler() {
		regUser = new Users();
	}

	Users regUser;
	String txtWebResponse = "";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// Get the Mobile number from the request parameters
		String mobileHash = req.getParameter(HTTP_PARAM_TXTWEB_MOBILE);
		regUser.setMobileId(mobileHash);
		
		// Get the message from the request parameter
		String standard = req.getParameter(HTTP_PARAM_TXTWEB_MESSAGE);
		
		// Get the correct string to be stored in DB for the String and also
		// create the appropriate registration message to be sent back
		if (null == standard || 0 == standard.length()) {
			// Default will be 10th Standard
			txtWebResponse += "No class given for registration. ";
			regUser.setRegStd(Utilities.getStdClass(TENTH));
			txtWebResponse += Utilities.getStdReplyMessage();
		} else {
			regUser.setRegStd(Utilities.getStdClass(standard));
			txtWebResponse += Utilities.getStdReplyMessage();
		}
	}

}
