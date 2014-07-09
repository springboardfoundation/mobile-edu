package org.freesource.mobedu.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;

/**
 * The main user class to store the attributes of a user
 * 
 */
public class Users {

	private int contextId;
	private String mobileId;
	private String regStd;
	private String regSubject;
	private Date regDate;
	private boolean active;
	private String location;
	private String protocol;

	public Users() {
		active = false;
	}

	/**
	 * @return the contextId
	 */
	public final int getContextId() {
		return contextId;
	}

	/**
	 * @param contextId
	 *            the contextId to set
	 */
	public void setContextId(int contextId) {
		this.contextId = contextId;
	}

	/**
	 * @return the mobileId
	 */
	public final String getMobileId() {
		return mobileId;
	}

	/**
	 * @param mobileId
	 *            the mobileId to set
	 */
	public final void setMobileId(String mobileId) {
		try {
			this.mobileId = URLEncoder.encode(mobileId, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.mobileId = mobileId;
			e.printStackTrace();
		}
	}

	/**
	 * @return the regStd
	 */
	public final String getRegStandard() {
		return regStd;
	}

	/**
	 * @param regStd
	 *            the regStd to set
	 */
	public final void setRegStandard(String regStd) {
		this.regStd = regStd;
	}

	/**
	 * @param regSub
	 *            the regSububject to set
	 */
	public final void setRegSubject(String regSub) {
		this.regSubject = regSub;
	}

	/**
	 * @return the regSubject
	 */
	public final String getRegSubject() {
		return regSubject;
	}

	/**
	 * @return the registration date to set in java.sql.Date format
	 */
	public final Date getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate
	 *            the registration date to set in java.sql.Date format
	 */
	public final void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the active
	 */
	public final boolean isActive() {
		return active;
	}

	/**
	 * Activate the user by setting the active to true
	 */
	public final void activateUser() {
		this.active = true;
	}

	/**
	 * Deactivate the user by setting the active to false
	 */
	public final void deActivateUser() {
		this.active = false;
	}

	/**
	 * @return the location
	 */
	public final String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public final void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * SMS: 1000 <br />
	 * USSD: 1001 <br />
	 * WEB: 200x <br />
	 * EMULATOR: 2100 <br />
	 * INSTANT MESSENGER: 220x <br />
	 * MISSED CALL:1002 <br />
	 * ANDROID: 3000 <br />
	 * 
	 * @param protocol
	 *            the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void copyUser(Users u) {
		this.contextId = u.getContextId();
		this.mobileId = u.getMobileId();
		this.regStd = u.getRegStandard();
		this.regSubject = u.getRegSubject();
		this.regDate = u.getRegDate();
		this.active = u.isActive();
		this.location = u.getLocation();
		this.protocol = u.getProtocol();
	}
}
