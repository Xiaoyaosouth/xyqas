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
    <title>添加分类 - 逍遥论坛</title>
    <link href="<%=path%>/static/css/bootstrap.min.css" rel="stylesheet">
    <script src="<%=path%>/static/js/jquery-3.2.1.js"></script>
    <script src="<%=path%>/static/js/bootstrap.min.js"></script>
    <style>
        li {list-style-type:none;}
        a{
            color: #8A8A8A;
            cursor: pointer;
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

<div class="panel panel-default" id="login" style="width: 55%;margin-left: 10%;margin-top: 5%;margin-bottom: 5%">
    <div class="panel-heading" style="background-color: #fff">
        <h3 class="panel-title">添加分类</h3>
    </div>
    <div class="panel-body">
        <form action="addTab.do" method="POST" id="myAddTabForm" class="form-horizontal" role="form" style="margin-left: 5%">
            <div class="form-group" >
                <label class="col-sm-2 control-label">*分类名</label>
                <div class="col-sm-10" style="width: 40%;">
                    <input type="text" class="form-control" id="tab_name" name="tab_name" required />
                    <p class="form-control-static"></p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">*所属版块：</label>
                <div class="col-sm-10" style="width: 20%">
                    <select class="form-control" id="selectForum" name="selectForum">
                        <option value="" selected>请选择版块</option>
                        <c:choose>
                            <c:when test="${not empty forums}">
                                <c:forEach items="${forums}" var="forum">
                                    <option value="${forum.forum_id}">${forum.forum_name}</option>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                添加版块
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>
            </div>

            <input type="button" class="btn btn-primary" value="添加"
                   style="margin-left: 20%" onclick="addTab_confirm()" />
            &nbsp;&nbsp;<input type="button" class="btn btn-default" value="返回"
                   style="margin-left: 15%" onclick="window.location.href='toTabManagePage.do'" />
        </form>

    </div>
</div>

<!-- 引入footer文件 -->
<%@ include file="footer.jsp"%>

<script>
    function addTab_confirm()
    {
        var r=confirm("确定添加?")
        if (r==true)
        {
            var form = document.getElementById("myAddTabForm"); // 由id获取表单
            var tabName = form.tab_name.value; // 获取输入的分类名
            var selectForum = form.selectForum.value; // 获取所属版块
            if (tabName == '' && selectForum == ''){
                alert("请填写分类名并选择所属版块！")
            }else{
                if(tabName == '') {
                    alert("请输入分类名！");
                } else{
                    if (selectForum == ''){
                        alert("请选择所属版块！");
                    }else {
                        form.submit(); // 提交表单
                    }
                }
            }
        } else { }
    }
</script>
</body>
</html>