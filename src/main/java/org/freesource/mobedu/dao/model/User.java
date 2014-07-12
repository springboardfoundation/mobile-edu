package org.freesource.mobedu.dao.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * The main user class to store the attributes of a user
 * 
 */
@Entity
@Table(name = "USER_CONTEXT")
public class User {

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONTEXT_ID", nullable = false)
	private int contextId;

	@Length(max = 50, message = "Maximum size allowed for the mobile hash code is 50")
	@NotNull(message = "Mobile Hash of the user cannot be null")
	@Column(name = "MOBILE_HASH", nullable = false)
	private String mobileId;

	@Length(max = 5, message = "Maximum size allowed for resistered standard code string is 5")
	@Column(name = "REG_STD")
	private String regStd;

	@Length(max = 10, message = "Maximum size allowed for registered standard code string is 10")
	@Column(name = "REG_SUB")
	private String regSubject;

	@Column(name = "REG_DATE")
	private Date regDate;

	@Column(name = "IS_ACTIVE")
	private boolean active;

	@Length(max = 50, min = 0, message = "Maximum size allowed location string is 50!")
	@Column(name = "LOCATION")
	private String location;

	@Length(max = 5, message = "Maximum size allowed for protocol string is 5")
	@Column(name = "PROTOCOL")
	private String protocol;

	public User() {
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

	/**
	 * Two User objects are equal if their mobile hash are the same. This
	 * assumes that TxtWeb generates unique case in-sensitive mobile hash for
	 * each mobile number.
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}
		if (((User) obj).getMobileId().equalsIgnoreCase(this.getMobileId())) {
			return true;
		}
		return false;
	}

	public void copyUser(User u) {
		this.contextId = u.getContextId();
		this.mobileId = u.getMobileId();
		this.regStd = u.getRegStandard();
		this.regSubject = u.getRegSubject();
		this.regDate = u.getRegDate();
		this.active = u.isActive();
		this.location = u.getLocation();
		this.protocol = u.getProtocol();
	}

	public String toString() {
		StringBuilder identity = new StringBuilder();
		identity.append("Context ID:" + this.getContextId() + "::");
		identity.append("Mobile Hash:" + this.getMobileId() + "::");
		identity.append("Standard:" + this.getRegStandard() + "::");
		identity.append("Subject:" + this.getRegSubject() + "::");
		identity.append("Registration Date:" + this.getRegDate() + "::");

		return identity.toString();

	}
}
