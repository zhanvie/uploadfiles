package com.zhanvie.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownLoadServlet
 */
@WebServlet(name = "downLoadServlet", urlPatterns = { "/downLoadServlet" })
public class DownLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownLoadServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8;");
		String filePath = request.getParameter("filepath");
		String realname = filePath.substring(filePath.lastIndexOf("/")+1);
		File file = new File(filePath);
		if(!file.exists()) {
			response.getWriter().write("<script>alert('您要下载的资源已被删除！');window.location.href='index.html';</script>");
			return;
		}
		response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(realname, "UTF-8"));
		FileInputStream fis = new FileInputStream(file);
		OutputStream os = response.getOutputStream();
		byte[] buf = new byte[1024*1024];
		int len = 0;
		while((len=fis.read(buf))!=-1) {
			os.write(buf, 0, len);
		}
		fis.close();
		os.close();
	}

}
