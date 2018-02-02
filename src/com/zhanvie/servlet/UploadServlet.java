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
* �����ϴ�Ҫ��·��
**********************************************************/
		//�����ļ��ϴ�·��
		//String savePath = request.getSession().getServletContext().getRealPath("/upload");
		String savePath = "E:/downloadtest";
		String tempPath = savePath;
		File saveDir = new File(savePath);
		File tempDir = new File(tempPath);
		//�����ļ����Ա��ϴ�
		if(!saveDir.exists() || !saveDir.isDirectory()){
			saveDir.mkdir();
		}
		if(!tempDir.exists() || !tempDir.isDirectory()){
			tempDir.mkdir();
		}
		
/*********************************************************
 * Ȼ��Ҫ�����ļ��������ϴ��࣬�����ñ��뼯
 *********************************************************/
		//��ʾ��Ϣ
		String message = "";
		//�ļ��ϴ�����
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		fileFactory.setSizeThreshold(1024*1024*10);
		fileFactory.setRepository(tempDir);
		//�ļ��ϴ�������
		ServletFileUpload upload = new ServletFileUpload(fileFactory);
		//���ñ��룬��ֹ�ļ���������ʱ��������
		upload.setHeaderEncoding("utf-8");
		
		
/***************************************************
 * �ж��ύ��request�Ƿ�Ϊ�ļ� ���ǵĻ������ļ��б�����
 ***************************************************/
		if(!ServletFileUpload.isMultipartContent(request)){
			return ;
		}
		try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				//�����ļ�
				if(item.isFormField()){
					String content = item.getFieldName();
					String value = item.getString("utf-8");
//System.out.println(content+":"+value);
				}
				//���ļ�
				else{
					//��ȡ�ļ����������������ȫ·������Ҫת��
					String fileName = item.getName();
					if(fileName.equals("")||fileName==null){
						continue;
					}
					//��ȡ�ļ���
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					
/******************************************************
 * �����ļ�����������д�ļ���������
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
					message = "�ļ��ϴ��ɹ���\n";
				}
			}
		} catch (FileUploadException e) {
			message = "�ļ��ϴ�ʧ�ܣ�\n";
			e.printStackTrace();
		}
		response.getWriter().write(message);
	}

}
