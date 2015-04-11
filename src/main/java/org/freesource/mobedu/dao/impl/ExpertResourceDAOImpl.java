package org.freesource.mobedu.dao.impl;
import java.util.List;

import org.freesource.mobedu.dao.ExpertResourceDAO;
import org.freesource.mobedu.dao.model.ExpertResource;
import org.freesource.mobedu.utils.Constants;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional

public  class ExpertResourceDAOImpl implements ExpertResourceDAO, Constants {
	@Autowired
	private SessionFactory sessionFactory;

	public void insertExpert(ExpertResource expert) {
		sessionFactory.getCurrentSession().save(expert);
		
	}

	public ExpertResource getExpertById(int expertId) {
		
		return (ExpertResource) sessionFactory.getCurrentSession()
				.get(ExpertResource.class, expertId);
	}

	public void updateExpert(ExpertResource expert) {
		sessionFactory.getCurrentSession().update(expert);
		
	}

	public List<ExpertResource> getExpertResource() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ExpertResource.class);
		return criteria.list();
	}

	public void delete(ExpertResource expert) {
		// TODO Auto-generated method stub
		
	}
}
