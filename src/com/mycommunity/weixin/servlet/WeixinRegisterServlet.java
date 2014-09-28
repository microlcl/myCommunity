package com.mycommunity.weixin.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WeixinRegisterServlet
 */
public class WeixinRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WeixinRegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String echostr = request.getParameter("echostr");
		if (echostr == null || echostr == "") {
			echostr = "not from weixin";
		}
		response.setContentType("text/plain");
		response.getWriter().append(echostr);
		response.getWriter().close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = this.getMessage(request);
		this.dispatch(message, request, response);
	}

	private String getMessage(HttpServletRequest request) throws ServletException, IOException {
		InputStream is = request.getInputStream();
		int size = request.getContentLength();
		byte[] buffer = new byte[size];
		byte[] xmldataByte = new byte[size];
		int count = 0;
		int rbyte = 0;
		while (count < size) {
			rbyte = is.read(buffer);
			for (int i = 0; i < rbyte; i++) {
				xmldataByte[count + i] = buffer[i];
			}
			count += rbyte;
		}
		is.close();
		String requestStr = new String(xmldataByte, "UTF-8");
		return requestStr;
	}

	private void dispatch(String requestStr, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("from request:" + requestStr);
		String responseStr = "hello, world";

		System.out.println("responseStr:" + responseStr);
		OutputStream os = response.getOutputStream();
		os.write(responseStr.getBytes("UTF-8"));

	}

}
