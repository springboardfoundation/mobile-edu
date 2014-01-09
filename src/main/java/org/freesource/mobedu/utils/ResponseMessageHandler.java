/**
 * 
 */
package org.freesource.mobedu.utils;

//Import required java libraries
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class ResponseMessageHandler implements Constants {

	public static void writeMessage(HttpServletRequest request,
			HttpServletResponse response, String message) throws IOException {
		PrintWriter out = response.getWriter();

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
		// A big NOTE: Closing the response to the web, nothing more can be
		// written
		out.close();
	}
}
