package org.freesource.mobedu.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class AnswerClusterHandlerServlet extends HttpServlet implements
		Constants {
	private Logger log = Logger.getInstance("AnswerClusterHandlerServlet");
	private AnswerClusterManagerService ansService;

	@RequestMapping(value = "replyToQuestion", method = RequestMethod.POST)
	public @ResponseBody
	String sendAnswerToUser(@RequestBody ExpertAnswer exp,
			HttpServletRequest req, HttpServletResponse res) {
		log.debug("Inside: sendAnswerToUser()");
		log.debug("The expert answer object passed is:" + exp);
		// Get the request parameters
		String answer = exp.getAnswer();
		int expertId = exp.getExpertId();
		log.debug("Answer:" + answer + ", Expert ID:" + expertId);
		try {
			ansService = (AnswerClusterManagerService) DBConnectionManager
					.getInstance().getUserBean("answerClusterHandlerService");
			log.debug("inside answer servlet");
			String result = ansService.sendAnswerToUser(exp, req, res);
			return result;

		} catch (MobileEduException e) {
			String errMsg = "MobileEduException thrown in the server:"
					+ e.getMessage();
			log.error(errMsg, e);
			return errMsg;
		} catch (Exception e) {
			String errMsg = "Exception thrown in the messaging server:"
					+ e.getMessage();
			log.error(errMsg, e);
			return errMsg;
		}
	}

}
