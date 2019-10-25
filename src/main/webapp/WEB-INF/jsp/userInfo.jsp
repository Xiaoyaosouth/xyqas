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
    <title>逍遥论坛 › 用户个人信息</title>
</head>
<body>

<!-- 弹出结果 -->
<c:if test="${not empty myInfo}">
    <script type="text/javascript" language="javascript">
        {
            alert("<%=request.getAttribute("myInfo")%>");
        }
    </script>
</c:if>

<!-- 引入header文件 -->
<%@ include file="header.jsp"%>

<div style="width: 70%;margin:1% 2% 1% 5%;float: left;">
    <div class="panel panel-default" id="main" style="">
        <div class="panel-heading" style="background-color: white">
            <a href="<%=basePath%>">逍遥论坛</a> › ${requestScope.userObject.user_nick}
        </div>

        <div class="panel-body">
            <c:if test="${!empty userObject}">
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户ID</label>
                    <div class="col-sm-10">
                        <p class="form-control-static">${userObject.user_id}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-10">
                        <p class="form-control-static">
                            ${userObject.user_name}
                            <!-- 只有自己才能修改个人信息，查看别人时不能修改他人的信息 -->
                            <c:if test="${userObject.user_id == USER.user_id}">&nbsp;&nbsp;
                                <a class="btn btn-warning" role="button"
                                   href="toUpdateUserInfoPage.do?userId=${userObject.user_id}" >修改密码</a>
                            </c:if>
                        </p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">昵称</label>
                    <div class="col-sm-10">
                        <p class="form-control-static">
                            ${userObject.user_nick}
                            <!-- 只有自己才能修改个人信息，查看别人时不能修改他人的信息 -->
                            <c:if test="${userObject.user_id == USER.user_id}">&nbsp;&nbsp;
                                <a class="btn btn-primary" role="button"
                                   href="toUpdateUserInfoPage.do?userId=${userObject.user_id}">修改昵称</a>
                            </c:if>
                        </p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">状态</label>
                    <div class="col-sm-10">
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
                    <div class="col-sm-10">
                        <p class="form-control-static">
                            <c:choose>
                                <c:when test="${userObject.user_type == 0}">超级管理员</c:when>
                                <c:when test="${userObject.user_type == 1}">管理员</c:when>
                                <c:otherwise>普通用户</c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </div>
            </form>
            </c:if>

        </div>
    </div>
</div>

<!-- 引入侧边栏文件 -->
<%@ include file="side.jsp"%>

<!-- 引入footer文件 -->
<%@ include file="footer.jsp"%>

</body>
</html>