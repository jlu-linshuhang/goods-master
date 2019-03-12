<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>默认样式</title>
    <link rel="stylesheet" href="https://a.amap.com/jsapi_demos/static/demo-center/css/demo-center.css"/>
    <style>
        html, body, #container {
            height: 100%;
            width: 100%;
        }
    </style>
</head>
<body>
<div id="container"></div>
<div id="tip" class="info">地图上右击鼠标，弹出自定义样式的右键菜单</div>
<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.11&key=您申请的key值&plugin=AMap.MouseTool"></script>
<script type="text/javascript">
    var lnglat = new AMap.LngLat(116.397, 39.918);

    var map = new AMap.Map("container", {
        center: lnglat,
        zoom: 14,
        resizeEnable: true
    });


    var marker;


    //创建右键菜单
    var contextMenu = new AMap.ContextMenu();

    //右键放大
    contextMenu.addItem("放大一级", function () {
        map.zoomIn();
    }, 0);

    //右键缩小
    contextMenu.addItem("缩小一级", function () {
        map.zoomOut();
    }, 1);

    //右键显示全国范围
    contextMenu.addItem("缩放至全国范围", function (e) {
        map.setZoomAndCenter(4, [108.946609, 34.262324]);
    }, 2);

    //右键添加Marker标记
    contextMenu.addItem("添加标记", function (e) {
            marker = new AMap.Marker({
            map: map,
            position: contextMenuPositon //基点位置
        });
    }, 3);

    //地图绑定鼠标右击事件——弹出右键菜单
    map.on('rightclick', function (e) {
        contextMenu.open(map, e.lnglat);
        contextMenuPositon = e.lnglat;
    });















    contextMenu.open(map, lnglat);
</script>
</body>
</html>