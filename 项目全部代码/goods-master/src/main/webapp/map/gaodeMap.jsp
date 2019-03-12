<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">

    <title>高德地图展示</title>

    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <script src="http://cache.amap.com/lbs/static/es5.min.js"></script>
    <script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.11&key=38f7f7a4f883e9fa27036896503fd200"></script>
    <%--<script src="http://webapi.amap.com/maps?v=1.3&key=9b2c5c13a6501227c97613b559324a12-"></script>--%>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>

    <style type="text/css">
        #container{
            margin-left:200px;
            margin-top:50px;
            width:1500px;
            height:800px;
        }
    </style>
</head>
    <body>
    <div><h2>欢迎进入林书行创建的高德地图</h2></div>
                <div style="font-size: 10pt; line-height: 10px;">
                            <a href="<c:url value='/map/gaodeMap.jsp'/>">高德</a> |&nbsp;
                            <a href="<c:url value='/map/addMap.jsp'/>">添加功能</a> |&nbsp;
                            <a href="<c:url value='/index.jsp'/>">返回商场</a>
                </div>
                <div id="container"></div>

        <script>
            //创建地图,设定地图的中心点和级别
            var map = new AMap.Map('container', {
                resizeEnable: true,
                zoom:13,
                center: [125.324957,43.911629]

            });

            var infowindow = new AMap.InfoWindow({
                content: '<h3>高德地图</h1><div>高德是中国领先的数字地图内容、导航和位置服务解决方案提供商。</div>',
                offset: new AMap.Pixel(0, -30),
                size:new AMap.Size(230,0)
            });

            var marker = new AMap.Marker({
                position: [125.324957,43.911629],
                map:map
            });

        </script>
    </body>
</html>
