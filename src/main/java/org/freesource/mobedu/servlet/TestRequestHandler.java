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

import org.freesource.mobedu.db.DBConnectionManager;
import org.freesource.mobedu.utils.MobileEduException;

public class TestRequestHandler extends HttpServlet {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -5057969347707865613L;

	public void init() throws ServletException {
		// Do required initializations here
		try {
			DBConnectionManager.getInstance();
		} catch (MobileEduException e) {
			System.out.println("Exception in intilization of the DB Connection Manager");
			e.printStackTrace();
		}
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/xml");

		// Actual logic goes here.
		if (-1 == processRequest(request, response)) {
			// Failed to read from xml file? No Problem. Send Manually
			PrintWriter out = response.getWriter();
			out.println(generateMessage());
		}
	}

	private int processRequest(HttpServletRequest req, HttpServletResponse res) {
		InputStream inputStream = null;
		BufferedReader br = null;
		try {
			PrintWriter out = res.getWriter();
			inputStream = TestRequestHandler.class.getClassLoader()
					.getResourceAsStream("SuccessResponse.xml");
			br = new BufferedReader(new InputStreamReader(inputStream));
			while (true) {
				String s = br.readLine();
				if (s == null)
					break;
				out.println(s);
				out.flush();
			}
			out.close();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		// This is returned only if an exception occurred
		return -1;
	}

	private String generateMessage() {
		StringBuilder message = new StringBuilder();
		message.append("<?xml version=\"1.0\"?>");
		message.append("<txtWeb>");
		message.append("<status>");
		message.append("<code>0</code>");
		message.append("<message>success</message>");
		message.append("</status>");
		message.append("</txtWeb>");
		return message.toString();
	}

	public void destroy() {
		// do nothing.
	}

}
