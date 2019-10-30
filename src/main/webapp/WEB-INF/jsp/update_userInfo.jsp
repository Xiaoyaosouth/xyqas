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
    <link href="<%=path%>/static/css/bootstrap.min.css" rel="stylesheet">
    <script src="<%=path%>/static/js/jquery-3.2.1.js"></script>
    <script src="<%=path%>/static/js/bootstrap.min.js"></script>
    <title>逍遥论坛 ›修改用户信息</title>
</head>
<body>
<!-- 引入header文件 -->
<%@ include file="header.jsp"%>

<div style="width: 70%;margin:1% 2% 1% 5%;float: left;">
    <div class="panel panel-default" id="main" style="">
        <div class="panel-heading" style="background-color: white">
            <a href="<%=basePath%>">逍遥论坛</a> › 修改用户信息 ›
            <c:choose>
                <c:when test="${empty userObject.user_nick}">${userObject.user_name}</c:when>
                <c:otherwise>${userObject.user_nick}</c:otherwise>
            </c:choose>
        </div>

        <div class="panel-body">

            <form  action="updateUserInfo.do" id="myUpdateForm" method="POST" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="col-sm-2 control-label">ID</label>
                    <div class="col-sm-5">
                        <p class="form-control-static">${userObject.user_id}</p>
                    </div>
                    <div class="col-sm-5">
                        <input type="text"  name="user_id" value="${userObject.user_id}" readonly />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-5">
                        <p class="form-control-static">${userObject.user_name}</p>
                    </div>
                    <div class="col-sm-5">
                        <input type="text"  name="user_name" value="${userObject.user_name}" readonly />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-5">
                        <p class="form-control-static"></p>
                    </div>
                    <div class="col-sm-5">
                        如需修改请输入新密码：<br><input type="password"  name="user_password" value="${userObject.user_password}" required />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">昵称</label>
                    <div class="col-sm-5">
                        <p class="form-control-static">${userObject.user_nick}</p>
                    </div>
                    <div class="col-sm-5">
                        新昵称：<br><input type="text"  name="user_nick" value="${userObject.user_nick}" required />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户状态</label>
                    <div class="col-sm-5">
                        <p class="form-control-static">
                            <c:choose>
                                <c:when test="${userObject.user_status == 1}">禁用</c:when>
                                <c:when test="${userObject.user_status == 2}">锁定</c:when>
                                <c:otherwise>正常</c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户权限</label>
                    <div class="col-sm-5">
                        <p class="form-control-static">
                            <c:choose>
                                <c:when test="${userObject.user_type == 0}">超级管理员</c:when>
                                <c:when test="${userObject.user_type == 1}">管理员</c:when>
                                <c:when test="${userObject.user_type == 2}">普通用户</c:when>
                                <c:otherwise></c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </div>

                <input class="btn btn-warning" type="button" value="修改" onclick="update_confirm()">
                <input class="btn btn-default" type="reset" value="重填">
                <input type="button" class="btn btn-default" value="返回"
                       style="margin-left: 10%" onclick="window.location.href='<%=basePath%>getUserInfo.do?userId=${userObject.user_id}'" />
            </form>
        </div>
    </div>
</div>

<!-- 引入侧边栏文件 -->
<%@ include file="side.jsp"%>

<!-- 引入footer文件 -->
<%@ include file="footer.jsp"%>

<script>
    function update_confirm()
    {
        var r=confirm("确定修改?")
        if (r==true)
        {
            var form = document.getElementById("myUpdateForm"); // 由id获取表单
            form.submit();
        } else { }
    }
</script>

</body>
</html>