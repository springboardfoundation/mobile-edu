/**
 * 
 */
package org.freesource.mobedu.utils;

//Import required java libraries
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.freesource.mobedu.dao.model.Message;
import org.freesource.mobedu.dao.model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 */
public class ResponseMessageHandler implements Constants {
	
	private Logger log = Logger.getInstance("ResponseMessageHandler");

	private ResponseMessageHandler() {
		// Making this so that the use is forced to instantiate
	}

	private static HttpServletRequest request;
	private static HttpServletResponse response;
	private static volatile ResponseMessageHandler thisObj;

	/**
	 * A singleton pattern object being created as there is no need for multiple
	 * instances of this object on the same request/response combination
	 * @param req
	 * @param res
	 * @return
	 */
	public static ResponseMessageHandler getInstance(HttpServletRequest req,
			HttpServletResponse res) {
		ResponseMessageHandler rmh = thisObj;
		if (null == rmh) {
			synchronized (ResponseMessageHandler.class) {
				rmh = thisObj;
				if (null == rmh) {
					request = req;
					response = res;
					thisObj = new ResponseMessageHandler();
					rmh = thisObj;
				}
			}
		}
		return rmh;
	}

	public void writeMessage(String message) throws IOException {
		PrintWriter out = response.getWriter();
		// logging the response before writing
		log.trace("Writing the response:" + message);

		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
		// Write the appkey to the response
		out.print("<meta name=");
		out.print(TXT_APPKEY_NAME);
		out.print(" content=");
		out.print(TXT_SERVICE_APP_KEY);
		out.println(" />");

		// Write the appid to the response
		String s = request.getParameter(TXT_WEBID_NAME);
		if (null != s && !s.isEmpty()) {
			log.debug(TXT_WEBID_NAME + ":" + s);
			out.print("<meta name=");
			out.print(TXT_WEBID_NAME);
			out.print(" content=");
			out.print(s);
			out.println(" />");
		}

		out.println("<meta name=\"description\" content=\"Simple response html page with message in the body\"/>");
		out.println("<title>Mobile Education</title>");
		out.println("</head>");
		out.println("<body>");
		out.println(message);
		out.println("</body>");
		out.println("</html>");
		// A big NOTE: Closing the response to web, nothing more can be written
		out.close();
		out.flush();
	}

	public String pushMessage(Message messageObj, User userObj)
			throws MobileEduException {
		String mobileHash = userObj.getMobileId();
		String message = messageObj.getMessage();

		if (mobileHash == null) {
			mobileHash = "";
		}
		if (message == null) {
			message = "";
		}
		log.trace("pushMessage> Mobile: " + mobileHash + " Message: " + message);

		StringBuilder reply = new StringBuilder();
		reply.append("Push message to User: " + userObj.getContextId());
		int result = sendPushMessage(message, mobileHash);

		if (result == 0) {
			reply.append(" sent successfully!");
		}
		// try again after waiting for some time
		else if (result == -1) {
			int count = 10000;
			while (count-- > 0)
				; // To wait for sometime before retrying
			result = sendPushMessage(message, mobileHash);
			if (result == 0) {
				reply.append(" sent successfully on retry!");
			} else {
				reply.append("Result after trying again " + result);
			}
		} else {
			throw new MobileEduException("Error occured!!! Error code : "
					+ result);
		}
		log.trace("Push Message response string: " + reply);
		return reply.toString();
	}

	/**
	 * 0 Success! <br />
	 * -1 Unknown Exception(Usually Server side) Have a retry logic in place to
	 * call the API again in case such an error code is received or wait till
	 * the APIs are back to being functional. <br />
	 * -3 Invalid input Incorrect format for calling the API Check the right
	 * syntax for making the API call <br />
	 * -101 No such mobile mobile number does not exist <br />
	 * -103 MAX Publisher Allocation exceeded No more than 250 messages per 5
	 * minutes per mobile number No more than 20 messages per 10 seconds per
	 * mobile number <br />
	 * -104 Number registered with NCPR <br />
	 * -300 Missing publisher key Get your publisher key under Build and Manage
	 * my apps section on txtWeb.com and include it in the parameter list of the
	 * API call <br />
	 * -301 Incorrect publisher key Check and verify your publisher key under
	 * Build and Manage my apps section on txtWeb.com against the one you have
	 * sent in the API request call <br />
	 * -400 Missing application key Get the application key of the app under
	 * Build and Manage my apps section on txtWeb.com and include it in the
	 * message body list of the API call <br />
	 * -401 Incorrect application key Check and verify the application key for
	 * the app under Build and Manage my apps section on txtWeb.com against the
	 * one you have sent in the API request call <br />
	 * -402 Maximum Throttle exceeded No more than 5,000 API calls in a single
	 * day <br />
	 * -500 Mobile opted out If a mobile number has opted out from receiving any
	 * message from the app <br />
	 * -600 Missing message Check if you have included the message to be sent in
	 * the right format
	 * 
	 * @throws MobileEduException
	 */
	private int sendPushMessage(String message, String mobileHash)
			throws MobileEduException {

		String head = "<html>" + "<head>"
				+ "<meta name=\"txtweb-appkey\" content=\""
				+ TXT_SERVICE_APP_KEY + "\">" + "</head>" + "<body>";

		String tail = "</body></html>";

		String htmlMessage = head + message + tail;

		try {
			String urlParams = HTTP_PARAM_TXTWEB_MESSAGE + "="
					+ URLEncoder.encode(htmlMessage, "UTF-8") + "&"
					+ HTTP_PARAM_TXTWEB_MOBILE + "=" + mobileHash + "&"
					+ HTTP_PARAM_PUBLISHER_KEY + "="
					+ URLEncoder.encode(TXT_PUBLISHER_ID, "UTF-8");

			log.trace("The push message being sent:" + urlParams);
			// Using DOM parser to parse the XML response from the API
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			// Testing URL: LOCAL_PUSH_MSG_URL and actual: PUSH_MSG_URL
			URLConnection conn = new URL(PUSH_MSG_URL).openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(urlParams);
			wr.flush();

			// Reading the response
			Document doc = db.parse(conn.getInputStream());
			// Use the below for logging the response XML
			log.trace("The response for push message:");
			// Utilities.printXML(doc, System.out);

			NodeList statusNodes = doc.getElementsByTagName("status");
			String code = "-1";
			for (int index = 0; index < statusNodes.getLength(); index++) {
				Node childNode = statusNodes.item(index);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) childNode;
					code = getTagValue("code", element);
					log.debug("Response Code: " + code);
					return Integer.parseInt(code);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new MobileEduException(
					"Sending Push message failed with a runtime exception: ParserConfigurationException",
					e);
		} catch (SAXException e) {
			e.printStackTrace();
			throw new MobileEduException(
					"Sending Push message failed with a runtime exception: SAXException",
					e);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new MobileEduException(
					"Sending Push message failed with a runtime exception: MalformedURLException",
					e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new MobileEduException(
					"Sending Push message failed with a runtime exception: IOException",
					e);
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
			throw new MobileEduException(
					"Sending Push message failed with a runtime exception: NumberFormatException",
					ne);
		}

		return -999; // APPLICATION ERROR
	}

	private String getTagValue(String sTag, Element eElement) {
		NodeList nodeList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node node = nodeList.item(0);
		return node.getNodeValue();
	}
}
