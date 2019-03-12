<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/2
  Time: 13:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>json交互测试</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript">
        /*请求json，输出json*/
        function requestJson() {
            $.ajax({
                type:'post',
                url:'${pageContext.request.contextPath}/requestJson.action',
                contentType:'application/json;charset=utf-8',
                data:'{"name":"手机","price":"999"}',
                dataType: "text",
                /*dataType: "json",*/
                success:function (data) {
                    alert(data);
                }
            });
        }
        /*请求key/value,输出json*/
        function responseJson() {
            $.ajax({
                type:'post',
                url:'${pageContext.request.contextPath}/responseJson.action',
               /* contentType:'application/json;charset=utf-8',*/
                data:'name=手机&price=999',
              /*  dataType: "text",*/
                dataType: "json",
                success:function (data) {
                    alert(data.name);
                }
            });
        }
    </script>
</head>
<body>
<input type="button" onclick="requestJson()" value="请求json，输出json"/>
<input type="button" onclick="responseJson()" value="请求key/value,输出json"/>
</body>
</html>
