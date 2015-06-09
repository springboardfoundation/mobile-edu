package org.freesource.mobedu.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.AnswerClusterDAO;
import org.freesource.mobedu.dao.AnswerClusterManagerService;
import org.freesource.mobedu.dao.ExpertResourceDAO;
import org.freesource.mobedu.dao.ExpertResourceManagerService;
import org.freesource.mobedu.dao.MessageDAO;
import org.freesource.mobedu.dao.UserDAO;
import org.freesource.mobedu.dao.model.AnswerCluster;
import org.freesource.mobedu.dao.model.Message;
import org.freesource.mobedu.dao.model.User;
import org.freesource.mobedu.dao.model.object.ExpertAnswer;
import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Logger;
import org.freesource.mobedu.utils.MobileEduException;
import org.freesource.mobedu.utils.ResponseMessageHandler;
import org.freesource.mobedu.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerClusterHandlerService implements AnswerClusterManagerService, Constants {
	@Autowired
	private AnswerClusterDAO answerClusterDAO;
	@Autowired
	private ExpertResourceDAO expDAO;

	@Autowired
	private MessageDAO msgDAO;
	@Autowired
	private UserDAO usrDao;
	private Logger log = Logger.getInstance("AnswerClusterHandlerService");

	public List<AnswerCluster> getAnswersById(int answerId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<AnswerCluster> getAnswersByAnsId(int answerId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<AnswerCluster> getAnswerByQId(int questionID) {
		// TODO Auto-generated method stub
		return null;
	}

	public String sendAnswerToUser(ExpertAnswer expObj, HttpServletRequest req, HttpServletResponse res) {
		StringBuilder answer = new StringBuilder();
		log.debug("Inside answerReply");
		Message argMsgObj = new Message();
		User usrObj = new User();
		try {
			log.debug("Inside try");

			Message msgObj = msgDAO.getMessageById(expObj.getQuestionId());
			log.debug("Question is to be :" + msgObj.getMessage());
			log.debug("user context id:" + msgObj.getUserId());
			int id = msgObj.getUserId();
			usrObj = usrDao.getUserById(id);
			log.debug("Inside answerReply user:" + usrObj);

			// Set the argument message object and sent to the SMS sender
			argMsgObj.setAsQuestion();
			argMsgObj.setMessage(expObj.getAnswer());
			argMsgObj.setUserId(msgObj.getUserId());
			String response = ResponseMessageHandler.getInstance(req, res).pushMessage(argMsgObj, usrObj);
			log.debug("The return from pushMessage:" + response);
			if (response.contains("sent")) {
				// deactivating message (updating) and inserting answer to db
				msgObj.deActivateMessage();
				msgDAO.update(msgObj);
				AnswerCluster ansObj = new AnswerCluster();
				ansObj.setExpertID(expObj.getExpertId());
				ansObj.setAnswerId(expObj.getAnswerId());
				ansObj.setAnswer(expObj.getAnswer());
				ansObj.setQuestionID(expObj.getQuestionId());
				ansObj.setAnswerDate(new java.sql.Date(Utilities.getCurrentTimestamp().getTime()));
				log.debug("the answer object to be inserted :" + ansObj);
				insertAnswer(ansObj);
				return "Success";
			}
		} catch (MobileEduException e) {
			log.error("MobileEduException occurred when processing the user: " + usrObj.getContextId(), e);
			answer.append("MobileEduException occurred: " + e.getMessage() + " when processing the user: "
					+ usrObj.getContextId());
		}
		log.debug("sendAnswerToUser: Message Handler response: " + answer);
		return answer.toString();
	}

	public String insertAnswer(AnswerCluster answer) {
		int nextAnswerId = answerClusterDAO.getMaxANS_ID();
		if (-1 == nextAnswerId) {
			nextAnswerId = 1;
		} else {
			nextAnswerId++;
		}
		answer.setAnswerId(nextAnswerId);

		answerClusterDAO.insertAnswer(answer);
		return null;
	}

	public List<AnswerCluster> getAllAnswers(int questionID) {
		List<AnswerCluster> ansLst = (List<AnswerCluster>) answerClusterDAO.getAllAnswersByQuestionId(questionID);
		List<AnswerCluster> expAns = new ArrayList<AnswerCluster>();
		ExpertResourceManagerService ansClus = null;
		try {
			ansClus = (ExpertResourceManagerService) DBConnectionManager.getInstance().getUserBean(
					"expertResourceHandlerService");
		} catch (MobileEduException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != ansLst) {

			for (AnswerCluster ls : ansLst) {
				AnswerCluster ans = new AnswerCluster();
				ans.setExpertID(ls.getAnswerId());
				ans.setAnswer(ls.getAnswer());
				ans.setExpertID(ls.getExpertID());
				ans.setAnswerDate(ls.getAnswerDate());
				expAns.add(ans);
			}
		}
		return expAns;
	}

	public String getExpName(int expertID) {
		// TODO Auto-generated method stub
		return null;
	}

}
