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
    <title>用户管理 - 逍遥论坛</title>
    <link href="<%=path%>/static/css/bootstrap.min.css" rel="stylesheet">
    <script src="<%=path%>/static/js/jquery-3.2.1.js"></script>
    <script src="<%=path%>/static/js/bootstrap.min.js"></script>
    <style>
        li {list-style-type:none;}
        html, body {
            height: 100%;
            font-size: 14px;
            color: #525252;
            font-family: NotoSansHans-Regular,AvenirNext-Regular,arial,Hiragino Sans GB,"Microsoft Yahei","Hiragino Sans GB","WenQuanYi Micro Hei",sans-serif;
            background: #f0f2f5;
        }
        .footer {
            background-color: #fff;
            margin-top: 22px;
            margin-bottom: 22px;
            width: 100%;
            padding-top: 22px;
            color: #8A8A8A;
            display: block;
            height: 200px;
            border: 1px ;
            clear:both
        }

        .container {
            margin-right: 5%;
            margin-left: 5%;
            padding-left: 15px;
            padding-right: 15px;
            width: 40%;
            float: left;
        }
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
     style="width: 90%;margin-left: 5%; margin-right: 5%; margin-bottom: 5%">
    <div class="panel-heading" style="background-color: #fff">
        <h3 class="panel-title">用户管理</h3>
    </div>
    <div class="panel-body">

            <!-- 这里显示所有用户信息 -->
            <table class="table">
                <thead>
                <tr>
                    <th>用户ID</th>
                    <th>用户名</th>
                    <th>用户昵称</th>
                    <th>用户密码</th>
                    <th>用户权限</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <!-- 这里是表格内容，需要遍历数组 -->
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.user_id}</td>
                        <td>${user.user_name}</td>
                        <td>${user.user_nick}</td>
                        <td>${user.user_password}</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.user_type == 0}">超级管理员</c:when>
                                <c:when test="${user.user_type == 1}">管理员</c:when>
                                <c:otherwise>普通用户</c:otherwise>
                            </c:choose>
                        <td>
                            <c:choose>
                                <c:when test="${user.user_status == 1}">禁用</c:when>
                                <c:when test="${user.user_status == 2}">锁定</c:when>
                                <c:otherwise>正常</c:otherwise>
                            </c:choose>
                        </td>
                        <td><!-- 这里放操作按钮 -->
                            <c:choose>
                                <c:when test="${user.user_status == 1}">
                                <input type="button" class="btn btn-success" value="启用"
                                       onclick="window.location.href='<%=basePath%>enableUser.do?userId=${user.user_id}'" />
                                <input type="button" class="btn btn-warning" value="锁定"
                                       onclick="window.location.href='<%=basePath%>lockUser.do?userId=${user.user_id}'" />
                                </c:when>
                                <c:when test="${user.user_status == 2}">
                                    <input type="button" class="btn btn-success" value="解锁"
                                           onclick="window.location.href='<%=basePath%>enableUser.do?userId=${user.user_id}'" />
                                    <input type="button" class="btn btn-danger" value="禁用"
                                           onclick="window.location.href='<%=basePath%>disableUser.do?userId=${user.user_id}'" />
                                </c:when>
                                <c:otherwise>
                                    <!-- 处理其它正常的用户：管理员不能处理自己的状态 -->
                                    <c:if test="${user.user_id != USER.user_id}">
                                        <!-- 管理员不能处理其它管理员 -->
                                        <c:choose>
                                            <c:when test="${USER.user_type == 1}">
                                                <!-- 管理员可处理普通用户 -->
                                                <c:if test="${user.user_type == 2}">
                                                    <input type="button" class="btn btn-danger" value="禁用"
                                                           onclick="window.location.href='<%=basePath%>disableUser.do?userId=${user.user_id}'" />
                                                    <input type="button" class="btn btn-warning" value="锁定"
                                                           onclick="window.location.href='<%=basePath%>lockUser.do?userId=${user.user_id}'" />
                                                </c:if>
                                            </c:when>
                                            <c:when test="${USER.user_type == 0}">
                                                <!-- 超级管理员权限 -->
                                                <c:choose>
                                                    <c:when test="${user.user_status == 1}">
                                                        <input type="button" class="btn btn-success" value="启用"
                                                               onclick="window.location.href='<%=basePath%>enableUser.do?userId=${user.user_id}'" />
                                                        <input type="button" class="btn btn-warning" value="锁定"
                                                               onclick="window.location.href='<%=basePath%>lockUser.do?userId=${user.user_id}'" />
                                                    </c:when>
                                                    <c:when test="${user.user_status == 2}">
                                                        <input type="button" class="btn btn-success" value="解锁"
                                                               onclick="window.location.href='<%=basePath%>enableUser.do?userId=${user.user_id}'" />
                                                        <input type="button" class="btn btn-danger" value="禁用"
                                                               onclick="window.location.href='<%=basePath%>disableUser.do?userId=${user.user_id}'" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="button" class="btn btn-warning" value="锁定"
                                                               onclick="window.location.href='<%=basePath%>lockUser.do?userId=${user.user_id}'" />
                                                        <input type="button" class="btn btn-danger" value="禁用"
                                                               onclick="window.location.href='<%=basePath%>disableUser.do?userId=${user.user_id}'" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                        </c:choose>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
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