package org.freesource.mobedu.dao.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "ANS_CLUSTER")
public class AnswerCluster {
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ANS_ID", nullable = false)
	private int answerId;

	@Length(max = 500, message = "Maximum size allowed for Answer  is 500")
	@NotNull(message = "Answer cannot be null")
	@Column(name = "ANS_CONTENT", nullable = false)
	private String answer;

	@Column(name = "Q_ID")
	private int questionID;

	@Column(name = "E_ID")
	private int expertID;

	@Column(name = "ANS_DATE")
	private Date answerDate;

	@Column(name = "IS_SENT")
	private boolean isSent;

	public AnswerCluster() {
		isSent = true;
	}

	/**
	 * @return the answerId
	 */
	public int getAnswerId() {
		return answerId;
	}

	/**
	 * @param answerId
	 *            the answerId to set
	 */
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
		// this.message = URLEncoder.encode(message, "UTF-8");
	}

	/**
	 * @return the standard
	 */
	public int getQuestionID() {
		return questionID;
	}

	/**
	 * @param questionID
	 *            the questionID to set
	 */
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	/**
	 * @return the subject
	 */
	public int getExpertID() {
		return expertID;
	}

	/**
	 * @param expertID
	 *            the expertID to set
	 */
	public void setExpertID(int expertID) {
		this.expertID = expertID;
	}

	/**
	 * @return the answerDate
	 */
	public Date getAnswerDate() {
		return answerDate;
	}

	/**
	 * @param answerDate
	 *            the answerDate to set
	 */
	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}

	/**
	 * @return the active
	 */
	public boolean isSent() {
		return isSent;
	}

}
