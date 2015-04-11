package org.freesource.mobedu.dao.impl;

/**
 * 
 */

import java.util.List;

import org.freesource.mobedu.dao.AnswerClusterDAO;
import org.freesource.mobedu.dao.model.AnswerCluster;
import org.freesource.mobedu.utils.Constants;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementor for DAO accessing user object
 * 
 */
@Service
@Transactional
public class AnswerClusterDAOImpl implements AnswerClusterDAO, Constants {

	@Autowired
	private SessionFactory sessionFactory;

	public void insertAnswer(AnswerCluster answer) {
		sessionFactory.getCurrentSession().save(answer);

	}

	public List<AnswerCluster> getAnswerCluster() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				AnswerCluster.class);
		return criteria.list();
	}

	public AnswerCluster getAnswerById(int answerId) {
		return (AnswerCluster) sessionFactory.getCurrentSession().get(
				AnswerCluster.class, answerId);

	}

	public void updateAnswer(AnswerCluster answer) {
		sessionFactory.getCurrentSession().update(answer);

	}

	public AnswerCluster getAnswerById(String expertId) {
		return (AnswerCluster) sessionFactory.getCurrentSession().get(
				AnswerCluster.class, expertId);
	}

	public List<AnswerCluster> getAllAnswersByQuestionId(int questionId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				AnswerCluster.class);
		criteria.add(Restrictions.eq("questionID", questionId));
		return criteria.list();
	}
}
