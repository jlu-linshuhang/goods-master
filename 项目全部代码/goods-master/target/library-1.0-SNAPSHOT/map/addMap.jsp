<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>林书行创建的高德地图展示</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <script src="http://cache.amap.com/lbs/static/es5.min.js"></script>
    <script src="https://a.amap.com/jsapi_demos/static/demo-center/js/demoutils.js"></script>
    <script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.11&key=38f7f7a4f883e9fa27036896503fd200&plugin=AMap.Driving,AMap.Autocomplete,AMap.MouseTool"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
    <style type="text/css">
        #container{
            margin-left:300px;
            margin-top:70px;
            width:1550px;
            height:800px;
        }
        #panel {
            position: fixed;
            background-color: white;
            max-height: 90%;
            overflow-y: auto;
            top: 10px;
            right: 10px;
            width: 280px;
        }
        #panel .amap-call {
            background-color: #009cf9;
            border-top-left-radius: 4px;
            border-top-right-radius: 4px;
        }
        #panel .amap-lib-driving {
            border-bottom-left-radius: 4px;
            border-bottom-right-radius: 4px;
            overflow: hidden;
        }
        #tip2 {
            background-color: #ddf;
            color: #333;
            border:1px solid silver;
            box-shadow: 3px 4px 3px 0px silver;
            position: absolute;
            top: 5px;
            left: 350px;
            border-radius: 5px;
            overflow: hidden;
            line-height: 20px;
        }
        #tip2 input[type="text"] {
            height: 25px;
            border: 0;
            padding-left: 5px;
            width: 280px;
            border-radius: 3px;
            outline: none;
        }
        .info{
            width:26rem;
        }

    </style>

