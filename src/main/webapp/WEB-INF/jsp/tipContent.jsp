<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="<%=path%>/static/css/bootstrap.min.css" rel="stylesheet">
    <script src="<%=path%>/static/js/jquery-3.2.1.js"></script>
    <script src="<%=path%>/static/js/bootstrap.min.js"></script>
    <title>${tip.tip_title} - 逍遥论坛 </title>
</head>
<body>
<!-- 这是进入贴子查看内容的页面 -->
<!-- 引入header文件 -->
<%@ include file="header.jsp"%>

<div style="width: 70%;margin:1% 2% 1% 5%;float: left;">
<div class="panel panel-default" id="main" style="">
    <div class="panel-heading" style="background-color: white">
        <div>
            <div class="panel-heading" style="background-color: white">
                <a href="<%=basePath%>">逍遥论坛</a> › <a href="showTip.do?tipId=${tip.tip_id}">${tip.tip_title}</a>
            </div>
            <h3>${tip.tip_title}</h3><span class="label label-info" title="回复数">${tip.tip_replies}</span><br/>
            <div>
                <a href="getUserInfo.do?userId=${tip.user.user_id}">
                    <span>
                        <strong>
                            <c:choose>
                                <c:when test="${empty tip.user.user_nick}">
                                    ${tip.user.user_name}
                                </c:when>
                                <c:otherwise>
                                    ${tip.user.user_nick}
                                </c:otherwise>
                            </c:choose>
                        </strong>
                    </span>
                </a>
                <span>
                    <small class="text-muted">发表于：<fmt:formatDate value="${tip.tip_publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
                    <br>
                    <small class="text-muted">更新于：<fmt:formatDate value="${tip.tip_modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
                    <br>
                    <small class="text-muted">${tip.tip_click}次点击</small>
                </span>
            </div>
        </div>
    </div>
    <!-- 这里显示贴子正文 -->
    <ul class="list-group" style="width: 100%">
            <li class="list-group-item">
                <c:out value="${tip.tip_content}"></c:out>
            </li>
    </ul>
</div>

    <!-- 这里显示贴子的回复 -->
    <ul class="list-group" style="width: 100%">
        <!-- 遍历并显示回复 -->
        <c:forEach items="${replies}" var="reply">
        <li class="list-group-item">
            <div style="height: 50px">
                <div style="width: 89%;float: left">
                    <a href="getUserInfo.do?userId=${reply.user.user_id}">
                        <strong>
                            <!-- 显示发表回复的用户昵称 -->
                            <c:choose>
                                <c:when test="${empty reply.user.user_nick}">
                                    ${reply.user.user_name}
                                </c:when>
                                <c:otherwise>
                                    ${reply.user.user_nick}
                                </c:otherwise>
                            </c:choose>
                        </strong>
                    </a>&nbsp;&nbsp;
                    <small class="text-muted">
                        <!-- 显示回复发表的时间 -->
                        <fmt:formatDate value="${reply.reply_publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </small>
                    <br>
                    <!-- 这里显示回复的正文 -->
                    <div>
                        <p><c:out value="${reply.reply_content}"></c:out></p>
                    </div>
                </div>
            </div>
        </li>
        </c:forEach>
    </ul>

    <div class="panel panel-default" style="">
        <div class="panel-heading" style="background-color: white">回复本贴</div>
    <div class="panel-body">
        <div class="form-group">
            <!-- 非删除或结贴的贴子才能回复 -->
            <c:choose>
                <c:when test="${tip.tip_isKnot == 1}">
                    已结贴！不允许回复。
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${not empty USER}">
                            <c:choose>
                                <c:when test="${USER.user_status == 2}">
                                    <!-- 被锁定的用户不能回贴 -->
                                    您的账号已被锁定！请联系管理员解锁。
                                </c:when>
                                <c:otherwise>
                                    <!-- 这里是发表回复的表单 -->
                                    <form action="publishReply.do" method="post" id="myReplyForm">
                                        <input type="hidden" name="tip_id" value="${tip.tip_id}">
                                        <input type="hidden" name="user_id" value="${USER.user_id}">
                                        <!-- 这里显示输入回复内容的文本框 -->
                                        <textarea class="form-control" rows="3" name="reply_content" required ></textarea>
                                        <br/>
                                        <input type="button" class="btn btn-success btn-sm"
                                               value="发表" onclick="publishReply_confirm()" />
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <!-- 如果用户没有登录则取消文本框 -->
                            <input type="button" class="btn btn-warning btn-sm"
                                   value="请先登录" onclick="window.location.href='<%=basePath%>toLoginPage.do?tipId=${tip.tip_id}'"/>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    </div>
</div>
<!-- 引入侧边栏文件 -->
<%@ include file="side.jsp"%>

<!-- 引入footer文件 -->
<%@ include file="footer.jsp"%>

<script>
    function publishReply_confirm()
    {
        var form = document.getElementById("myReplyForm"); // 由id获取表单
        var replycontent = form.reply_content.value; // 获取输入的回复内容
        if(replycontent == '') {
            alert("请填写回复内容！");
        }else {
            var r = confirm("确定发表该回复?")
            if (r == true)
            {
                form.submit(); // 提交表单
            } else { }
        }
    }
</script>

</body>
</html>