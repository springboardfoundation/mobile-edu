package org.freesource.mobedu.dao;

import java.util.List;

import org.freesource.mobedu.dao.model.object.Question;
import org.freesource.mobedu.utils.MobileEduException;

public interface QuestionManagerService {
	/**
	 * get All messages(queries)from DB to the UI
	 */
	public List<Question> getAllQuestions() throws MobileEduException;

	/**
	 * get these many number of messages(queries)from DB to the UI
	 */
	public Question getAllQuestions(int begin, int end);

	/**
	 * get All unanswered messages(queries)from DB to the UI
	 */
	public Question getUnAnsweredQuestions();

	/**
	 * get these many number of unanswered messages(queries)from DB to the UI
	 */
	public Question getUnAnsweredQuestions(int begin, int end);

	/**
	 * get All answered messages(queries)from DB to the UI
	 */
	public Question getAnsweredQuestions();

	/**
	 * get these many number of answered messages(queries)from DB to the UI
	 */
	public Question getAnsweredQuestions(int begin, int end);

}
