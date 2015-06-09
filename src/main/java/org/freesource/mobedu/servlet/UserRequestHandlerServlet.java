/**
 * 
 */
package org.freesource.mobedu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.UserManagerService;
import org.freesource.mobedu.dao.model.User;
import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Logger;
import org.freesource.mobedu.utils.MobileEduException;
import org.freesource.mobedu.utils.ResponseMessageHandler;
import org.freesource.mobedu.utils.Utilities;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This servlet is used to store the information of any user who registers to
 * the service.
 */
@Controller
@RequestMapping(value = { "user" })
@SuppressWarnings("serial")
public class UserRequestHandlerServlet extends HttpServlet implements Constants {

	private User regUser;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private UserManagerService userService;
	private Logger log = Logger.getInstance("UserRequestHandlerServlet");

	/**
	 * Initialize the class objects
	 */
	public void init() throws ServletException {
		try {
			userService = (UserManagerService) DBConnectionManager.getInstance().getUserBean("userHandlerService");
		} catch (MobileEduException e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage(), e);
		}
		regUser = new User();
	}

	@RequestMapping(value = "userCount", method = RequestMethod.GET)
	public @ResponseBody long getNumberOfUsers(HttpServletRequest req, HttpServletResponse res) throws IOException,
			MobileEduException {
		log.debug("Inside: getNumberOfUsers()");
		userService = (UserManagerService) DBConnectionManager.getInstance().getUserBean("userHandlerService");
		long count = userService.getNumberOfUser();
		return count;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try {
			// Buffer to contain the response to be sent back to the user
			StringBuilder txtWebResponse = new StringBuilder();

			// Assign to global variables so that other functions can use it
			request = req;
			response = res;
			response.setContentType("text/html");

			// Get the Mobile number from the request parameters
			String mobileHash = request.getParameter(HTTP_PARAM_TXTWEB_MOBILE);
			// What if there is no mobile hash?
			// Means the request is from txt-web to register the app
			if (null == mobileHash || mobileHash.isEmpty()) {
				respondToRegisterRequest();
				return;
			}

			userService = (UserManagerService) DBConnectionManager.getInstance().getUserBean("userHandlerService");
			// Get the message from the request parameter
			String txtweb_message = request.getParameter(HTTP_PARAM_TXTWEB_MESSAGE);
			// Populate protocol
			regUser.setProtocol(request.getParameter(HTTP_PARAM_TXTWEB_PROTOCOL));
			if (!userService.validatePopulateUser(regUser, mobileHash, txtweb_message)) {
				ResponseMessageHandler.getInstance(request, response).writeMessage(userService.getMessage());
				return;
			}

			if ((regUser.getRegStandard() != null && regUser.getRegStandard().isEmpty())
					&& !regUser.getRegStandard().equals(QUESTION) && !txtweb_message.equalsIgnoreCase(UNREGISTER)) {
				// The user has requested to register to an invalid std and
				// hence it was not populated by populateUser()
				replyForValidStd(txtWebResponse);
				return;
			}
			log.debug("The User Object:" + regUser);

			// Handle the all service stop request here
			if (null != txtweb_message && txtweb_message.equalsIgnoreCase(UNREGISTER)) {
				txtWebResponse.append(userService.stopService(regUser));
			} else if (regUser.getRegStandard() != null && !regUser.getRegStandard().isEmpty()
					&& !regUser.getRegStandard().equals(QUESTION)) {
				// Handle start/register request here
				log.debug("Going to start the service");
				txtWebResponse.append(userService.startService(regUser));
			} else {
				txtWebResponse.append(userService.getMessage());
			}

			ResponseMessageHandler.getInstance(request, response).writeMessage(txtWebResponse.toString());
		} catch (MobileEduException e) {
			log.debug(e.getMessage());
			ResponseMessageHandler.getInstance(request, response).writeMessage(DEFAULT_REGISTRATION_ERR_MSG);
			return;
		} catch (IOException e) {
			log.debug(e.getMessage());
			ResponseMessageHandler.getInstance(request, response).writeMessage(DEFAULT_REGISTRATION_ERR_MSG);
		}
	}

	/**
	 * If the Std is not set in the user object then the registration standard
	 * given is invalid. Print the message and write the respond to the user
	 * without continuing further
	 * 
	 * @param txtWebResponse
	 *            - StringBuffer to append the response and send to the user
	 * @throws IOException
	 */
	private void replyForValidStd(StringBuilder txtWebResponse) throws IOException {

		txtWebResponse.append("Invalid option selected. ");
		txtWebResponse.append("Please select one from the given list:<br />" + Utilities.getListOfSupportedClass());
		txtWebResponse.append("<br /> Eg: @sioguide 10th");
		ResponseMessageHandler.getInstance(request, response).writeMessage(txtWebResponse.toString());
	}

	/**
	 * Respond to the app registration message that is to be sent to txt-web for
	 * its response.
	 * 
	 * @param mobileHash
	 * @throws IOException
	 */
	private void respondToRegisterRequest() throws IOException {
		// This has been moved to a separate method so that it can be changed in
		// future to forward to a different page, if required
		ResponseMessageHandler.getInstance(request, response).writeMessage("Application Registration Message");
		return;
	}

}
