package org.freesource.mobedu.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.ExpertResourceManagerService;
import org.freesource.mobedu.dao.model.object.Expert;
import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This servlet is used to send the message to registered users.
 */
@Controller
@RequestMapping(value = { "expert" })
@SuppressWarnings("serial")
public class ExpertRequestHandlerServlet extends HttpServlet implements
		Constants {
	private Logger log = Logger.getInstance("ExpertRequestHandlerServlet");

	@RequestMapping(value = "checkExpert.this", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Expert loginCheck(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		log.debug("Inside: loginCheck()");
		String expertid = req.getParameter(HTTP_PARAM_USERNAME);
		String password = req.getParameter(HTTP_PARAM_PASSWRD);
		// should we delete line for security purpose?
		log.debug("userExpert:" + expertid + ", pass word:" + password);
		Expert result = null;
		try {
			ExpertResourceManagerService expertService = (ExpertResourceManagerService) DBConnectionManager
					.getInstance().getUserBean("expertResourceHandlerService");

			result = expertService.checkExpert(expertid, password);
			if (result == null) {
				result = new Expert();
				result.setId(0);
				result.setLoginId("InvalidUser");
				result.setName("Invalid username/passowrd combination provided.<BR/> Please enter valid credentails");
				log.debug("login failure");
			} else
				System.out.println("login success");
		} catch (Exception e) {
			log.error("Error", e);
			result = new Expert();
			result.setId(0);
			result.setLoginId("InvalidUser");
			result.setName("There was an exception in the server.<BR/> Please check with the system administrator.");
		}
		return result;
	}

}