</head>
    <body>
        <div><h2>欢迎进入林书行创建的高德地图</h2></div>
        <%--按钮--%>
        <div style="font-size: 10pt; line-height: 10px;">
            <a href="<c:url value='/map/gaodeMap.jsp'/>">高德</a> |&nbsp;
            <a href="<c:url value='/map/addMap.jsp'/>">添加功能</a> |&nbsp;
            <a href="<c:url value='/index.jsp'/>">返回商场</a>
        </div>
        <%--画地图--%>
        <div id="container"></div>
        <%--驾车路线--%>
        <div id="panel"></div>
        <%--查询位置详细信息--%>
        <div id="mapContainer"></div>
        <div id="tip2">
            <input type="text" id="keyword" name="keyword" value="请输入关键字：(选定后搜索)" onfocus='this.value=""'/>
        </div>

        <%--定位显示位置坐标--%>
        <div class="info">
            <div id='result'>定位结果： <br>&nbsp;&nbsp;您还没有定位</div>
            <div id='location'>点击位置信息： <br>&nbsp;&nbsp;您还没有点击地图</div>
            <div id='locationAll'>点击位置地址信息： <br>&nbsp;&nbsp;您还没有点击地图</div>
        </div>
        <%--按钮--%>
        <div class="input-card" style="width: auto;">
            <div class="input-item">
                <button class="btn" onclick="toggle()">显示/隐藏实时路况</button>
            </div>
            <div class="input-item">
                <button class="btn" onclick="toggle2()">显示/隐藏驾车路线</button>
            </div>
        </div>

        <table>
            <tr>
                <td>起点：</td>
                <td><input type="text" name="start" id="start"></td>
            </tr>
            <tr>
                <td>经过：</td>
                <td><input type="text" name="pass" id="pass"></td>
            </tr>
            <tr>
                <td>终点：</td>
                <td><input type="text" name="end" id="end"></td>
            </tr>
            <tr>
                <td><input type="submit" value="查询" onclick="ok()"></td>
            <tr>
        </table>


        <script>
            //创建地图,设定地图的中心点和级别
            var map = new AMap.Map('container', {
                resizeEnable: true,
                center: [125.324957,43.911629],//中心点坐标
                zoom:13,
                keyboardEnable: false

            });

            //添加路况信息和按钮，选择是否隐藏
            var trafficLayer = new AMap.TileLayer.Traffic({
                zIndex: 10
            });
            trafficLayer.setMap(map);

            //构造路线导航类
            var driving = new AMap.Driving({
                map: map,
                panel: "panel"
            });

            //输入提示
            var auto = new AMap.Autocomplete({
                input: "tipinput"
            });

            //查询位置详细信息
            var windowsArr = [];
            var marker = [];
            AMap.plugin(['AMap.Autocomplete','AMap.PlaceSearch'],function(){
                var autoOptions = {
                    city: "长春", //默认城市：长春
                    input: "keyword"
                };
                autocomplete= new AMap.Autocomplete(autoOptions);
                var placeSearch = new AMap.PlaceSearch({
                    city:'长春',
                    map:map
                });
                AMap.event.addListener(autocomplete, "select", function(e){
                    placeSearch.setCity(e.poi.adcode);
                    placeSearch.search(e.poi.name)
                });
            });

            //定位
            AMap.plugin('AMap.Geolocation', function() {
                var geolocation = new AMap.Geolocation({
                    enableHighAccuracy: true,//是否使用高精度定位，默认:true
                    timeout: 10000,          //超过10秒后停止定位，默认：5s
                    buttonPosition:'RB',    //定位按钮的停靠位置
                    buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                    zoomToAccuracy: true,   //定位成功后是否自动调整地图视野到定位点
                    showCircle: false,
                    panToLocation: true,
                    zoomToAccuracy:true
                });
                map.addControl(geolocation);
                geolocation.getCurrentPosition(function(status,result){
                    if(status=='complete'){
                        onComplete(result)
                    }
                });
            });
            //点击查询位置信息
            map.on('click', function(e) {
                var lng = e.lnglat.getLng();
                var lat = e.lnglat.getLat();

                var str = [];
                str.push('点击位置信息：<br>&nbsp;&nbsp;'+lng+','+lat);
                document.getElementById('location').innerHTML = str.join('<br>');
                var lnglatXY = [lng, lat];//地图上所标点的坐标
                AMap.service('AMap.Geocoder',function() {//回调函数
                    geocoder = new AMap.Geocoder({});
                    geocoder.getAddress(lnglatXY, function (status, result) {
                        if (status === 'complete') {
                            var str2 = [];
                            str2.push('点击位置地址信息： <br>&nbsp;&nbsp;'+result.regeocode.formattedAddress);
                            document.getElementById('locationAll').innerHTML = str2.join('<br>');
                        }
                    });
                })
            });

            //输入导航
            var path = [];
            var route,maker;
            function ok() {
                var start = document.getElementById('start').value;
                var pass = document.getElementById('pass').value;
                var end = document.getElementById('end').value;
                var str_1 = start.split(",");
                var str_2 = pass.split(",");
                var str_3 = end.split(",");
                if(pass==""||pass==null){
                    var a = str_1[0];
                    var b = str_1[1];
                    var node1 = new AMap.LngLat(a, b);
                    a = str_3[0];
                    b = str_3[1];
                    var node2 = new AMap.LngLat(a, b);
                    driving.clear();
                    driving.search(node1,node2, function(status, result) { });
                }else {
                    var a = str_1[0];
                    var b = str_1[1];
                    var node1 = new AMap.LngLat(a, b);
                     a = str_2[0];
                     b = str_2[1];
                    var node2 = new AMap.LngLat(a, b);
                    a = str_3[0];
                    b = str_3[1];
                    var node3 = new AMap.LngLat(a, b);
                    path = [];

                    path.push(node1);
                    path.push(node2);
                    path.push(node3);
                    map.plugin("AMap.DragRoute", function() {
                        route = new AMap.DragRoute(map, path, AMap.DrivingPolicy.LEAST_FEE); //构造拖拽导航类
                        route.search(); //查询导航路径并开启拖拽导航
                    });
                }

            }












            function onComplete(data) {
                var str = [];
                str.push('定位结果：<br>&nbsp;&nbsp;' + data.position);
                document.getElementById('result').innerHTML = str.join('<br>');
            }
            var isVisible = true;
            function toggle() {
                if (isVisible) {
                    trafficLayer.hide();
                    isVisible = false;
                } else {
                    trafficLayer.show();
                    isVisible = true;
                }
            }
            function toggle2() {
                if (path.length==0) {
                    route = new AMap.DragRoute(map, path, AMap.DrivingPolicy.LEAST_FEE); //构造拖拽导航类
                    route.search();
                } else {
                    path = [];
                    route.destroy();
                }
            }
        </script>
    </body>
</html>
