<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>逍遥论坛 ›修改昵称</title>
</head>
<body>
<%-- 引入header文件 --%>
<%@ include file="header.jsp" %>

<div style="width: 70%;margin:1% 2% 1% 5%;float: left;">
    <div class="panel panel-default" id="main" style="">
        <div class="panel-heading" style="background-color: white">
            <a href="<%=basePath%>">逍遥论坛</a>&nbsp;›&nbsp;
            <c:choose>
                <c:when test="${empty userObject.user_nick}">${userObject.user_name}</c:when>
                <c:otherwise>${userObject.user_nick}</c:otherwise>
            </c:choose>
            ›&nbsp;修改昵称

        </div>

        <div class="panel-body" style="margin: 1%">
            <%-- 表单 id与js关联 --%>
            <form action="modifyUserNickName.do" id="myUpdateForm" method="POST" class="form-horizontal" role="form"
                  style="margin:1% 50% 1% 1%">
                <%-- 保存用户id 隐藏 --%>
                <input type="hidden" name="user_id" value="${userObject.user_id}"/>
                <label>原昵称</label>
                <input type="text" class="form-control" id="oldNickName" name="oldNickName"
                       value="${userObject.user_nick}" readonly
                       style="margin-bottom: 5%"/>
                <label>新昵称</label>
                <input type="text" class="form-control" id="newNickName" name="user_nick" placeholder="请输入新昵称"
                       style="margin-bottom: 5%" onkeyup="checkUserNickName()"/>
                <span id="newNickNameErr" style="color:red"></span>
                <%-- 按钮 --%>
                <div>
                    <input class="btn btn-warning" type="button" value="修改" onclick="update_confirm()">
                    <input class="btn btn-default" type="reset" value="重填" style="margin-left: 5%">
                    <span>
                            <input type="button" class="btn btn-default" value="返回"
                                   style="float: right"
                                   onclick="window.location.href='<%=basePath%>getUserInfo.do?userId=${userObject.user_id}'"/>
                        </span>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- 引入侧边栏文件 --%>
<%@ include file="side.jsp" %>

<%-- 引入footer文件 --%>
<%@ include file="footer.jsp" %>

<script>
    // 2020-09-25
    function update_confirm() {
        if (checkUserNickName() == false) {
            alert("数据错误！请返回修改再试。")
        }
        else {
            var r = confirm("确定修改?");
            if (r == true) {
                var form = document.getElementById("myUpdateForm"); // 由id获取表单
                form.submit();
            }
        }
    }

    /**
     * 检查昵称
     * @returns {boolean}
     * 2020-09-25 17:16
     */
    function checkUserNickName() {
        var newNickName = document.getElementById('newNickName').value; // 获取输入的用户名
        var oldNickName = document.getElementById('oldNickName').value; // 原昵称
        // 比较
        if (newNickName == oldNickName) {
            document.getElementById('newNickNameErr').innerText = "新昵称不能与原昵称相同！";
            return false;
        } else {
            document.getElementById('newNickNameErr').innerText = "OK";
            return true;
        }
    }
</script>

</body>
</html>