package org.freesource.mobedu.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Message {
	private int messageId;
	private String message;
	private String standard;
	private String subject;
	private String insertDate;
	private String sentDate;
	private boolean active;

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
		try {
			this.message = URLEncoder.encode(message, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.message = message;
			e.printStackTrace();
		}
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
	public String getInsertDate() {
		return insertDate;
	}

	/**
	 * @param insertDate
	 *            the insertDate to set
	 */
	public void setInsertDate(String insertDate) {
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
	 */
	public void activateMessage() {
		this.active = true;
	}

	/**
	 */
	public void deActivateMessage() {
		this.active = false;
	}

}
