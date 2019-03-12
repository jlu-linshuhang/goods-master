<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/17
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script>
        function _hyz() {
            var img = document.getElementById("imgVerifyCode");
            img.src = "/goods/VerifyCodeServlet?a="+new Date().getTime();
        }
    </script>
</head>
<body>

<form action="/goods/LoginServlet" method="post">
    <%--<input type="hidden " value="login">--%>
    <%--用于提交表单进行验证，使用login方法的servlet，笔记上有，没有写--%>
    用户名：<input type="text" name="name"/><br/>
    密码:<input type="password" name="pwd"/><br/>
    验证码:<input type="text" name="verifyCode"/><br/>
    <img src="/goods/VerifyCodeServlet" id="imgVerifyCode"/>
    <a href="javascript:_hyz()">换一张</a>
    <br/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
