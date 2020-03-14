<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--这个是头部导航栏--%>
<header>
    <nav class="navbar navbar-default" role="navigation" style="background-color: white">
        <div class="container-fluid" style="margin-left: 10%">
            <div class="navbar-header">
                <a class="navbar-brand" href="<%=basePath%>">逍遥论坛</a>
            </div>
            <div>

                <%--向左对齐--%>
                <ul class="nav navbar-nav navbar-left">
                    <li <c:if test="${tab.tab_name == '技术'}">class="active" </c:if>>
                        <a href="#">技术</a>
                    </li>
                </ul>

                <c:if test="${empty USER}">
                    <%--未登录--%>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <p class="navbar-text"><a href="toLoginPage.do">登录</a></p>
                        </li>
                        <li>
                            <p class="navbar-text"><a href="toSignUpPage.do">注册</a></p>
                        </li>
                    </ul>
                </c:if>
                <c:if test="${!empty USER}">
                    <%--已登录--%>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <p class="navbar-text"><a href="<%=basePath%>">首页</a></p>
                        </li>
                            <%--被锁定的用户不能发贴--%>
                        <c:if test="${USER.user_status != 2}">
                            <li><p class="navbar-text"><a href="toPublishTipPage.do">发表新贴</a></p></li>
                        </c:if>
                        <li>
                            <p class="navbar-text">
                                    <%--显示用户昵称--%>
                                <a href="getUserInfo.do?userId=${USER.user_id}">
                                    <c:choose>
                                        <c:when test="${empty USER.user_nick}">
                                            ${USER.user_name}
                                        </c:when>
                                        <c:otherwise>
                                            ${USER.user_nick}
                                        </c:otherwise>
                                    </c:choose>
                                        <%--展示用户权限 2020-03-14 23:22--%>
                                    <c:choose>
                                        <c:when test="${USER.user_type == 0}"> <span
                                                class="label label-success">超级管理员</span></c:when>
                                        <c:when test="${USER.user_type == 1}"> <span
                                                class="label label-warning">管理员</span></c:when>
                                        <c:otherwise><span class="label label-default">普通用户</span></c:otherwise>
                                    </c:choose>
                                </a>
                            </p>
                        </li>

                            <%--显示管理员功能下拉栏--%>
                        <c:if test="${USER.user_type == 0 || USER.user_type == 1}">
                            <li class="dropdown" open>
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"
                                   role="button" aria-haspopup="true" aria-expanded="true">
                                    管理员功能<span class="caret"/>
                                </a>
                                <ul class="dropdown-menu">
                                    <c:if test="${USER.user_type == 0}">
                                        <%--超级管理员可以注册新管理员--%>
                                        <li><a href="toSignUpPage.do">注册新的管理员</a></li>
                                        <li role="separator" class="divider"></li>
                                    </c:if>
                                    <li><a href="toUserManagePage.do">用户管理</a></li>
                                        <%--分割线separator--%>
                                    <li role="separator" class="divider"></li>
                                    <li><a href="toTipManagePage.do">贴子管理</a></li>
                                    <li role="separator" class="divider"></li>
                                    <li><a href="toForumManagePage.do">版块管理</a></li>
                                    <li role="separator" class="divider"></li>
                                    <li><a href="toTabManagePage.do">分类管理</a></li>
                                    <li role="separator" class="divider"></li>
                                </ul>
                            </li>
                        </c:if>

                        <li>
                            <p class="navbar-text"><a href="javascript:signOut_confirm();">退出登录</a></p>
                        </li>
                    </ul>
                </c:if>
            </div>
        </div>
    </nav>

</header>
<script>
    function signOut_confirm() {
        var r = confirm("确定退出?")
        if (r == true) {
            window.location.href = "<%=basePath%>userSignOut.do";
        } else {
        }
    }
</script>