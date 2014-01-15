/**
 * 
 */
package org.freesource.mobedu.services;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;

import org.freesource.mobedu.dao.Users;
import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.MobileEduException;
import org.freesource.mobedu.utils.Utilities;

/**
 *
 */
public class UserHandlerService implements Constants {

	DBConnectionManager dm;
	StringBuffer message;

	/**
	 * @throws MobileEduException
	 * 
	 */
	public UserHandlerService() throws MobileEduException {
		dm = new DBConnectionManager();
		message = new StringBuffer();
	}

	public String getMessage() {
		return message.toString();
	}

	public boolean validatePopulateUser(Users user, String mobileHash,
			String arguments) {
		String[] args;
		log.debug("Mobile Hash passed:" + mobileHash);
		user.setMobileId(mobileHash);

		if (null == arguments || arguments.isEmpty()) {
			// Default standard that is given in the std properties file
			user.setRegStandard(Utilities.getDefaultStdClass());
		} else {

			try {
				arguments = URLDecoder.decode(arguments, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				message.append("Error occurred when decoding the arguments. "
						+ "Please remove any special character in the message."
						+ "<br />To register SMS @sioguide &#60;Class&#62; to "
						+ TXTWEB_MOBILE_NUMBER + "<br />Eg: @sioguide 10th");
				return false;
			}
			args = arguments.split(" ");

			if (args.length > 2) {
				message.append("Invalid number of arguments passed."
						+ "<br />To register SMS @sioguide &#60;Class&#62; to "
						+ TXTWEB_MOBILE_NUMBER + "<br />Eg: @sioguide 10th");
				return false;
			}
			if (args.length == 2) {
				String standard = args[0];
				user.setRegStandard(Utilities.getStdClass(standard));
				user.setRegSubject("all");
				if (user.getRegStandard().isEmpty()
						&& !arguments.equalsIgnoreCase(UNREGISTER)) {
					message.append("Invalid number of arguments passed."
							+ "<br />To register SMS @sioguide &#60;Class&#62; to "
							+ TXTWEB_MOBILE_NUMBER + "<br />Eg: @sioguide 10th");
				} else {
					message.append("Subject specific registration is coming soon."
							+ "<br />To register SMS @sioguide &#60;Class&#62; to "
							+ TXTWEB_MOBILE_NUMBER + "<br />Eg: @sioguide 10th");
				}
				return false;
			}
		}
		user.setRegStandard(Utilities.getStdClass(arguments));
		user.setRegSubject("all");
		user.setRegDate(Utilities.getCurrentTimestamp());
		user.activateUser();
		setUserLocation(user);

		return true;
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
	 * @param user
	 */
	public String saveUserToDB(Users user) {
		// Clear the message object to populate it with the results string
		message.delete(0, message.length());
		try {
			dm.saveNewUser(user);
		} catch (MobileEduException e) {
			e.printStackTrace();
			return DEFAULT_ERR_MSG;
		} catch (SQLException e) {
			e.printStackTrace();
			return DEFAULT_ERR_MSG;
		}
		message.append(Utilities.getStdReplyMessage());

		// create the appropriate registration message to be sent back
		message.append("You will get regular examination preparation tips for "
				+ user.getRegStandard()
				+ " std.<br />To stop please SMS @sioguide stop to "
				+ TXTWEB_MOBILE_NUMBER);
		return message.toString();
	}

	/**
	 * Function to search for the user in the DB for existence
	 * 
	 * @param mobileHash
	 *            - The mobile number id of the user
	 * @param user
	 *            - The user object in which user will be returned if found
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

	/**
	 * Handle all the stop service request. Below table illustrates the possible
	 * options of a stop request by the user:
	 * <table border="1">
	 * <tr>
	 * <th>User Exists</th>
	 * <th>User IS ACTIVE</th>
	 * <th>Action to be taken</th>
	 * </tr>
	 * <tr>
	 * <td align="center">No</td>
	 * <td align="center">---</td>
	 * <td>Error: Not registered</td>
	 * </tr>
	 * </tr>
	 * <tr>
	 * <td align="center">Yes</td>
	 * <td align="center">No</td>
	 * <td>Error: Not registered</td>
	 * </tr>
	 * <tr>
	 * <td align="center">Yes</td>
	 * <td align="center">Yes</td>
	 * <td>Success: Unregister</td>
	 * </tr>
	 * </table>
	 * 
	 * @param regUser
	 *            - The user to be unregistered
	 * @return String - The reply message to be sent
	 * @throws MobileEduException
	 */
	public String stopService(Users regUser) throws MobileEduException {
		// Clear the message object to populate it with the results string
		message.delete(0, message.length());
		try {
			if (!searchUser(regUser.getMobileId(), regUser)
					|| !regUser.isActive()) {
				message.append("You are not registered to this service."
						+ "<br />To register SMS @sioguide &#60;Class&#62; to "
						+ TXTWEB_MOBILE_NUMBER + "<br />Eg: @sioguide 10th");
			} else {
				// If code comes here then the user is exists and is active
				regUser.deActivateUser();
				dm.updateUser(regUser);
				message.append("Thank you for using SIO-Guide service.<br />"
						+ "You have been successfully unregistered.<br />"
						+ "To register again SMS @sioguide &#60;Class&#62 to "
						+ TXTWEB_MOBILE_NUMBER + "<br />Eg: @sioguide 10th");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MobileEduException(e.getErrorCode() + "", e.getMessage());
		} catch (MobileEduException e) {
			e.printStackTrace();
			throw new MobileEduException(e.getErrorCode() + "", e.getMessage());
		}
		return message.toString();
	}

	/**
	 * Handle all the start service request. Below table illustrates the
	 * possible options of a start request by the user:
	 * <table border="1">
	 * <tr>
	 * <th>User Exists</th>
	 * <th>User IS ACTIVE</th>
	 * <th>Action to be taken</th>
	 * </tr>
	 * <tr>
	 * <td align="center">No</td>
	 * <td align="center">---</td>
	 * <td>Success: Register</td>
	 * </tr>
	 * </tr>
	 * <tr>
	 * <td align="center">Yes</td>
	 * <td align="center">No</td>
	 * <td>Success: Register</td>
	 * </tr>
	 * <tr>
	 * <td align="center">Yes</td>
	 * <td align="center">Yes</td>
	 * <td>Error: Already Registered</td>
	 * </tr>
	 * </table>
	 * 
	 * @param user
	 * @return
	 * @throws MobileEduException
	 */
	public String startService(Users user) throws MobileEduException {
		// Save the current standard as it gets overwritten by search, if found
		String currentStd = user.getRegStandard();
		// Clear the message object to populate it with the results string
		message.delete(0, message.length());
		try {
			if (searchUser(user.getMobileId(), user) && user.isActive()) {
				message.append("You are already registered to this service for getting tips of: "
						+ user.getRegStandard()
						+ "<br />Note: Multiple class registration is not yet supported<br />"
						+ "To stop please SMS @sioguide stop to "
						+ TXTWEB_MOBILE_NUMBER);
			} else {
				// If control comes here then either user does not exists or is
				// inactive
				if (!user.isActive()) {
					// Set the previously saved standard again
					user.setRegStandard(currentStd);
					user.activateUser();
					log.debug("User activated and now updating the user in DB");
					dm.updateUser(user);
					// create the appropriate registration message to be sent
					// back
					message.append(Utilities.getStdReplyMessage());
					message.append("You will get regular examination preparation tips for "
							+ user.getRegStandard()
							+ " std.<br />To stop please SMS @sioguide stop to "
							+ TXTWEB_MOBILE_NUMBER);
				} else {
					// Finally Save to the DB
					saveUserToDB(user);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MobileEduException(e.getErrorCode() + "", e.getMessage());
		} catch (MobileEduException e) {
			e.printStackTrace();
			throw new MobileEduException(e.getErrorCode() + "", e.getMessage());
		}
		return message.toString();
	}

	public void closeConnections() throws MobileEduException {
		try {
			dm.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MobileEduException(e.getErrorCode() + "", e.getMessage());
		}
	}

}
