<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>支付宝当面付 二维码支付</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/order/pay.css'/>">
    <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
    <style>
        *{
            margin:0;
            padding:0;
        }
        ul,ol{
            list-style:none;
        }

        .bank-list li{
            float:left;
            width:153px;
            margin-bottom:5px;
        }

        .content dt{
            width:160px;
            display:inline-block;
            text-align:right;
            float:left;

        }
        .content dd{
            margin-left:100px;
            margin-bottom:5px;
        }

        .foot-ul li {
            text-align:center;
        }

        .cashier-nav ol li {
            float: left;
        }

        .alipay_link a:link{
            text-decoration:none;
            color:#8D8D8D;
        }
        .alipay_link a:visited{
            text-decoration:none;
            color:#8D8D8D;
        }
    </style>
</head>
<body>
<c:choose>
    <c:when test="${number eq 0}">订单创建成功</c:when>
    <c:when test="${number eq 1}">订单已创建，请不要重复创建</c:when>
</c:choose>
<div style="margin: 40px;">
    <img align="middle" src="<c:url value="/erweima_img/qr-${oid}.png"/>"/>
    <a href="<c:url value='/order/flush.action?oid=${oid}'/>">刷新页面</a>
    <a href="<c:url value='/order/back2.action?oid=${oid}'/>">查询订单状态</a>
</div>

</body>
</html>