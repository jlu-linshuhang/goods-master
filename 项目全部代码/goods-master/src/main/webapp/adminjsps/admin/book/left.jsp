<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'left.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/book/left.css'/>">
	  <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/menu/mymenu.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/menu/mymenu.css'/>" type="text/css" media="all">
	  <script language="javascript">
          var bar = new Q6MenuBar("bar", "林书行商场");
          $(function() {
              bar.colorStyle = 2;//背景颜色配色方案，有01234，共五中方案  黄色、蓝色、深灰色、浅灰色、绿色
              bar.config.imgDir = "<c:url value='/menu/img/'/>";//一级分类和二级分类的加减号图片
              bar.config.radioButton=true;//用来判断是否能够同时打开多个一级分类，true就只能打来一个，false能打来多个
              /*
              1. 程序设计：一级分类名称
              2. Java Javascript：二级分类名称
              3. /goods/jsps/book/list.jsp：点击二级分类后链接到的URL
              4. body:链接的内容在哪个框架页中显示,例如在body.jsp页面显示
              */
              <c:forEach items="${parents}" var="parent">
				  <c:forEach items="${parent.children}" var="child">
					  bar.add("${parent.cname}", "${child.cname}", "/goods/adminBook/findBookByCategory.action?cid=${child.cid}", "body");
				  </c:forEach>
              </c:forEach>


              $("#menu").html(bar.toString());
          });
	  </script>
  </head>
  
  <body onload="load()">
<div id="menu"></div>
  </body>
</html>
