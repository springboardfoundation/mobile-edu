package org.freesource.mobedu.dao;

import java.util.List;

import org.freesource.mobedu.dao.model.AnswerCluster;

public interface AnswerClusterDAO {

	void insertAnswer(AnswerCluster answer);

	AnswerCluster getAnswerById(int answerId);

	List<AnswerCluster> getAllAnswersByQuestionId(int questionId);

	void updateAnswer(AnswerCluster answer);

	// void delete(AnswerCluster answer);

	List<AnswerCluster> getAnswerCluster();

	int getMaxANS_ID();
}
