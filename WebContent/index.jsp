<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,javax.servlet.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#msg {
	width:100px;
	position:absolute;
	top:20px;
	right:10px;
	padding-top:35px;
	color:red;
	text-shadow:3px 3px 2px #545454;
}
td {width:230px;color:#fff;}
a {text-decoration:none}
a:hover{text-decoration:underline}
#downloadpart {
	width:500px;
	height:205px;
	background:lightblue;
	margin:auto;
	margin-bottom:10px;
	text-align:center;
	overflow:auto;
	border-radius:0 0 5px 5px;
}
#title {
	width:500px;
	height:30px;
	background:#898989;
	margin:auto;
	border-bottom:1px solid #888;
	border-radius:5px 5px 0 0;
}
.titleBoz{
	display:inline-block;
	width:232px;
	height:30px;
	line-height:30px;
	text-align:center;
	font-weight:1000;
}
#fresh {position:absolute;right:20px;bottom:50px;}
#listfiles{width:70px;height:120px;}
</style>
</head>
<body>
	<fieldset style="width:48%;height:260px;display:inline-block;vertical-align:top;">
	<legend>文件上传</legend>
		<div style="height:220px;width:400px;border:1px solid red;margin:auto;margin-top:10px;position:relative">
			<div style="text-align:center">
				<input style="margin-top:10px;" type="text" name="name" placeholder="上传者"/><br/>
 				<input style="margin-top:10px;margin-left:70px;" type="file" name="file1"/><br/>
 				<input style="margin-top:10px;margin-left:70px;" type="file" name="file2"/><br/>
 				<div id="msg"></div>
				<input style="margin-top:10px;" type="submit" value="上传"/>
			</div>
			<div style="background:lightgreen;text-align:center;margin-top:10px;padding:10px;color:#fff;">
				上传进度：<progress></progress>
				<p id="progress" style="margin:0;padding:0;margin-top:5px;">等待上传</p>
			</div>
		</div>
	</fieldset>
	
	<fieldset style="width:47%;height:260px;display:inline-block;">
	<legend>文件下载</legend>
		<div style="position:relative;">
			<div id="title">
				<div class="titleBoz">文件名</div>
				<div class="titleBoz">操作</div>
			</div>
			<div id="downloadpart">
				<table>
				</table>
			</div>
			<div id="fresh" style="text-align:center;">
				<button id="listfiles">刷新</button>
			</div>
		</div>
	</fieldset>
	
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="js/upload.js"></script>
<script type="text/javascript">
$(function(){
	$.ajax({
		url:"listFiles",
		type:"get",
		dataType:"json",
		success:function(data){
			var insertHTML = "";
			for(var i=0;i<data.length;i++) {
				insertHTML += "<tr><td>"+data[i].name+"</td><td><a href='${pageContext.request.contextPath}/downLoadServlet?filepath="+data[i].url+"'>下载</a></td></tr>";
			}
			$("table").html(insertHTML);
		}
	});
	
	$("#listfiles").click(function(){
		$.ajax({
			url:"listFiles",
			type:"get",
			dataType:"json",
			success:function(data){
				var insertHTML = "";
				for(var i=0;i<data.length;i++) {
					insertHTML += "<tr><td>"+data[i].name+"</td><td><a href='${pageContext.request.contextPath}/downLoadServlet?filepath="+data[i].url+"'>下载</a></td></tr>";
				}
				$("table").html(insertHTML);
			}
		});
	});
});
</script>
</body>
</html>