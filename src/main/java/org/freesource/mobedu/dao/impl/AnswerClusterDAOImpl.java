package org.freesource.mobedu.dao.impl;

/**
 * 
 */

import java.util.List;

import org.freesource.mobedu.dao.AnswerClusterDAO;
import org.freesource.mobedu.dao.model.AnswerCluster;
import org.freesource.mobedu.dao.model.Message;
import org.freesource.mobedu.utils.Constants;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
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

	public void delete(AnswerCluster answer) {
		sessionFactory.getCurrentSession().delete(answer);

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

	public AnswerCluster getAnswerByExpertId(int expertId) {
		return (AnswerCluster) sessionFactory.getCurrentSession().get(
				AnswerCluster.class, expertId);
	}

	@SuppressWarnings("unchecked")
	public List<AnswerCluster> getAllAnswersByQuestionId(int questionId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				AnswerCluster.class);
		criteria.add(Restrictions.eq("questionID", questionId));
	
		List list = criteria.list();
		if (list.size() == 0) {
			return null;
		}
		return (List<AnswerCluster>)list;
	} 

	public int getMaxANS_ID() {
		/*
		 * Query query = sessionFactory.getCurrentSession().createQuery(
		 * "SELECT MAX(ANS_ID) FROM ANSWERCLUSTER");
		 */
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(AnswerCluster.class)
					.setProjection(Projections.max("answerId"));
			List list = criteria.list();
			if (list.size() == 0) {
				return -1;
			}
			return (Integer) list.get(0);
		} catch (Exception ex) {
			return -1;
		}
	}
}
