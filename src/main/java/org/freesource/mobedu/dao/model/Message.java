package org.freesource.mobedu.dao.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * The main message class to store the attributes of a message
 * 
 */
@Entity
@Table(name = "MESSAGE_ASSET")

public class Message {
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MSG_ID", nullable = false)
	private int messageId;
	
	@Length(max = 160, message = "Maximum size allowed for message is 160")
	@NotNull(message = "Message cannot be empty")
	@Column(name = "MESSAGE", nullable = false)
	private String message;
	
	@Length(max = 10, message = "Maximum size allowed for message standard code string is 10")
	@Column(name = "MSG_STD")
	private String standard;
	
	@Column(name = "MSG_SUB")
	private String subject;
	
	
	@Column(name = "INSERT_DATE")
	private Date insertDate;
	
	@Column(name = "SENT_DATE")
	private String sentDate;
	
	@Column(name = "IS_ACTIVE")
	private boolean active;
	
	@Column(name = "USER_ID")
	private int userId;
	
	@Column(name = "MSG_TYPE")
	private boolean msgType;

	public Message() {
		active = true;
	}

	/**
	 * @return the messageId
	 */
	public int getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	/**
	 * @return the messageId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}



	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
		// this.message = URLEncoder.encode(message, "UTF-8");
	}

	/**
	 * @return the standard
	 */
	public String getStandard() {
		return standard;
	}

	/**
	 * @param standard
	 *            the standard to set
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the insertDate
	 */
	public Date getInsertDate() {
		return insertDate;
	}

	/**
	 * @param insertDate
	 *            the insertDate to set
	 */
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	/**
	 * @return the sentDate
	 */
	public String getSentDate() {
		return sentDate;
	}

	/**
	 * @param sentDate
	 *            the sentDate to set
	 */
	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * to keep a message active or
	 * to activate a message(message to be replied)
	 */
	public void activateMessage() {
		this.active = true;
	}

	/**
	 *Deactivate a message by setting the active to false
	 */
	public void deActivateMessage() {
		this.active = false;
	}
	
	/*
	 * to set message as question  
	 */
	public void setAsQuestion(){
		this.msgType=false;
	}
	/*
	 * to set message as not a question  
	 */
	public void setAsMessage(){
		this.msgType=true;
	}
	
	/**
	 *@return msgType
	 */
	public boolean getMessageType()
	{
		return msgType;
	}

}
