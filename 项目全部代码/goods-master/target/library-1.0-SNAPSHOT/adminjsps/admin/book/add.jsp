<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'bookdesc.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/book/add.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/jquery/jquery.datepick.css'/>">
<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick.js'/>"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick-zh-CN.js'/>"></script>
<script type="text/javascript">
$(function () {
	/*$("#publishtime").datepick({dateFormat:"yy-mm-dd"});
	$("#printtime").datepick({dateFormat:"yy-mm-dd"});*/
	
	$("#btn").addClass("btn1");
	$("#btn").hover(
		function() {
			$("#btn").removeClass("btn1");
			$("#btn").addClass("btn2");
		},
		function() {
			$("#btn").removeClass("btn2");
			$("#btn").addClass("btn1");
		}
	);
	
	$("#btn").click(function() {
		var bname = $("#bname").val();
		var currprice = $("#currprice").val();
		var price = $("#price").val();
		var discount = $("#discount").val();
		var author = $("#author").val();
		var press = $("#press").val();
		var pid = $("#pid").val();
		var cid = $("#cid").val();
		var imageW = $("#imageW").val();
		var imageB = $("#imageB").val();
		
		if(!bname || !currprice || !price || !discount || !author || !press || !pid || !cid || !imageW|| !imageB) {
			alert("图名、当前价、定价、折扣、作者、出版社、1级分类、2级分类、大图、小图都不能为空！");
			return false;
		}
		
		if(isNaN(currprice) || isNaN(price) || isNaN(discount)) {
			alert("当前价、定价、折扣必须是合法小数！");
			return false;
		}
		$("#form").submit();
	});
});
function loadChildren() {
	var pid = $("#pid").val();
	$.ajax({
        async:true,
        cache:false,
        url:"/goods/adminBook/ajaxFindChildren.action",
        data:{method:"ajaxFindChildren",pid:pid},
        type:"POST",
        dataType:"json",
        success:function(arr) {
            // 3. 得到cid，删除它的内容
            $("#cid").empty();//删除元素的子元素
            $("#cid").append($("<option>====请选择2级分类====</option>"));//4.添加头
            // 5. 循环遍历数组，把每个对象转换成<option>添加到cid中
            for(var i = 0; i < arr.length; i++) {
                var option = $("<option>").val(arr[i].cid).text(arr[i].cname);
                $("#cid").append(option);
            }
        }
	});

}

</script>
  </head>
  
  <body>
  <div>
   <p style="font-weight: 900; color: red;">${msg }</p>
   <form action="<c:url value="/adminBook/insertBook.action"/>" enctype="multipart/form-data" method="post" id="form">
    <div>
	    <ul>
	    	<li>书名：　<input id="bname" type="text" name="bname" value="Spring实战(第3版)" style="width:500px;"/></li>
	    	<li>大图：　<input id="imageW" type="file" name="item_imageW"/></li>
	    	<li>小图：　<input id="imageB" type="file" name="item_imageB"/></li>
	    	<li>定价：<input id="price" type="text" name="price" value="59.0" style="width:50px;"/></li>
	    	<li>当前价：　<input id="currprice" type="text" name="currprice" value="40.7" style="width:50px;"/>
	    	折扣：<input id="discount" type="text" name="discount" value="6.9" style="width:30px;"/>折</li>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table>
			<tr>
				<td colspan="3">
					作者：　　<input type="text" id="author" name="author" value="Craig Walls" style="width:150px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					出版社：　<input type="text" name="press" id="press" value="人民邮电出版社" style="width:200px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">出版时间：<input type="text" id="publishtime" name="publishtime" value="2013-6-1" style="width:100px;"/>xxxx-xx-xx</td>
			</tr>
			<tr>
				<td>版次：　　<input type="text" name="edition" id="edition" value="1" style="width:40px;"/></td>
				<td>页数：　　<input type="text" name="pagenum" id="pagenum" value="374" style="width:50px;"/></td>
				<td>字数：　　<input type="text" name="wordnum" id="wordnum" value="48700" style="width:80px;"/></td>
			</tr>
			<tr>
				<td width="250">印刷时间：<input type="text" name="printtime" id="printtime" value="2013-6-1" style="width:100px;"/>xxxx-xx-xx</td>
				<td width="250">开本：　　<input type="text" name="booksize" id="booksize" value="16" style="width:30px;"/></td>
				<td>纸张：　　<input type="text" name="paper" id="paper" value="胶版纸" style="width:80px;"/></td>
			</tr>
			<tr>
				<td>
					一级分类：<select name="pid" id="pid" onchange="loadChildren()">
						<option value="">====请选择1级分类====</option>
					<c:forEach items="${parents}" var="parent">
						<option value="${parent.cid}">${parent.cname}</option>
					</c:forEach>
				</select>
				</td>
				<td>
					二级分类：<select name="cid" id="cid">
						<option value="">====请选择2级分类====</option>

					</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<input type="button" id="btn" class="btn" value="新书上架">
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
   </form>
  </div>

  </body>
</html>
