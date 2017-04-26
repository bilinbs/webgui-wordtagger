package com.wordtagger;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TaggerServlet
 */
public class TaggerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaggerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String input = new String(request.getParameter("inputText").getBytes("iso-8859-1"), "utf-8");
		String output = tagText(input);
		request.setAttribute("output", output);
		System.out.println(output);
		request.getRequestDispatcher("/WEB-INF/tagOutput.jsp").forward(request, response);
	}

	private String tagText(String input) {
		
		return input.toUpperCase() + "::" + input.toLowerCase() ;
	}

}
