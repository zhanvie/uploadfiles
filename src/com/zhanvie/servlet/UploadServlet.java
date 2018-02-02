package com.zhanvie.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet(name = "uploadServlet", urlPatterns = { "/uploadServlet" })
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
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
/*********************************************************
* 首先上传要有路径
**********************************************************/
		//创建文件上传路径
		//String savePath = request.getSession().getServletContext().getRealPath("/upload");
		String savePath = "E:/downloadtest";
		String tempPath = savePath;
		File saveDir = new File(savePath);
		File tempDir = new File(tempPath);
		//创建文件夹以便上传
		if(!saveDir.exists() || !saveDir.isDirectory()){
			saveDir.mkdir();
		}
		if(!tempDir.exists() || !tempDir.isDirectory()){
			tempDir.mkdir();
		}
		
/*********************************************************
 * 然后要创建文件工厂和上传类，并设置编码集
 *********************************************************/
		//提示消息
		String message = "";
		//文件上传工厂
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		fileFactory.setSizeThreshold(1024*1024*10);
		fileFactory.setRepository(tempDir);
		//文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(fileFactory);
		//设置编码，防止文件名是中文时产生乱码
		upload.setHeaderEncoding("utf-8");
		
		
/***************************************************
 * 判断提交的request是否为文件 ，是的话接收文件列表并遍历
 ***************************************************/
		if(!ServletFileUpload.isMultipartContent(request)){
			return ;
		}
		try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				//不是文件
				if(item.isFormField()){
					String content = item.getFieldName();
					String value = item.getString("utf-8");
//System.out.println(content+":"+value);
				}
				//是文件
				else{
					//获取文件名，部分浏览器是全路径，需要转换
					String fileName = item.getName();
					if(fileName.equals("")||fileName==null){
						continue;
					}
					//获取文件名
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					
/******************************************************
 * 创建文件处理流，读写文件至服务器
 ******************************************************/
					InputStream is = item.getInputStream();
					FileOutputStream fos = new FileOutputStream(saveDir+"\\"+fileName);
//System.out.println(saveDir+"\\"+fileName);
					byte buf[] = new byte[1024*1024];
					int len = 0;
					while((len=is.read(buf))!=-1){
						fos.write(buf, 0, len);
					}
					is.close();
					fos.close();
					item.delete();
					message = "文件上传成功！\n";
				}
			}
		} catch (FileUploadException e) {
			message = "文件上传失败！\n";
			e.printStackTrace();
		}
		response.getWriter().write(message);
	}

}
