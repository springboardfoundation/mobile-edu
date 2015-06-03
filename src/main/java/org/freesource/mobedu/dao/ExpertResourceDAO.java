package org.freesource.mobedu.dao;

import java.util.List;

import org.freesource.mobedu.dao.model.ExpertResource;

public interface ExpertResourceDAO {
	void insertExpert(ExpertResource expert);

	ExpertResource getExpertById(int expId);

	ExpertResource getExpertByloginId(String loginId);

	void updateExpert(ExpertResource expert);

	void delete(ExpertResource expert);

	List<ExpertResource> getExpertResource();

}
