package org.freesource.mobedu.services;

import net.sf.cglib.core.Constants;

import org.freesource.mobedu.dao.ExpertResourceDAO;
import org.freesource.mobedu.dao.ExpertResourceManagerService;
import org.freesource.mobedu.dao.MessageDAO;
import org.freesource.mobedu.dao.model.ExpertResource;
import org.freesource.mobedu.dao.model.object.Expert;
import org.freesource.mobedu.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpertResourceHandlerService implements Constants,
		ExpertResourceManagerService {
	@Autowired
	private ExpertResourceDAO expDAO;
	@Autowired
	private MessageDAO msgDAO;

	private Logger log = Logger.getInstance("ExpertResourceHandlerService");

	public Expert checkExpert(String loginid, String password) {
		ExpertResource userLogin;
		log.debug("userExpert:" + loginid + ", pass word:" + password);
		userLogin = expDAO.getExpertByloginId(loginid);
		log.debug("DB Expert Object:" + userLogin);
		if (null != userLogin && userLogin.getExpertPass().equals(password)) {
			// set all required sessions n return success
			Expert exp = new Expert();
			exp.setId(userLogin.getExpertId());
			exp.setLoginId(userLogin.getExpertLogin());
			exp.setName(userLogin.getExpertName());
			return exp;
		}
		log.debug("Login faulire: Returning null");
		return null;
	}

	@Override
	public void getExpertByMail(String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getExpertLogin(String loginId) {
		// TODO Auto-generated method stub

	}
	public String getname(int expId){
		ExpertResource exp;
		exp=expDAO.getExpertById(expId);
		return exp.getExpertName();
	}

}
