package org.freesource.mobedu.dao;

import org.freesource.mobedu.dao.model.Message;
public interface MessageDAO {

	void insertMessage(Message message);

	void update(Message message);

	//Message getMessageById(int msgId);
	void delete(Message message);

	//Message getMessageByMobileHash(String mobileHash);
	int  getMaxMsgId();
	//returns max id of message table;

}
