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
    <link href="<%=path%>/static/css/bootstrap.min.css" rel="stylesheet">
    <script src="<%=path%>/static/js/jquery-3.2.1.js"></script>
    <script src="<%=path%>/static/js/bootstrap.min.js"></script>
    <title>逍遥论坛 › 发表新贴 </title>
</head>
<body>

<!-- 发贴后弹出结果 -->
<c:if test="${not empty myInfo}">
    <script type="text/javascript" language="javascript">
        {
            var str = "<%=request.getAttribute("myInfo")%>";
            if (str == 'error'){
                alert("发布失败！请重试！")
            }else if (str == 'success'){
                var r = confirm("发布成功！是否跳转到主页？");
                if (r == true){
                    window.location.href = "<%=basePath%>";
                }else {
                    window.location.href = "<%=basePath%>toPublishTipPage.do";
                }
            }
        }
    </script>
</c:if>

<!-- 引入header文件 -->
<%@ include file="header.jsp"%>

    <div style="width: 70%;margin:1% 2% 1% 5%;float: left;">
    <div class="panel panel-default" id="main" style="">
        <div class="panel-heading" style="background-color: white">
            <a href="<%=basePath%>">逍遥论坛</a> › 发表新贴
        </div>

        <div class="panel-body">
            <form action="publishNewTip.do" method="POST" id="myNewTipForm">
                <div class="form-group">
                    <label for="tip_title">标题</label>
                    <input type="text" class="form-control" id="tip_title" name="tip_title" placeholder="请输入标题" required />
                </div>

                <div class="form-group">
                    <label for="tip_content">正文</label>
                    <textarea class="form-control" rows="10" id="tip_content" name="tip_content" ></textarea>
                </div>

                <div class="form-group">
                    <div class="col-sm-6" style="width: 20%">
                        所属版块：<select class="form-control" id="selectForum" name="selectedForumId" onchange="selectForumFunc()">
                        <option value="" selected>请选择版块</option>
                            <c:forEach items="${forums}" var="forum">
                            <option value="${forum.forum_id}">${forum.forum_name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-6" style="width: 20%">
                        分类：<select class="form-control" id="selectTab" name="tab_id">
                        <option value="" selected>请选择分类</option>
                            <c:forEach items="${tabs}" var="tab">
                                <option value="${tab.tab_id}">${tab.tab_name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <br><br><br><br>

                <input type="button"  value="发布" onclick="publishTip_confirm()" class="btn btn-success btn-sm" />
                <input type="button" class="btn btn-default" value="返回"
                                   style="margin-left: 15%" onclick="window.location.href='toMainPage.do'" />

            </form>
        </div>
    </div>
</div>


    <div class="panel panel-default" id="sidebar2" style="width: 20%;margin:1% 2% 1% 0%;float: right">
        <div class="panel-heading" style="background-color: white;text-align: center">
            tips1
        </div>
        <ul class="list-group" style="width: 100%">
            <li class="list-group-item">
                <h5>标题</h5>
                <p>
                    （待补充）
                </p>
            </li>

            <li class="list-group-item">
                <h5>正文</h5>
                <p>
                    （待补充）
                </p>
            </li>
        </ul>
    </div>


    <div class="panel panel-default" id="sidebar1" style="width: 20%;margin:1% 2% 1% 0%;float: right">
    <div class="panel-heading" style="background-color: white;text-align: center">
        tips2
    </div>
    <ul class="list-group" style="width: 100%">
        <li class="list-group-item">
            <h5>待补充</h5>
            <p>
                （待补充）
            </p>
        </li>

        <li class="list-group-item">
            <h5>待补充</h5>
            <p>
                （待补充）
            </p>
        </li>
    </ul>
</div>


<!-- 引入footer文件 -->
<%@ include file="footer.jsp"%>

<script>
    function publishTip_confirm()
    {
        var r=confirm("确定发表该贴?")
        if (r==true)
        {
            var form = document.getElementById("myNewTipForm"); // 由id获取表单
            var tiptitle = form.tip_title.value; // 获取输入的标题
            if(tiptitle == '') {
                alert("请填写贴子标题！");
            }else{
                form.submit(); // 提交表单
            }
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
                            alert("将要添加的optionStr = " + optionStr);
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