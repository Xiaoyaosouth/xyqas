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
    <title>逍遥论坛 › 修改贴子 › ${tip.tip_title}</title>
</head>
<body>
<!-- 引入header文件 -->
<%@ include file="header.jsp"%>

<div style="width: 70%;margin:1% 2% 1% 5%;float: left;">
    <div class="panel panel-default" id="main" style="">
        <div class="panel-heading" style="background-color: white">
            <a href="<%=basePath%>">逍遥论坛</a> › 修改贴子 ›
            <a href="showTip.do?tipId=${tip.tip_id}">${tip.tip_title}</a>
        </div>

        <div class="panel-body">
            <%--发贴人修改贴子，不同的action--%>
            <form  action="userModifyTip.do" id="myTipUpdateForm" method="POST" class="form-horizontal" role="form">
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
                            <td>贴子ID</td>
                            <td>${tip.tip_id}</td>
                            <%--贴子ID不允许用户修改--%>
                            <td><input class="form-control" type="hidden"
                                        name="tip_id" value="${tip.tip_id}" readonly/></td>
                        </tr>
                        <tr>
                            <td>版块</td>
                            <td>${tip.tab.forum.forum_name}</td>
                            <td>
                                <select class="form-control" id="selectForum" name="selectedForumId" onchange="selectForumFunc()">
                                    <c:forEach items="${forums}" var="forum">
                                        <c:choose>
                                            <c:when test="${tip.tab.forum.forum_id} == ${forum.forum_id}">
                                                <option value="${forum.forum_id}" selected>${forum.forum_name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${forum.forum_id}">${forum.forum_name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>分类</td>
                            <td>${tip.tab.tab_name}</td>
                            <td>
                                <select class="form-control" id="selectTab" name="selectedTabId">
                                    <c:forEach items="${tabs}" var="tab">
                                        <c:choose>
                                            <c:when test="${tip.tab.tab_id} == ${tab.tab_id}">
                                                <option value="${tab.tab_id}" selected>${tab.tab_name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${tab.tab_id}">${tab.tab_name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>发贴人</td>
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
                                          name="tip_content">${tip.tip_content}</textarea>
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
                                    <c:when test="${tip.tip_isDeleted == 1}">删除</c:when>
                                    <%--<c:otherwise>正常</c:otherwise>--%>
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
                                <input class="btn btn-warning" type="button" value="修改" onclick="userModifyTip_confirm()"/>
                                <input class="btn btn-default" type="reset" value="重填"/>
                                <%--这里的返回应回到贴子详情--%>
                                <input type="button" class="btn btn-default" value="返回"
                                       style="margin-left: 10%" onclick="window.location.href='<%=basePath%>showTip.do?tipId=${tip.tip_id}'" />
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
    /**
     * 修改贴子确认
     * @author rk 2020-02-18 22:14
     */
    function userModifyTip_confirm()
    {
        var r=confirm("确定修改?")
        if (r==true)
        {
            var form = document.getElementById("myTipUpdateForm"); // 由id获取表单
            form.submit();
        } else { }
    }

    function selectForumFunc() {
        // 获取选择的项目 jquery
        var selectedForum = $('select option:selected').val();
        // 获取分类下拉栏id
        var selectTab = document.getElementById("selectTab");
        // alert("您选择了：" + selectedForum);
        if (selectedForum != null){
            $.ajax(
                {
                    url:"<%=basePath%>getTabBySelectedForum.do",
                    type:"post",
                    data:{
                        selectedForum : selectedForum
                    },
                    dataType:"json",
                    success:function (data) {
                        var tabList = data;
                        if (tabList){
                            // 清除选项
                            selectTab.options.length = 0;
                            var optionStr = "";
                            // 先加一个无用的选项
                            optionStr += "<option value=\"\" selected>请选择分类</option>";
                            for (var i = 0; i < tabList.length; i++){
                                optionStr += "<option value=\"" + tabList[i].tab_id + "\">" +
                                    tabList[i].tab_name + "</option>";
                            }
                            //alert("将要添加的optionStr = " + optionStr);
                            // 添加到select标签（刷新选项）
                            $("select[id=selectTab]").append(optionStr);
                        }
                    }
                }
            )
        }else{
            alert("selectedForum == null");
            selectTab.options.length = 0;
        }
    }
</script>

</body>
</html>