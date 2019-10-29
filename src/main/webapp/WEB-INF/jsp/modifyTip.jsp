<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>逍遥论坛 ›修改贴子信息</title>
</head>
<body>
<!-- 引入header文件 -->
<%@ include file="header.jsp"%>

<div style="width: 70%;margin:1% 2% 1% 5%;float: left;">
    <div class="panel panel-default" id="main" style="">
        <div class="panel-heading" style="background-color: white">
            <a href="<%=basePath%>">逍遥论坛</a> › 修改贴子信息 ›
            <a href="showTip.do?tipId=${tip.tip_id}">${tip.tip_title}</a>
        </div>

        <div class="panel-body">
            <form  action="modifyTip.do" id="myTipUpdateForm" method="POST" class="form-horizontal" role="form">
                <table class="table">
                    <thead>
                    <tr>
                        <th>名称</th>
                        <th>修改前</th>
                        <th>修改后</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>ID</td>
                            <td>${tip.tip_id}</td>
                            <td><input class="form-control" type="hidden"
                                        name="tip_id" value="${tip.tip_id}"/></td>
                        </tr>
                        <tr>
                            <td>版块</td>
                            <td>${tip.tab.forum.forum_name}</td>
                            <td>

                            </td>
                        </tr>
                        <tr>
                            <td>分类</td>
                            <td>${tip.tab.tab_name}</td>
                            <td>

                            </td>
                        </tr>
                        <tr>
                            <td>楼主</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty tip.user.user_nick}">${tip.user.user_nick}</c:when>
                                    <c:otherwise>${tip.user.user_name}</c:otherwise>
                                </c:choose>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>标题</td>
                            <td>${tip.tip_title}</td>
                            <td><input class="form-control" type="text"
                                       name="tip_title" value="${tip.tip_title}" required/></td>
                        </tr>
                        <tr>
                            <td>内容</td>
                            <td>${tip.tip_content}</td>
                            <td>
                                <textarea class="form-control" rows="2"
                                          name="tip_content" required >${tip.tip_content}</textarea>
                            </td>
                        </tr>
                        <tr>
                            <td>回复数</td>
                            <td>${tip.tip_replies}</td>
                        </tr>
                        <tr>
                            <td>发表时间<br>更新时间</td>
                            <td>
                                <fmt:formatDate value="${tip.tip_publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                <br>
                                <fmt:formatDate value="${tip.tip_modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        </tr>
                        <tr>
                            <td>点击量</td>
                            <td>${tip.tip_click}</td>
                        </tr>
                        <tr>
                            <td>状态</td>
                            <td>
                                <c:choose>
                                    <c:when test="${tip.tip_status == 1}">删除</c:when>
                                    <c:otherwise>正常</c:otherwise>
                                </c:choose>
                                <br>
                                <c:choose>
                                    <c:when test="${tip.tip_isKnot == 1}">结贴</c:when>
                                    <c:otherwise>未结贴</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td>操作</td>
                            <td>
                                <input class="btn btn-warning" type="button" value="修改" onclick="update_confirm()"/>
                                <input class="btn btn-default" type="reset" value="重填"/>
                                <input type="button" class="btn btn-default" value="返回"
                                       style="margin-left: 10%" onclick="window.location.href='<%=basePath%>toTipManagePage.do'" />
                            </td>
                        </tr>
                    </tbody>
                </table>
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
            var form = document.getElementById("myTipUpdateForm"); // 由id获取表单
            form.submit();
        } else { }
    }
</script>

</body>
</html>