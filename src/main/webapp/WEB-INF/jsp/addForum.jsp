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
    <title>添加版块 - 逍遥论坛</title>
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
        <h3 class="panel-title">添加版块</h3>
    </div>
    <div class="panel-body">
        <form action="addForum.do" method="POST" id="myAddForumForm" class="form-horizontal" role="form" style="margin-left: 5%">
            <div class="form-group" >
                <label class="col-sm-2 control-label">*版块名</label>
                <div class="col-sm-10" style="width: 40%;">
                    <input type="text" class="form-control" id="forum_name" name="forum_name" required />
                    <p class="form-control-static"></p>
                </div>
            </div>

            <input type="button" class="btn btn-primary" value="添加"
                   style="margin-left: 20%" onclick="addForum_confirm()" />
            &nbsp;&nbsp;<input type="button" class="btn btn-default" value="返回"
                   style="margin-left: 15%" onclick="window.location.href='toForumManagePage.do'" />
        </form>

    </div>
</div>

<!-- 引入footer文件 -->
<%@ include file="footer.jsp"%>

<script>
    function addForum_confirm()
    {
        var r=confirm("确定添加?")
        if (r==true)
        {
            var form = document.getElementById("myAddForumForm"); // 由id获取表单
            var forumName = form.forum_name.value; // 获取输入的版块名
            if (forumName == ''){
                alert("请填写版块名！")
            }else{
                form.submit(); // 提交表单
            }
        } else { }
    }
</script>
</body>
</html>