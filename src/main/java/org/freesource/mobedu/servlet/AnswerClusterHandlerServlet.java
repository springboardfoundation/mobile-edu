package org.freesource.mobedu.servlet;

import javax.servlet.http.HttpServlet;

import org.freesource.mobedu.dao.AnswerClusterManagerService;
import org.freesource.mobedu.dao.model.object.ExpertAnswer;
import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Logger;
import org.freesource.mobedu.utils.MobileEduException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = { "answer" })
@SuppressWarnings("serial")
public class AnswerClusterHandlerServlet extends HttpServlet implements Constants {
	private Logger log = Logger.getInstance("AnswerClusterHandlerServlet");
	private AnswerClusterManagerService ansService;

	/*
	@RequestMapping(value = "saveAnswer", method = RequestMethod.POST)
	public @ResponseBody String sendMessageToUser(HttpServletRequest req, HttpServletResponse res) throws IOException {
		log.debug("Inside: sendAnswerToUser()");
		// Get the request parameters
		String answer = req.getParameter(HTTP_PARAM_MESSAGE);
		String userId = req.getParameter(HTTP_PARAM_USER);
		// String quesId = req.getParameter(HTTP_PARAM_QUESTIONID);
		log.debug("Answer:" + answer + ", User ID:" + userId);

		// Check for the validity of the param arguments passed
		if (null == userId || userId.isEmpty()) {
			return "User ID has not been passed and hence don't know whom to send the Answer to!";
		}
		if (null == answer || answer.isEmpty()) {
			return "Empty Answer, nothing submitted to send to the user:" + userId;
		}
		try {
			UserManagerService userService = (UserManagerService) DBConnectionManager.getInstance().getUserBean(
					"userHandlerService");
			log.debug("Got userService bean:" + userService);
			AnswerClusterManagerService answerService = (AnswerClusterManagerService) DBConnectionManager.getInstance()
					.getUserBean("answerClusterHandlerService");
			log.debug("Got AnswerClusterService bean:" + answerService);
			if (null == answerService) {
				throw new MobileEduException("Answer Handler Service did not get loaded!");
			}

			User currUser = userService.getUserById(Integer.parseInt(userId));
			// if currUser is null reply error return
			log.debug("Got the User:" + currUser);
			AnswerCluster ans = new AnswerCluster();
			ans.setAnswer(answer);

			String result = answerService.sendAnswerToUser(req, res, ans, currUser);
			log.debug("Answer Sending result:" + result);
			return result;
		} catch (MobileEduException e) {
			String errMsg = "MobileEduException thrown in the server:" + e.getMessage();
			log.debug(errMsg);
			return errMsg;
		} catch (Exception e) {
			String errMsg = "Exception thrown in the messaging server:" + e.getMessage();
			log.debug(errMsg);
			return errMsg;
		}
	}*/

	@RequestMapping(value = "replyToQuestion", method = RequestMethod.POST)
	public @ResponseBody String sendAnswerToUser(@RequestBody ExpertAnswer exp) {
		log.debug("Inside: sendAnswerToUser()");
		log.debug("The expert answer object passed is:" + exp);
		// Get the request parameters
		String answer = exp.getAnswer();
		int expertId = exp.getExpertId();
		log.debug("Answer:" + answer + ", Expert ID:" + expertId);
		try {
			ansService = (AnswerClusterManagerService) DBConnectionManager.getInstance().getUserBean(
					"answerClusterHandlerService");
		} catch (MobileEduException e) {
			String errMsg = "MobileEduException thrown in the server:" + e.getMessage();
			log.error(errMsg, e);
			return errMsg;
		} catch (Exception e) {
			String errMsg = "Exception thrown in the messaging server:" + e.getMessage();
			log.error(errMsg, e);
			return errMsg;
		}
		return "Success";
	}

}
