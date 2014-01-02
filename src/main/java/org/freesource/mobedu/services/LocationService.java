/**
 * 
 */
package org.freesource.mobedu.services;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;

import org.freesource.mobedu.dao.Location;
import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Logger;
import org.freesource.mobedu.utils.XMLContainer;

/**
*
*/
public class LocationService implements Constants {

	private static final Logger logger = Logger
			.getInstance(LocationService.class.getName());

	public LocationService() {
	}

	public Location getLocation(String txtWebMobileHash) throws IOException {

		Location ll = new Location();

		if (txtWebMobileHash == null || txtWebMobileHash.isEmpty()) {
			return null;
		}
		try {
			/*
			 * mobile number in hash format of the user whose location has to be
			 * extracted String txtWebMobileHash = httpRequest
			 * .getParameter(HTTP_PARAM_TXTWEB_MOBILE);
			 */
			String mobileHashParam = "txtweb-mobile="
					+ URLEncoder.encode(txtWebMobileHash, "UTF-8");

			XMLContainer xmlBox = new XMLContainer(new URL(LOCATION_API_URL
					+ "?" + mobileHashParam).openStream());

			String code = "-1";
			code = xmlBox.getTagValue("code");
			// if success, then extract all location params
			if (SUCCESS_CODE.equals(code)) {
				ll.setIsDefaultLocation(xmlBox.getTagValue("default"));
				ll.setUserLocationText(xmlBox.getTagValue("userlocationtext"));
				// Note that City, province, country and postalcode can be null
				ll.setCity(xmlBox.getTagValue("city"));
				ll.setProvince(xmlBox.getTagValue("province"));
				ll.setCountry(xmlBox.getTagValue("country"));
				ll.setPostalCode(xmlBox.getTagValue("postalcode"));
			}
			return ll;
		} catch (Exception e) {
			// Exception handling
			logger.error(Level.SEVERE + "Exception caught" + e);
			return null;
		}
	}
}
