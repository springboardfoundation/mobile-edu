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
public class HelloWorld extends HttpServlet {

	private static final long serialVersionUID = -4746779576591177425L;
	private String message;

	public void init() throws ServletException {
		// Do required initialization
		message = "Hello World";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + "</h1>");
	}

	public void destroy() {
		// do nothing.
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Am the main class, please find me!");
		Server server = new Server(Integer.valueOf(System.getenv("PORT"))
				.intValue());
		ServletContextHandler context = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		context.addServlet(new ServletHolder(new HelloWorld()), "/HelloWorld");
		server.start();
		server.join();
	}
}
