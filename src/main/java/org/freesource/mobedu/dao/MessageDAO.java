package org.freesource.mobedu.dao;

import java.util.List;

import org.freesource.mobedu.dao.model.Message;

public interface MessageDAO {

	// new message inserted;
	void insertMessage(Message message);

	// updating current message object;
	void update(Message message);

	// getting message using its id;
	Message getMessageById(int msgId);

	void delete(Message message);

	// Message getMessageByMobileHash(String mobileHash);
	List<Message> getAllQuestions();

	// returns max id of message table;
	int getMaxMsgId();

	// returns all message of question
	List<Message> getQuestion();

	// returns all message of question which r not answered
	List<Message> getUnAnsweredQ();

	// returns all message of question which r not answered
	List<Message> getAnsweredQ();
}
