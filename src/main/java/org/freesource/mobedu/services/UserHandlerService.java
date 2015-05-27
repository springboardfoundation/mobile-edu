/**
 * 
 */
package org.freesource.mobedu.services;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.freesource.mobedu.dao.MessageDAO;
import org.freesource.mobedu.dao.UserDAO;
import org.freesource.mobedu.dao.UserManagerService;
import org.freesource.mobedu.dao.model.Message;
import org.freesource.mobedu.dao.model.User;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Logger;
import org.freesource.mobedu.utils.MobileEduException;
import org.freesource.mobedu.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * All user object management has to be put in this class
 * 
 */
@Service
public class UserHandlerService implements Constants, UserManagerService {

	@Autowired
	private UserDAO userDAO;
	private StringBuffer message;

	@Autowired
	private MessageDAO msgDAO;

	private Logger log = Logger.getInstance("UserHandlerService");

	/**
	 * @throws MobileEduException
	 * 
	 */
	public UserHandlerService() throws MobileEduException {
		message = new StringBuffer();
	}

	public String getMessage() {
		return message.toString();
	}

	/**
	 * This method is used to insert a new user. Note that the user has to be
	 * unique before being inserted. Use UserDAO.getUserById() to check if the
	 * user exists before inserting.
	 * 
	 * @param user
	 */
	private void insertUser(User user) {
		int nextContextId = userDAO.getMaxContextId();
		if (-1 == nextContextId) {
			nextContextId = 1;
		} else {
			nextContextId++;
		}
		user.setContextId(nextContextId);
		userDAO.insertUser(user);
	}

	@Transactional
	public void updateUser(User user) {
		userDAO.update(user);
	}

