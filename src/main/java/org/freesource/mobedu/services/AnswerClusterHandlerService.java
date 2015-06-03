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
import org.freesource.mobedu.dao.model.AnswerCluster;
import org.freesource.mobedu.dao.model.Message;
import org.freesource.mobedu.dao.model.User;
import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Logger;
import org.freesource.mobedu.utils.MobileEduException;
import org.freesource.mobedu.utils.ResponseMessageHandler;
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

	public String sendAnswerToUser(HttpServletRequest req, HttpServletResponse res, AnswerCluster answerObject,
			User user) {
		StringBuilder answer = new StringBuilder();
		log.debug("Inside sendAnswerToUser");
		try {
			log.debug("sendAnswerToUser: Sending for user: " + user.getContextId());
			Message tempAns = new Message();
			tempAns.setMessage(answerObject.getAnswer());
			String response = ResponseMessageHandler.getInstance(req, res).pushMessage(tempAns, user);
			answer.append("Response=>" + response);
			log.debug("Final response from sendAnswerToUser:" + answer.toString());
		} catch (MobileEduException e) {
			log.error("MobileEduException occurred when processing the user: " + user.getContextId(), e);
			answer.append("MobileEduException occurred: " + e.getMessage() + " when processing the user: "
					+ user.getContextId());
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
