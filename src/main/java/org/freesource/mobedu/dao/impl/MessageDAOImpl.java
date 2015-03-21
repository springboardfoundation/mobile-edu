package org.freesource.mobedu.dao.impl;

/**
 * 
 */

import org.freesource.mobedu.dao.MessageDAO;
import org.freesource.mobedu.dao.model.Message;
import org.freesource.mobedu.utils.Constants;
import org.hibernate.SessionFactory;
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

	public void insertMessage(Message message) {
		sessionFactory.getCurrentSession().save(message);

	}

	public void update(Message message) {
		sessionFactory.getCurrentSession().update(message);
	}

	public void delete(Message message) {
		sessionFactory.getCurrentSession().delete(message);

	}

}
