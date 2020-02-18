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
    <title>贴子管理 - 逍遥论坛</title>
    <link href="<%=path%>/static/css/bootstrap.min.css" rel="stylesheet">
    <script src="<%=path%>/static/js/jquery-3.2.1.js"></script>
    <script src="<%=path%>/static/js/bootstrap.min.js"></script>
    <style>
        li {list-style-type:none;}

        a{
            color: #8A8A8A;
            cursor: pointer;
        }

        th {
            text-align:center; /*设置水平居中*/
            /* vertical-align:middle; */ /*设置垂直居中*/
        }
        td {
            text-align:center; /*设置水平居中*/
            /* vertical-align:middle; */ /*设置垂直居中*/
            border: 1px solid gray;
        }
    </style>
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

<div class="panel panel-default"
     style="width: 90%;margin-left: 5%; margin-right: 5%; margin-bottom: 5%">
    <div class="panel-heading" style="background-color: #fff">
        <h3 class="panel-title">贴子管理</h3>
    </div>
    <div class="panel-body">

            <!-- 这里显示所有贴子信息 -->
            <table class="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>所属分类</th>
                    <th>标题</th>
                    <th>内容</th>
                    <th>发表人</th>
                    <th>回复数</th>
                    <th>发表时间<br>更新时间</th>
                    <th>点击数</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <!-- 这里是表格内容，需要遍历数组 -->
                <c:forEach items="${tips}" var="tip">
                    <tr>
                        <td>${tip.tip_id}</td>
                        <td>${tip.tab.tab_name}</td>
                        <!-- 标题 -->
                        <td style="overflow: hidden; text-overflow: ellipsis; max-width: 120px; white-space: nowrap">${tip.tip_title}</td>
                        <!-- 内容 -->
                        <td style="overflow: hidden; text-overflow: ellipsis; max-width: 120px; white-space: nowrap">${tip.tip_content}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty tip.user.user_nick}">${tip.user.user_nick}</c:when>
                                <c:otherwise>${tip.user.user_name}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${tip.tip_replies}</td>
                        <td>
                            <fmt:formatDate value="${tip.tip_publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            <br>
                            <fmt:formatDate value="${tip.tip_modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>${tip.tip_click}</td>
                        <td>
                            <c:choose>
                                <c:when test="${tip.tip_isDeleted == 1}">删除</c:when>
                                <c:otherwise>正常</c:otherwise>
                            </c:choose>
                            <br>
                            <c:choose>
                                <c:when test="${tip.tip_isKnot == 1}">结贴</c:when>
                                <c:otherwise>未结贴</c:otherwise>
                            </c:choose>
                        </td>
                        <td><!-- 这里显示操作按钮 -->
                            <input type="button" class="btn btn-warning" value="修改"
                                   onclick="window.location.href='<%=basePath%>toModifyTipPage.do?tipId=${tip.tip_id}'"/>
                            <c:choose>
                                <c:when test="${tip.tip_isDeleted == 1}">
                                    <input type="button" class="btn btn-success" value="取消删除"
                                           onclick="window.location.href='<%=basePath%>ChangeTipStatus.do?tipId=${tip.tip_id}&opr=2'"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="button" class="btn btn-danger" value="删除"
                                           onclick="window.location.href='<%=basePath%>ChangeTipStatus.do?tipId=${tip.tip_id}&opr=1'"/>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${tip.tip_isKnot == 1}">
                                    <input type="button" class="btn btn-warning" value="取消结贴"
                                           onclick="window.location.href='<%=basePath%>ChangeTipStatus.do?tipId=${tip.tip_id}&opr=4'"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="button" class="btn btn-primary" value="结贴"
                                           onclick="window.location.href='<%=basePath%>ChangeTipStatus.do?tipId=${tip.tip_id}&opr=3'"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <input type="button" class="btn btn-default" value="返回"
                   style="margin-left: 17%" onclick="window.location.href='<%=basePath%>toMainPage.do'" />

    </div>
</div>

<!-- 引入footer文件 -->
<%@ include file="footer.jsp"%>

</body>
</html>