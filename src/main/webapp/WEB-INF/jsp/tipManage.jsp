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

<%--引入header文件--%>
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
                    <th>发表时间、更新时间</th>
                    <th>点击数</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <%--这里是表格内容，需要遍历数组--%>
                <c:forEach items="${tips}" var="tip">
                    <tr>
                        <td>${tip.tip_id}</td>
                        <td>${tip.tab.tab_name}</td>
                        <%--标题--%>
                        <td style="overflow: hidden; text-overflow: ellipsis; max-width: 120px; white-space: nowrap">${tip.tip_title}</td>
                        <%--内容--%>
                        <td style="overflow: hidden; text-overflow: ellipsis; max-width: 120px; white-space: nowrap">${tip.tip_content}</td>
                        <%--发贴用户昵称或用户名--%>
                        <td>
                            <c:choose>
                                <c:when test="${not empty tip.user.user_nick}">${tip.user.user_nick}</c:when>
                                <c:otherwise>${tip.user.user_name}</c:otherwise>
                            </c:choose>
                        </td>
                        <%--回复数--%>
                        <td>${tip.tip_replies}</td>
                        <%--发贴时间 修改时间--%>
                        <td>
                            <fmt:formatDate value="${tip.tip_publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            <br>
                            <fmt:formatDate value="${tip.tip_modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <%--点击数--%>
                        <td>${tip.tip_click}</td>
                        <%--状态--%>
                        <td>
                            <%--2020-03-05 10:44--%>
                            <c:if test="${tip.tip_isDeleted == 1}"><span class="label label-danger">已删除</span></c:if>
                            <c:if test="${tip.tip_isKnot == 1}"><span class="label label-success">已结贴</span></c:if>
                            <c:if test="${tip.tip_isTop == 1}"><span class="label label-warning">已置顶</span></c:if>
                            <%--<c:choose>--%>
                                <%--<c:when test="${tip.tip_isKnot == 1}">结贴</c:when>--%>
                                <%--<c:otherwise>未结贴</c:otherwise>--%>
                            <%--</c:choose>--%>
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
                                    <input type="button" class="btn btn-success" value="取消结贴"
                                           onclick="window.location.href='<%=basePath%>ChangeTipStatus.do?tipId=${tip.tip_id}&opr=4'"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="button" class="btn btn-primary" value="结贴"
                                           onclick="window.location.href='<%=basePath%>ChangeTipStatus.do?tipId=${tip.tip_id}&opr=3'"/>
                                </c:otherwise>
                            </c:choose>
                            <%--置顶 2020-02-27 10:00--%>
                            <c:choose>
                                <c:when test="${tip.tip_isTop == 1}">
                                    <input type="button" class="btn btn-success" value="取消置顶"
                                           onclick="window.location.href='<%=basePath%>ChangeTipStatus.do?tipId=${tip.tip_id}&opr=6'"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="button" class="btn btn-warning" value="置顶"
                                           onclick="window.location.href='<%=basePath%>ChangeTipStatus.do?tipId=${tip.tip_id}&opr=5'"/>
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