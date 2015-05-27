package org.freesource.mobedu.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.dao.model.AnswerCluster;
import org.freesource.mobedu.dao.model.User;

public interface AnswerClusterManagerService {
	// Getting the List of answer by ANS_Id
	List<AnswerCluster> getAnswersByAnsId(int answerId);

	// Getting the List of answer by Q_ID
	List<AnswerCluster> getAnswerByQId(int questionID);
	
	//Getting the list of all answers
	List<AnswerCluster>getAllAnswers(int questionID);

	// Sending the answer to Particular user Who ask the Question.
	public String sendAnswerToUser(HttpServletRequest req, HttpServletResponse res, AnswerCluster answerObject,
			User user);

	// Inserting the answer to database(Table- AnswerCluster)
	public String insertAnswer(AnswerCluster ans);
	
	public String getExpName(int expertID);
}
