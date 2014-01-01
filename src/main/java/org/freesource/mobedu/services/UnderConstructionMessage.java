/**
 * 
 */
package org.freesource.mobedu.services;

//Import required java libraries
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Reehan
 * 
 * @WebServlet( name = "MyServlet", urlPatterns = {"/hello"} )
 */
public class UnderConstructionMessage extends HttpServlet {

	private static final long serialVersionUID = -4746779576591177425L;
	private String message;

	public void init() throws ServletException {
		// Do required initialization
		message = "This website is still under construction";
	}

	private void writeMessage(PrintWriter out) {
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\" />");
		out.println("<meta name=\"description\" content=\"Error Page: simple and good looking error html page\"/>");
		out.println("<title>Mobile Education</title>");
		out.println("<style>");
		out.println("* { margin: 0; padding: 0; }");

		out.println("html { ");
		out.println("background: url(images/error.jpg) no-repeat center center fixed; ");
		out.println("-webkit-background-size: cover;");
		out.println("-moz-background-size: cover;");
		out.println("-o-background-size: cover;");
		out.println("background-size: cover;");
		out.println("}");

		out.println("#page-wrap {");
		out.println("width: 400px;");
		out.println("margin: 50px auto;");
		out.println("padding: 20px;");
		out.println("background: white;");
		out.println("-moz-box-shadow: 0 0 20px black;");
		out.println("-webkit-box-shadow: 0 0 20px black;");
		out.println("box-shadow: 0 0 20px black; }");
		out.println("p { font: 15px/2 Georgia, Serif; margin: 0 0 30px 0; text-indent: 40px; }");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
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
