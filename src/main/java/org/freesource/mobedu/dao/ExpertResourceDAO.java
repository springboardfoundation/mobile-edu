package org.freesource.mobedu.dao;

import java.util.List;

import org.freesource.mobedu.dao.model.ExpertResource;

public interface ExpertResourceDAO {
void insertExpert(ExpertResource expert);
	
ExpertResource getExpertById(int expertId);
	
	void updateExpert(ExpertResource expert);
	
	void delete(ExpertResource expert);
	
	List<ExpertResource> getExpertResource();

}
