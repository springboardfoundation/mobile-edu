/**
 * 
 */
package org.freesource.mobedu.dao.model;

/**
 * The location class container to hold all the location attributes
 */
public class Location {

	String defaultLocation = "";
	String userLocationText = "";
	String city = "";
	String province = "";
	String country = "";
	String postalCode = "";
	String response = "";

	public Location() {
	}

	/**
	 * If this value is set to true, it means the location has been set by the
	 * user, else it is a value that has been guesses by the platform from the
	 * end user's mobile number
	 * 
	 * @return the defaultLocation
	 */
	public String getIsDefaultLocation() {
		return defaultLocation;
	}

	/**
	 * @param defaultLocation
	 *            the defaultLocation to set
	 */
	public void setIsDefaultLocation(String isDefaultLocation) {
		this.defaultLocation = isDefaultLocation;
	}

	/**
	 * @return the userLocationText
	 */
	public String getUserLocationText() {
		return userLocationText;
	}

	/**
	 * @param userLocationText
	 *            the userLocationText to set
	 */
	public void setUserLocationText(String userLocationText) {
		this.userLocationText = userLocationText;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}

}
