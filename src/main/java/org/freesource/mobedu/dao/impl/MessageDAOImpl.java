package org.freesource.mobedu.dao.impl;

/**
 * 
 */

import java.util.List;

import org.freesource.mobedu.dao.MessageDAO;
import org.freesource.mobedu.dao.model.Message;
import org.freesource.mobedu.utils.Constants;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
public class MessageDAOImpl implements MessageDAO, Constants {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void insertMessage(Message message) {
		sessionFactory.getCurrentSession().save(message);

	}

	@Override
	public void update(Message message) {
		sessionFactory.getCurrentSession().update(message);
	}

	@Override
	public void delete(Message message) {
		sessionFactory.getCurrentSession().delete(message);

	}

	@Override
	public List<Message> getAllQuestions() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class);
		criteria.add(Restrictions.eq("msgType", false));
		criteria.addOrder(Order.desc("messageId"));

		List list = criteria.list();
		if (list.size() == 0) {
			return null;
		}
		return (List<Message>) list;
	}

	@Override
	public int getMaxMsgId() {
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class)
					.setProjection(Projections.max("messageId"));
			List list = criteria.list();
			if (list.size() == 0) {
				return 0;
			}
			return (Integer) list.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Message> getQuestion() {
		// creates a query condition
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Message.class)
				.add(Restrictions.eq("msgType", false));
		query.addOrder(Order.desc("messageId"));
		// this statement executes query returns value
		List list = query.list();
		if (list.size() == 0) {
			return null;
		}
		return (List<Message>) list;
	}

	@Override
	public List<Message> getUnAnsweredQ() {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Message.class)
				.add(Restrictions.eq("msgType", false)).add(Restrictions.eq("active", true));
		query.addOrder(Order.desc("messageId"));

		List list = query.list();
		if (list.size() == 0) {
			return null;
		}
		return (List<Message>) list;
	}

	@Override
	public List<Message> getAnsweredQ() {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Message.class)
				.add(Restrictions.eq("msgType", false)).add(Restrictions.eq("active", false));
		query.addOrder(Order.desc("messageId"));

		List list = query.list();
		if (list.size() == 0) {
			return null;
		}
		return (List<Message>) list;
	}

	@Override
	public Message getMessageById(int msgId) {
		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(Message.class)
				.add(Restrictions.eq("messageId",msgId));//  creates a query condition
		List list = query.list();// this statement executes query returns value
		if (list.size() == 0) {
			return null;
		}
		return (Message) list.get(0);		// TODO Auto-generated method stub
	}

}
