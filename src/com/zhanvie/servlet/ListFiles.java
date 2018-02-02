package com.zhanvie.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ListFiles
 */
@WebServlet(name = "listFiles", urlPatterns = { "/listFiles" })
public class ListFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String loadPath="";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListFiles() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8");
		loadPath = "E:/downloadtest/";
		File file = new File(loadPath);
		Map<String,String> filePathMap = new HashMap<String,String>();
		this.listFiles(file, 0,filePathMap);
		JSONArray jsonArr = new JSONArray();
		Set<Entry<String, String>> entrySet = filePathMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", entry.getKey());
			jsonObj.put("url", entry.getValue());
			jsonArr.add(jsonObj);
		}
		response.getWriter().write(jsonArr.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}
	
	private void listFiles(File file,int lev,Map<String,String> filePathMap) {
		if(!file.isFile()) {
//			System.out.println(file.getName());
			File files[] = file.listFiles();
			lev++;
			for(int i=0;i<files.length;i++) {
				if(files[i].isDirectory()){
					listFiles(files[i],lev,filePathMap);
				}else{
//					for(int j=0;j<lev;j++) {
//						System.out.print("  ");
//					}
//					System.out.println("¡¤"+files[i].getName());
					filePathMap.put(files[i].getName(), loadPath+"/"+files[i].getName());
				}
			}
		}
	}

}
