package org.freesource.mobedu.dao;


import java.util.List;

import org.freesource.mobedu.dao.model.AnswerCluster;
public interface AnswerClusterDAO{

	void insertAnswer(AnswerCluster answer);
	
	List<AnswerCluster> getAllAnswersByQuestionId(int questionId);
	
	void updateAnswer(AnswerCluster answer);
	
	AnswerCluster getAnswerById(String answerId);
	//void delete(AnswerCluster answer);
	
	List<AnswerCluster> getAnswerCluster();


}
