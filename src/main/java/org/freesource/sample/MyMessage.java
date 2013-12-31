/**
 * 
 */
package org.freesource.sample;

//Import required java libraries
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author Reehan
 * 
 */
// Extend HttpServlet class
public class MyMessage extends HttpServlet {

	private static final long serialVersionUID = -4746779576591177425L;
	private String message;

	public void init() throws ServletException {
		// Do required initialization
		message = "Hello World";
	}

	private void writeMessage(PrintWriter out) {
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
		out.println("<meta name=\"Required key\" value=\"VppDnvv75-BtWrq36T8BVvyLke\">");
		out.println("<title>Insert title here</title>");
		out.println("</head>");
		out.println("<body bgcolor=white>");
		out.println("<table border=\"0\" cellpadding=\"10\">");
		out.println("<tr>");
		out.println("<td>");
		out.println("<img src=\"images/freesource.png\">");
		out.println("</td>");
		out.println("<td>");
		out.println("<h1>\"Hello, World.\"</h1>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");

		out.println("<p>Am still under construction. Please wait till the work is complete </p>");
		out.println("<p>Please visit the following links, if they are working:");
		out.println("<ul>");
		out.println("<li>To a <a href=\"index.jsp\">JSP page</a>");
		out.println("<li>To a <a href=\"/HelloWorld\">servlet</a>");
		out.println("</ul>");
		out.println("</body>");
		out.println("</html>");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		writeMessage(out);
		// out.println("<h1>" + message + "</h1>");
	}

	public void destroy() {
		// do nothing.
	}
}
