/**
 * 
 */
package org.freesource.mobedu.dao.impl;

import java.util.List;

import org.freesource.mobedu.dao.UserDAO;
import org.freesource.mobedu.dao.model.User;
import org.freesource.mobedu.utils.Constants;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementor for DAO accessing user object
 * 
 */
@Service
public class UserDAOImpl implements UserDAO, Constants {

	@Autowired
	private SessionFactory sessionFactory;

	public void insertUser(User user) {
		sessionFactory.getCurrentSession().save(user);

	}

	public User getUserById(int userId) {
		return (User) sessionFactory.getCurrentSession()
				.get(User.class, userId);
	}

	public User getUserByMobileHash(String mobileHash) {

		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("mobileId", mobileHash));

		/*
		 * Query query = sessionFactory.getCurrentSession().createQuery(
		 * "from USER_CONTEXT where MOBILE_HASH = :mobile");
		 */
		// query.setParameter("mobile", mobileHash);
		List list = query.list();
		if (list.size() == 0) {
			return null;
		}
		return (User) list.get(0);
	}

	public int getMaxContextId() {
		/*
		 * Query query = sessionFactory.getCurrentSession().createQuery(
		 * "SELECT MAX(CONTEXT_ID) FROM USER_CONTEXT");
		 */
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.setProjection(Projections.max("contextId"));
		List list = criteria.list();
		if (list.size() == 0) {
			return -1;
		}
		return (Integer) list.get(0);
	}

	public void update(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

	public long getNumberOfUser() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				User.class);
		criteria.setProjection(Projections.rowCount());
		List list = criteria.list();
		Long count = (Long) list.get(0);

		return count;
	}

	public List<User> getUsers() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				User.class);
		return criteria.list();
	}

}
