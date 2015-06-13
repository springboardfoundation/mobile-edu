/**
 * 
 */
package org.freesource.mobedu.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freesource.mobedu.utils.Constants;
import org.freesource.mobedu.utils.Logger;

public class ExpertSessionHandler extends HttpServlet implements Constants {
	private Logger log = Logger.getInstance("ExpertSessionHandler");

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -4878682724627354734L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NullPointerException {
		String paramExpId = "0";
		String paramExpName = "Guest";
		String paramExpLoginId = "guest";
		log.debug("Inside ExpertSessionHandler");
		try {
			log.debug("inside try start");
			paramExpId = request.getParameter(HTTP_PARAM_EXPERTID);
			paramExpName = request.getParameter(HTTP_PARAM_EXPERT_NAME);
			paramExpLoginId = request.getParameter(HTTP_PARAM_EXPERT_LOGINID);
			log.debug("inside try finish");
		} catch (Exception e) {
			log.debug("inside null catch");
			paramExpId = "0";
			log.debug("paramExpId :" + paramExpId);
			log.debug("paramExpName :" + paramExpName);
			log.debug("paramExpLoginId :" + paramExpLoginId);
			e.printStackTrace();
		}

		log.debug("after null catch");
		log.debug("paramExpId :" + paramExpId);
		if (paramExpId == null) {
			paramExpId = "0";
			paramExpName = "Guest";
			paramExpLoginId = "guest";
			log.debug("inside if null paramExpId :" + paramExpId);

		}

		InputStream inputStream = null;
		BufferedReader br = null;

		// Actual logic goes here.

		try {
			int expId = Integer.parseInt(paramExpId.trim());
			log.debug("paramExpName :" + paramExpName);
			log.debug("expId :" + expId);
			String expName = paramExpName.trim();
			String expLoginId = paramExpLoginId.trim();

			// Set response content type
			response.setContentType("text/html");

			PrintWriter out = response.getWriter();
			inputStream = ExpertSessionHandler.class.getClassLoader()
					.getResourceAsStream("expertuipart.html");
			br = new BufferedReader(new InputStreamReader(inputStream));
			while (true) {
				String s = br.readLine();
				if (s == null)
					break;
				out.println(s);

			}
			// Now the file is written, write the end setting the required
			// values
			out.println("<script type=\"text/javascript\">");
			out.println("function setSession(){");
			out.println("setExpId(" + expId + ");");
			out.println("setExpName('" + expName + "');");
			out.println("setExpLoginId('" + expLoginId + "');");
			out.println("}");
			out.println("</script>");
			out.println("</body>");
			out.println("</html>");
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
			// Failed to read from html file? Send Failure Manually
			PrintWriter out = response.getWriter();
			out.println(generateFailureMessage());
		}

		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	private String generateFailureMessage() {
		StringBuilder message = new StringBuilder();
		message.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		message.append("<html>");
		message.append("<head>");
		message.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		message.append("<title>Expert Page</title>");
		message.append("</head>");
		message.append("<body>");
		message.append("<div id=\"mybody\" align=\"center\">");

		message.append("<h1>Expert Page</h1>");
		message.append("<h2>Unable to load the UI page</h2>");
		message.append("There has been some exception while loading the expert page. <br />");
		message.append("Please try after sometime. <br />");
		message.append("If the issue persists then contact the system administration at engineersforsociety@gmail.com");

		message.append("</div>");
		message.append("</body>");
		message.append("</html>");
		return message.toString();
	}
}
