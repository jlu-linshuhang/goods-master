<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/21
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>注册页面</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/regist.css'/> ">

    <script type="text/javascript" src="<c:url value="/jquery/jquery-1.5.1.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/jsps/js/user/regist.js"/> "></script>

</head>
<body>
<div id="divMain">
    <div id="divTitle">
        <span id="spanTitle">新用户注册</span>
    </div>
    <div id="divBody">
        <form action="<c:url value="/user/regist.action"/>" method="post" id="registForm">
            <input type="hidden" name="method" value="regist"/>
            <table id="tableForm">
                <tr>
                    <td class="tdText">用户名：</td>
                    <td class="tdInput">
                        <input class="inputClass" type="text" name="loginname" id="loginname" value="${form.loginname}"/>
                    </td>
                    <td class="tdError">
                        <label class="errorClass" id="loginnameError">${errors.loginname}</label>
                    </td>
                </tr>

                <tr>
                    <td class="tdText">登录密码：</td>
                    <td>
                        <input class="inputClass" type="password" name="loginpass" id="loginpass" value="${form.loginpass}"/>
                    </td>
                    <td>
                        <label class="errorClass" id="loginpassError">${errors.loginpass}</label>
                    </td>
                </tr>

                <tr>
                    <td class="tdText">确认密码：</td>
                    <td>
                        <input class="inputClass" type="password" name="reloginpass" id="reloginpass" value="${form.reloginpass}"/>
                    </td>
                    <td>
                        <label class="errorClass" id="reloginpassError">${errors.reloginpass}</label>
                    </td>
                </tr>

                <tr>
                    <td class="tdText">Email：</td>
                    <td>
                        <input class="inputClass" type="text" name="email" id="email" value="${form.email }"/>
                    </td>
                    <td>
                        <label class="errorClass" id="emailError">${errors.email}</label>
                    </td>
                </tr>

                <tr>
                    <td class="tdText">验证码：</td>
                    <td>
                        <%--输入框--%>
                        <input class="inputClass" type="text" name="verifyCode" id="verifyCode" maxlength="4" value="${form.verifyCode }"/>
                    </td>
                    <td>
                        <label class="errorClass" id="verifyCodeError">${errors.verifyCode}</label>
                    </td>
                </tr>


                <tr>
                    <td class="tdText">这是一张图片</td>
                    <td>
                        <div id="divVerifyCode">
                            <img id="imgVerifyCode" src="/goods/getVerifyCode/getVerifyCode" width="110" height="40"/>
                        </div>
                    </td>
                    <td>
                        <label><a href="javascript:_hyz()">换一张</a> </label>
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td>
                        <input type="image" src="<c:url value='/images/regist1.jpg'/>" id="submitBtn"/>
                    </td>
                    <td>
                        <label></label>
                    </td>
                </tr>


            </table>
        </form>
    </div>
</div>
</body>
</html>
