package org.freesource.mobedu.services;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.core.Constants;

import org.freesource.mobedu.dao.AnswerClusterDAO;
import org.freesource.mobedu.dao.ExpertResourceDAO;
import org.freesource.mobedu.dao.ExpertResourceManagerService;
import org.freesource.mobedu.dao.MessageDAO;
import org.freesource.mobedu.dao.QuestionManagerService;
import org.freesource.mobedu.dao.model.AnswerCluster;
import org.freesource.mobedu.dao.model.Message;
import org.freesource.mobedu.dao.model.object.ExpertAnswer;
import org.freesource.mobedu.dao.model.object.Question;
import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.utils.Logger;
import org.freesource.mobedu.utils.MobileEduException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionHandlerService implements Constants,
		QuestionManagerService {

	@Autowired
	private MessageDAO msgDAO;

	@Autowired
	private AnswerClusterDAO ansDAO;

	@Autowired
	private ExpertResourceDAO expDAO;

	private Logger log = Logger.getInstance("ExpertResourceHandlerService");

	@Override
	public List<Question> getAllQuestions() throws MobileEduException {
		List<Message> msgLst = (List<Message>) msgDAO.getAllQuestions();
		log.debug("LIST OF QUESTION" + msgLst);
		List<Question> qList = new ArrayList<Question>();
		for (Message m : msgLst) {
			Question q = new Question();
			q.setQuestionId(m.getMessageId());
			q.setQuestion(m.getMessage());
			q.setQuestionDate(m.getInsertDate());

			q.setAnswersList(getAllAnswers(q.getQuestionId()));
			qList.add(q);
		}

		return qList;
	}

	private List<ExpertAnswer> getAllAnswers(int questionId)
			throws MobileEduException {
		List<AnswerCluster> ansLst = (List<AnswerCluster>) ansDAO
				.getAllAnswersByQuestionId(questionId);
		List<ExpertAnswer> expAns = new ArrayList<ExpertAnswer>();
		ExpertResourceManagerService exp = (ExpertResourceManagerService) DBConnectionManager
				.getInstance().getUserBean("expertResourceHandlerService");
		if (null != ansLst) {

			for (AnswerCluster ls : ansLst) {
				ExpertAnswer ans = new ExpertAnswer();
				ans.setExpertId(ls.getAnswerId());
				ans.setAnswer(ls.getAnswer());
				ans.setExpertId(ls.getExpertID());
				ans.setExpertName(exp.getname(ls.getExpertID()));
				ans.setAnswerDate(ls.getAnswerDate());
				expAns.add(ans);
			}
		}
		return expAns;
	}

	@Override
	public Question getAllQuestions(int begin, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Question getUnAnsweredQuestions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Question getUnAnsweredQuestions(int begin, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Question getAnsweredQuestions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Question getAnsweredQuestions(int begin, int end) {
		// TODO Auto-generated method stub
		return null;
	}

}
