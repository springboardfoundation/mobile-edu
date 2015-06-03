package org.freesource.mobedu.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.MessageManagerService;
import org.freesource.mobedu.dao.QuestionManagerService;
import org.freesource.mobedu.dao.model.object.Question;
import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.services.MessageHandlerService;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Logger;
import org.freesource.mobedu.utils.MobileEduException;
import org.freesource.mobedu.utils.ResponseMessageHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = { "question" })
@SuppressWarnings("serial")
public class QuestionHandlerServlet extends HttpServlet implements Constants {
	private QuestionManagerService QuestionService;
	private Logger log = Logger.getInstance("QuestionHandlerServlet");

	@RequestMapping(value = "allQuestions", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody
	List<Question> loadAllQuestions() throws IOException, MobileEduException {
		log.debug("inside servelet to loadQuestn");
		QuestionService = (QuestionManagerService) DBConnectionManager.getInstance().getUserBean("questionHandlerService");
		List<Question> q = (List<Question>) QuestionService.getAllQuestions();
				
		return q;
	}
	

	@RequestMapping(value = "saveAnswer", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public @ResponseBody
	String saveAnswer(Question q) {
		return "";
	}
	
	@RequestMapping(value = "unAnsweredQues", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody
	List<Question> loadunAnsweredQues() throws IOException, MobileEduException {
		log.debug("inside servelet to loadunansweredQuestn");
		QuestionService = (QuestionManagerService) DBConnectionManager.getInstance().getUserBean("questionHandlerService");
		List<Question> q = (List<Question>) QuestionService.getUnAnsweredQuestions();
				
		return q;
	}
	
	
	@RequestMapping(value = "AnsweredQues", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody
	List<Question> loadAnsweredQues() throws IOException, MobileEduException {
		log.debug("inside servelet to loadunansweredQuestn");
		QuestionService = (QuestionManagerService) DBConnectionManager.getInstance().getUserBean("questionHandlerService");
		List<Question> q = (List<Question>) QuestionService.getAnsweredQuestions();
				
		return q;
	}
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			StringBuilder webResponse = new StringBuilder();
			
			MessageManagerService messageService = (MessageHandlerService) DBConnectionManager.getInstance()
					.getUserBean("messageHandlerService");

			String question = request.getParameter(HTTP_PARAM_MESSAGE);
			log.debug("Getting Questions: " + question);

			if (null == question || question.isEmpty()) {
				webResponse.append("No Question To fetch");
			} else {
				webResponse.append(messageService.getAllQuestions());
			}
			log.debug("Retreiving the Questions" + webResponse.toString());
			ResponseMessageHandler.getInstance(request, response).writeMessage(webResponse.toString());

		} catch (Exception e) {
			log.debug(e.getMessage());
			String errMsg = "MobileEduException thrown in the server:" + e.getMessage();
			log.debug(errMsg);
			ResponseMessageHandler.getInstance(request, response).writeMessage(errMsg);
		}

	}
}
