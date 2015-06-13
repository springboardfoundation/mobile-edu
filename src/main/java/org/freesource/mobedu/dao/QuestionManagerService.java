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
	public List<Question> getAllQuestions(int begin, int end);

	/**
	 * get All unanswered messages(queries)from DB to the UI
	 */
	public List<Question> getUnAnsweredQuestions() throws MobileEduException;
	/**
	 * get All current expert's answered messages(queries)from DB to the UI
	 */
	public List<Question> getMyAnsweredQuestions() throws MobileEduException;


	/**
	 * get these many number of unanswered messages(queries)from DB to the UI
	 */
	public List<Question> getUnAnsweredQuestions(int begin, int end);

	/**
	 * get All answered messages(queries)from DB to the UI
	 */
	public List<Question> getAnsweredQuestions() throws MobileEduException;

	/**
	 * get these many number of answered messages(queries)from DB to the UI
	 */
	public List<Question> getAnsweredQuestions(int begin, int end);

}
