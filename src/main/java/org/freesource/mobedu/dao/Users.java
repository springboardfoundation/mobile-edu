package org.freesource.mobedu.dao;

/**
 * The main user class to store the attributes of a user
 * 
 */
public class Users {

	private int contextId;
	private String mobileId;
	private String regStd;
	private String regDate;
	private boolean active;
	private String location;
	private String protocol;

	public Users() {
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
		this.mobileId = mobileId;
	}

	/**
	 * @return the regStd
	 */
	public final String getRegStd() {
		return regStd;
	}

	/**
	 * @param regStd
	 *            the regStd to set
	 */
	public final void setRegStd(String regStd) {
		this.regStd = regStd;
	}

	/**
	 * @return the regDate
	 */
	public final String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate
	 *            the regDate to set
	 */
	public final void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the active
	 */
	public final boolean isActive() {
		return active;
	}

	/**
	 * 
	 */
	public final void activateUser() {
		this.active = true;
	}

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
	 * @param protocol
	 *            the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void copyUser(Users u) {
		this.contextId = u.getContextId();
		this.mobileId = u.getMobileId();
		this.regStd = u.getRegStd();
		this.regDate = u.getRegDate();
		this.active = u.isActive();
		this.location = u.getLocation();
		this.protocol = u.getProtocol();
	}
}