	/**
	 * Method to get the User object fetched from the DB based on the
	 * Mobile-Hash as the key for the user
	 * 
	 * @param mobileHash
	 * @return fetched User object
	 */
	private User getUserByMobileHash(String mobileHash) {
		return userDAO.getUserByMobileHash(mobileHash);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freesource.mobedu.dao.UserManagerService#getNumberOfUser()
	 */
	@Transactional
	public long getNumberOfUser() {
		return userDAO.getNumberOfUser();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freesource.mobedu.dao.UserManagerService#getAllRegisteredUsers()
	 */
	@Transactional
	public List<User> getAllRegisteredUsers() {
		return userDAO.getUsers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freesource.mobedu.dao.UserManagerService#getUserById(int)
	 */
	@Transactional
	public User getUserById(int userId) {
		return userDAO.getUserById(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.freesource.mobedu.dao.UserManagerService#validatePopulateUser(org
	 * .freesource.mobedu .dao.model.User, String, String)
	 */
	@Transactional
	public boolean validatePopulateUser(User user, String mobileHash,
			String arguments) {
		String[] args;
		String str;
		log.debug("Mobile Hash passed:" + mobileHash);
		user.setMobileId(mobileHash);

		if (null == arguments || arguments.isEmpty()) {
			// Default standard that is given in the std properties file
			user.setRegStandard(Utilities.getDefaultStdClass());
		} else {

			try {
				arguments = URLDecoder.decode(arguments, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error("Error occurred when decoding the arguments.", e);
				message.append("Error occurred when decoding the arguments. "
						+ "Please remove any special character in the message."
						+ "<br />To register SMS @sioguide &#60;Class&#62; to "
						+ TXTWEB_MOBILE_NUMBER + "<br />Eg: @sioguide 10th");
				return false;
			}
			args = arguments.split(" ");

			if (args[0].equalsIgnoreCase("Q"))// check is it question?
			{
				log.debug("inside question block");
				if (args.length >= 2) {
					log.debug("inside valid question format  block");
					arguments = arguments.trim();
					str = arguments.substring(2);
					User u = getUserByMobileHash(mobileHash);
					if (u == null) {
						u = new User();
						u.setMobileId(mobileHash);
						u.setRegStandard(QUESTION);
						u.deActivateUser();
						u.setRegDate(new java.sql.Date(Utilities
								.getCurrentTimestamp().getTime()));
						insertUser(u);
					} else {
						u.setRegStandard(QUESTION);
					}
					user.copyUser(u);

					Message msg = new Message();
					msg.setUserId(u.getContextId());
					msg.setMessage(str);
					msg.activateMessage();
					msg.setInsertDate(new java.sql.Date(Utilities
							.getCurrentTimestamp().getTime()));
					msg.setAsQuestion();
					msg.setMessageId(msgDAO.getMaxMsgId() + 1);
					log.debug(" before insertion of question in DB:"
							+ msg.getMessageId());
					msgDAO.insertMessage(msg);
					log.debug("after insertion of question in DB");
					message.delete(0, message.length());// flush out or delete
														// the old content

					message.append("thank you for asking,"
							+ "<br/> will get back to you soon");// reply on
																	// qstn
					return true;
				} else {
					log.debug("inside invalid question format block");
					message.append("Invalid message format for Query ."
							+ "<br />To query SMS  @sioguide Q &#60;Question&#62; to "
							+ TXTWEB_MOBILE_NUMBER
							+ "<br />Eg: @sioguide Q carrer related question");
					return false;
				}

			} else {

				if (args.length > 2) {
					log.debug("inside invalid message block");
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
								+ TXTWEB_MOBILE_NUMBER
								+ "<br />Eg: @sioguide 10th");
					} else {
						message.append("Subject specific registration is coming soon."
								+ "<br />To register SMS @sioguide &#60;Class&#62; to "
								+ TXTWEB_MOBILE_NUMBER
								+ "<br />Eg: @sioguide 10th");
					}
					return false;
				}
			}
		}
		user.setRegStandard(Utilities.getStdClass(arguments));
		user.setRegSubject("all");
		user.setRegDate(new java.sql.Date(Utilities.getCurrentTimestamp()
				.getTime()));
		user.activateUser();
		setUserLocation(user);

		return true;
	}

	/**
	 * Use the location service and set the location of the user of the mobile
	 * 
	 * @param user
	 */
	private void setUserLocation(User user) {
		// TODO Need to integrate with the Location service to get the location
		// and put it in the user object
		user.setLocation(null);
	}

	/**
	 * Save DB to the user and write the response of success out to the server
	 * 
	 * @param user
	 */
	public String saveUserToDB(User user) {
		// Clear the message object to populate it with the results string
		message.delete(0, message.length());
		try {
			insertUser(user);
		} catch (Exception e) {
			log.error(DEFAULT_REGISTRATION_ERR_MSG, e);
			return DEFAULT_REGISTRATION_ERR_MSG;
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
	 * @throws MobileEduException
	 *             In case there is an exception when searching for existing
	 *             user
	 */
	public boolean searchUser(String mobileHash, User user)
			throws MobileEduException {
		User u;
		try {
			u = getUserByMobileHash(mobileHash);
			if (null == u) {
				return false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new MobileEduException(e.getMessage(), e);
		}
		// User exists, hence assign to the passed object
		user.copyUser(u);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freesource.mobedu.dao.UserManagerService#stopService(org
	 * .freesource.mobedu .dao.model.User)
	 */
	@Transactional
	public String stopService(User regUser) throws MobileEduException {
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
				updateUser(regUser);
				message.append("Thank you for using SIO-Guide service.<br />"
						+ "You have been successfully unregistered.<br />"
						+ "To register again SMS @sioguide &#60;Class&#62 to "
						+ TXTWEB_MOBILE_NUMBER + "<br />Eg: @sioguide 10th");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new MobileEduException(e.getMessage(), e);
		}
		return message.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freesource.mobedu.dao.UserManagerService#startService(org
	 * .freesource.mobedu .dao.model.User)
	 */
	@Transactional
	public String startService(User user) throws MobileEduException {
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
					updateUser(user);
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new MobileEduException(e.getMessage(), e);
		}

		return message.toString();
	}
}
