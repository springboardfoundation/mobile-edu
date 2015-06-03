/**
 * 
 */
package org.freesource.mobedu.dao.model.object;

/**
 * @author Md Mustakim
 * 
 */
public class ExpertAnswer {

	private int questionId;
	private int answerId;
	private String answer;
	private int expertId;
	private String expertName;
	private String answerDate;

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getExpertId() {
		return expertId;
	}

	public void setExpertId(int expertId) {
		this.expertId = expertId;
	}

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public String getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(String answerDate) {
		this.answerDate = answerDate;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("QuestionId:").append(getQuestionId());
		sb.append(", AnswerId:").append(getAnswerId());
		sb.append(", ExpertId:").append(getExpertId());
		sb.append(", ExpertName:").append(getExpertName());
		sb.append(", Answer:").append(getAnswer());
		sb.append(", AnswerDate:").append(getAnswerDate());
		sb.append("]");
		return sb.toString();
	}

}
