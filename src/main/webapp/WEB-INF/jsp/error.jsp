<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>错误页面</title>
	<meta http-equiv="Content-Type" tip_content="text/html; charset=UTF-8">
  </head>
  <body>
  <h3>服务器出错啦~</h3>
  <h4>错误信息：${msg }</h4>
  <br><br>
  <a href="<%=basePath%>index.jsp">返回</a>
  </body>
</html>
