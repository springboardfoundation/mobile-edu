package org.freesource.mobedu.dao.model.object;

import java.util.Date;
import java.util.List;

public class Question {
	private int questionId;
	private String question;
	private Date questionDate;
	private List<ExpertAnswer> answerList;

	/**
	 * @return the questionId
	 */
	public int getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId
	 *            the questionId to set
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the questionDate
	 */
	public Date getQuestionDate() {
		return questionDate;
	}

	/**
	 * @param questionDate
	 *            the questionDate to set
	 */
	public void setQuestionDate(Date questionDate) {
		this.questionDate = questionDate;
	}

	/**
	 * @return the answersList
	 */
	public List<ExpertAnswer> getAnswersList() {
		return answerList;
	}

	/**
	 * @param answersList
	 *            the answersList to set
	 */
	public void setAnswersList(List<ExpertAnswer> answersList) {
		this.answerList = answersList;
	}
	
	public void addAnswer(ExpertAnswer ans){
		this.answerList.add(ans);
	}
}
