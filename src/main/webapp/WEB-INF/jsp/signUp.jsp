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
    <title>用户注册</title>
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
        .info {
            margin-right: 5%;
            width: 10%;
            float: left;
        }
        a{
            color: #8A8A8A;
            cursor: pointer;
        }
    </style>
</head>
<body>

<!-- 执行注册后弹出结果 -->
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
        <h3 class="panel-title">注册</h3>
    </div>
    <div class="panel-body">
        <form action="userSignUp.do" method="POST" id="mySignUpForm" class="form-horizontal" role="form" style="margin-left: 5%">
            <div class="form-group" >
                <label class="col-sm-2 control-label">*用户名</label>
                <div class="col-sm-10" style="width: 40%;">
                    <input type="text" class="form-control" id="user_name" name="user_name" required />
                    <p class="form-control-static">用于登录。请使用半角的 a-z A-Z 或数字 0-9</p>
                </div>
            </div>
            <div class="form-group" >
                <label class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-10" style="width: 40%;">
                    <input type="text" class="form-control" id="user_nick" name="user_nick" />
                    <p class="form-control-static">可不填</p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">*密码</label>
                <div class="col-sm-10" style="width: 40%;">
                    <input type="password" class="form-control" id="user_password" name="user_password" required />
                    <p class="form-control-static">目前暂无要求</p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">注册用户类型：</label>
                <div class="col-sm-10" style="width: 20%">
                    <select class="form-control" id="user_type" name="user_type">
                        <c:choose>
                            <c:when test="${not empty USER}">
                                <c:if test="${USER.user_type == 0}">
                                    <!-- 超级管理员可以注册管理员 -->
                                    <option value="1" selected>管理员</option>
                                    <option value="2">普通用户</option>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <!-- 超级管理员没有登录 -->
                                <option value="2" selected>普通用户</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>
            </div>

            <input type="button" class="btn btn-primary" value="注册"
                   style="margin-left: 20%" onclick="signUp_confirm()" />
            &nbsp;&nbsp;<input type="button" class="btn btn-success" value="登录"
                   style="margin-left: 15%" onclick="window.location.href='toLoginPage.do'" />
        </form>

    </div>
</div>

<!-- 引入footer文件 -->
<%@ include file="footer.jsp"%>

<script>
    function signUp_confirm()
    {
        var r=confirm("确定注册?")
        if (r==true)
        {
            var form = document.getElementById("mySignUpForm"); // 由id获取表单
            var uname = form.user_name.value; // 获取输入的用户名
            var upwd = form.user_password.value; // 获取输入的密码
            if(uname == '' || upwd == '') {
                alert("请将注册信息填写完整！");
            }else{
                form.submit(); // 提交表单
            }
        }
        else
        { }
    }
</script>
</body>
</html>