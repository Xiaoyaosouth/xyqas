<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>大版块管理 - 逍遥论坛</title>
    <link href="<%=path%>/static/css/bootstrap.min.css" rel="stylesheet">
    <script src="<%=path%>/static/js/jquery-3.2.1.js"></script>
    <script src="<%=path%>/static/js/bootstrap.min.js"></script>
    <style>
        li {list-style-type:none;}
        a{
            color: #8A8A8A;
            cursor: pointer;
        }
    </style>
</head>
<body>

<!-- 引入header文件 -->
<%@ include file="header.jsp"%>

<div class="panel panel-default"
     style="width: 50%;margin-left: 5%; margin-right: 5%; margin-bottom: 5%">
    <div class="panel-heading" style="background-color: #fff">
        <h3 class="panel-title">大版块管理</h3>
    </div>
    <div class="panel-body">
            <!-- 这里显示所有版块信息 -->
            <table class="table">
                <thead>
                <tr>
                    <th style="width: 100px">大版块ID</th>
                    <th style="width: 160px">大板块名</th>
                    <th style="width: 200px">操作</th>
                </tr>
                </thead>
                <tbody>
                <!-- 这里是表格内容，需要遍历数组 -->
                <c:forEach items="${forums}" var="forum">
                    <tr>
                        <td>${forum.forum_id}</td>
                        <td>${forum.forum_name}</td>
                        <td><!-- 这里放操作按钮 -->
                            <input type="button" class="btn btn-warning" value="修改"
                                   onclick="window.location.href='<%=basePath%>toModifyForumPage.do?forumId=${forum.forum_id}'"/>
                            <input type="button" class="btn btn-danger" value="删除（未实现）"
                                   onclick="window.location.href='<%=basePath%>disEnableForum.do?forumId=${forum.forum_id}'"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <input type="button" class="btn btn-default" value="返回"
                   style="margin-left: 20%" onclick="window.location.href='<%=basePath%>toMainPage.do'" />
    </div>
</div>

<!-- 引入footer文件 -->
<%@ include file="footer.jsp"%>

</body>
</html>