<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
<%@ include file="header.jsp" %>

<div style="width: 70%;margin:1% 2% 1% 5%;float: left;">
    <div class="panel panel-default" id="main" style="">
        <div class="panel-heading" style="background-color: white">
            <a href="<%=basePath%>">逍遥论坛</a> › 用户信息 ›
            <c:choose>
                <c:when test="${empty userObject.user_nick}">${userObject.user_name}</c:when>
                <c:otherwise>${userObject.user_nick}</c:otherwise>
            </c:choose>
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
                            <p class="form-control-static">${userObject.user_name}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">昵称</label>
                        <div class="col-sm-10">
                            <p class="form-control-static">${userObject.user_nick}</p>
                        </div>
                    </div>
                        <%--2020-03-05 20:28 增加注册时间--%>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">注册时间</label>
                        <div class="col-sm-10">
                            <p class="form-control-static">
                                <fmt:formatDate value="${userObject.user_regTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </p>
                        </div>
                    </div>
                    <%--2020-03-05 20:30 增加最近登录时间--%>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">最近登录时间</label>
                        <div class="col-sm-10">
                            <p class="form-control-static">
                                <fmt:formatDate value="${userObject.user_lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-10">
                            <p class="form-control-static">
                                <c:choose>
                                    <c:when test="${userObject.user_status == 1}"> <span
                                            class="label label-danger">已禁用</span></c:when>
                                    <c:when test="${userObject.user_status == 2}"> <span
                                            class="label label-warning">已锁定</span></c:when>
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
                                    <c:when test="${userObject.user_type == 0}"> <span
                                            class="label label-success">超级管理员</span></c:when>
                                    <c:when test="${userObject.user_type == 1}"> <span
                                            class="label label-warning">管理员</span></c:when>
                                    <c:when test="${userObject.user_type == 2}"> <span
                                            class="label label-default">普通用户</span></c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                    </div>
                    <%-- 只有自己才能修改个人信息，查看别人时不能修改他人的信息 --%>
                    <c:if test="${userObject.user_id == USER.user_id}">&nbsp;&nbsp;
                        <a class="btn btn-warning" role="button"
                           href="toUpdateUserInfoPage.do?userId=${userObject.user_id}">修改密码</a>
                    </c:if>
                    <c:if test="${userObject.user_id == USER.user_id}">&nbsp;&nbsp;
                        <%-- 修改昵称单独一个网页 2020-09-25 15:40 --%>
                        <a class="btn btn-primary" role="button"
                           href="toModifyNickNamePage.do?userId=${userObject.user_id}">修改昵称</a>
                    </c:if>
                    <input type="button" class="btn btn-default" value="返回"
                           style="margin-left: 20%" onclick="window.location.href='<%=basePath%>toMainPage.do'"/>
                </form>
            </c:if>

        </div>
    </div>
</div>

<!-- 引入侧边栏文件 -->
<%@ include file="side.jsp" %>

<!-- 引入footer文件 -->
<%@ include file="footer.jsp" %>

</body>
</html>