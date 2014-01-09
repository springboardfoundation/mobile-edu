/**
 * 
 */
package org.freesource.mobedu.services;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.freesource.mobedu.dao.Users;
import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.MobileEduException;
import org.freesource.mobedu.utils.Utilities;

/**
 *
 */
public class UserHandlerService implements Constants {

	HttpServletRequest request;
	DBConnectionManager dm;

	/**
	 * @throws MobileEduException
	 * 
	 */
	public UserHandlerService(HttpServletRequest req) throws MobileEduException {
		// Assign to global variable so that other fucntions can use it
		request = req;
		dm = new DBConnectionManager(DB4_TYPE);
	}

	public void populateUser(Users user) {
		/*
		 * private String protocol;
		 */
		// Get the Mobile number from the request parameters
		String mobileHash = request.getParameter(HTTP_PARAM_TXTWEB_MOBILE);
		log.debug("Mobile Hash passed:" + mobileHash);
		user.setMobileId(mobileHash);
		// Get the message from the request parameter
		String standard = request.getParameter(HTTP_PARAM_TXTWEB_MESSAGE);
		// Get the correct string to be stored in DB for the standard
		if (null == standard || 0 == standard.length()) {
			// Default will be 10th Standard
			user.setRegStd(Utilities.getStdClass(TENTH));
		} else {
			user.setRegStd(Utilities.getStdClass(standard));
		}

		user.setRegDate(Utilities.getCurrentTimestamp());
		user.activateUser();

		setUserLocation(user);
		user.setProtocol(request.getParameter(HTTP_PARAM_TXTWEB_PROTOCOL));
	}

	/**
	 * Use the location service and set the location of the user of the mobile
	 * 
	 * @param user
	 */
	private void setUserLocation(Users user) {
		// TODO Need to integrate with the Location service to get the location
		// and put it in the user object
		user.setLocation(null);
	}

	/**
	 * Save DB to the user and write the response of success out to the server
	 * 
	 * @param request
	 * @param response
	 * @param txtWebResponse
	 * @throws IOException
	 */
	public String saveUserToDB(StringBuffer txtWebResponse, Users user)
			throws IOException {
		try {
			dm.saveNewUser(user);
		} catch (MobileEduException e) {
			e.printStackTrace();
			return DEFAULT_ERR_MSG;
		} catch (SQLException e) {
			e.printStackTrace();
			return DEFAULT_ERR_MSG;
		}
		// create the appropriate registration message to be sent back
		txtWebResponse
				.append("<br />You will get regular examination preparation tips for ");
		txtWebResponse.append(user.getRegStd());
		txtWebResponse
				.append(".<br />To stop please SMS @sioguide stop to 92665 92665");
		return txtWebResponse.toString();
	}

	/**
	 * Function to search for the user in the DB for existence 
	 * 
	 * @param mobileHash - The mobile number id of the user
	 * @param user - The user object in which user will be returned if found
	 * @return - TRUE if user is found, FALSE otherwise
	 */
	public boolean searchUser(String mobileHash, Users user) {
		Users u;
		try {
			u = dm.searchForUser(mobileHash);
			if (null == u) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (MobileEduException e) {
			e.printStackTrace();
			return false;
		}
		// User exists, hence assign to the passed object
		user.copyUser(u);
		return true;
	}

}
