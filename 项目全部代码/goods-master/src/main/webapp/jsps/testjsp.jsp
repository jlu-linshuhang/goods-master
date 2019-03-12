<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html lang="en">
<head>
    <title>登录界面</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <script type="text/javascript" src="<c:url value="/jquery/jquery-1.5.1.js"/>"></script>


    <script type="text/javascript">
        function genTimestamp() {
            var time = new Date();
            return time.getTime();
        }
        function changeImage() {
            $('#verifyCodeImage').attr('src', '${pageContext.request.contextPath }/login/getVerifyCode?timestamp=' + genTimestamp());
        }
        //登录，目前只检测验证码
        function login(){
            var user_input_verifyCode=$("#user_input_verifyCode").val();
            $.ajax({
                type : 'post',
                url : "${pageContext.request.contextPath}/login/login",
                data:{verifyCode:user_input_verifyCode},
                //dataType : "json",
                success : function(data) {
                    //alert("success!");
                },
                error : function() {
                    alert("查询失败");
                }
            });
        }

    </script>
</head>
<body>

<div class="login-content">
    <div class="login">
        <form>
            <div>
                <%--输入框--%>
                <input type="text" id="user_input_verifyCode" name="user_input_verifyCode" placeholder="验证码" maxlength="4"/>
                <%--图片--%>
                <span class="code_img">
                    <img src="${pageContext.request.contextPath }/login/getVerifyCode" width="110" height="40" id="verifyCodeImage">
                </span>
                <%--换一张--%>
                <span>
                    <a id="changeVerifImageRegister" onclick="javascript:changeImage();">换一张</a>
                </span>

            </div>
            <input id="submit" type="button" value="登录" onclick="login()"/>
        </form>
    </div>
</div>

</body>
</html>
