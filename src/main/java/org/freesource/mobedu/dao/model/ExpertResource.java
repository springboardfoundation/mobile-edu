package org.freesource.mobedu.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
@Entity
@Table(name = "EXP_RESOURCE")

public class ExpertResource{
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EXP_ID", nullable = false)
	private int expertId;
	
	@Length(max = 100, message = "Maximum size allowed for Name  is 100")
	@NotNull(message = "Name cannot be null")
	@Column(name = "EXP_NAME", nullable = false)
	private String expertName;
	
	@Column(name = "EXP_EMAIL")
	private String expertEMail;
	
	@Column(name = "EXP_CONTACT")
	private String expertContact;
	
	@Column(name = "IS_ACTIVE")
	private boolean isActive;
	
	@Column(name="EXP_LOGIN")
	private String expertLogin;
	
	@Column(name="EXP_PASS")
	private String expertPass;

	public ExpertResource() {
		isActive = true;
	}

	/**
	 * @return the expertId
	 */
	public int getExpertId() {
		return expertId;
	}

	/**
	 * @param expertId
	 *            the expertId to set
	 */
	public void setExpertId(int expertId) {
		this.expertId = expertId;
	}

	/**
	 * @return the expertName
	 */
	public String getExpertName() {
		return expertName;
	}

	/**
	 * @param expertName
	 *            the expertName to set
	 */
	public void setExpertName(String expertName) {
		this.expertName = expertName;
		// this.expertName = URLEncoder.encode(expertName, "UTF-8");
	}

	/**
	 * @return the expertEMail
	 */
	public String getExpertEMail() {
		return expertEMail;
	}

	/**
	 * @param expertEMail
	 *            the expertEMail to set
	 */
	public void setExpertEMail(String expertEMail) {
		this.expertEMail = expertEMail;
	}

	/**
	 * @return the expertContact
	 */
	public String getExpertContact() {
		return expertContact;
	}

	/**
	 * @param expertContact
	 *            the expertContact to set
	 */
	public void setExpertContact(String expertContact) {
		this.expertContact = expertContact;
	}

	
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @return the expertLogin
	 */
	public String getExpertLogin() {
		return expertLogin;
	}

	/**
	 * @param expertLogin
	 *            the expertLogin to set
	 */
	public void setExpertLogin(String expertLogin) {
		this.expertLogin = expertLogin;
	}
	/**
	 * @return the expertPass
	 */
	public String getExpertPass() {
		return expertPass;
	}

	/**
	 * @param expertPass
	 *            the expertPass to set
	 */
	public void setExpertPass(String expertPass) {
		this.expertPass = expertPass;
	}
}
