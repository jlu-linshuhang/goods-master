<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/list.css'/>">
<script type="text/javascript">
	$(function () {
		showTotal();
		$("#selectAll").click(function () {
			var bool = $("#selectAll").attr("checked");
			setItemCheckBox(bool);
			setJieSuan(bool);
			showTotal();
        });

		$(":checkbox[name=checkboxBtn]").click(function () {
			var all = $(":checkbox[name=checkboxBtn]").length;
			var select = $(":checkbox[name=checkboxBtn][checked=true]").length;
			if (select==all){
				$("#selectAll").attr("checked",true);
				setJieSuan(true);
			}else if (select==0){
                $("#selectAll").attr("checked",false);
                setJieSuan(false);
			}else {
                $("#selectAll").attr("checked",false);
                setJieSuan(true);
			}
			showTotal();
        });
		$(".jian").click(function () {
			var id = $(this).attr("id").substring(0,32);
			var quantity = $("#"+id+"Quantity").val();
			if (quantity == 1){
			    if (confirm("是否删除？")){
			        location = "/goods/cart/deletebatch.action?cartItemIds="+id;
				}
			}else{
                sendUpdateQuantity(id,quantity-1);
			}
        });
		$(".jia").click(function () {
            var id = $(this).attr("id").substring(0,32);
            var quantity = $("#"+id+"Quantity").val();
            sendUpdateQuantity(id,Number(quantity)+1);
        });

    });

	/*发送异步请求*/
    function sendUpdateQuantity(id,quantity){
		$.ajax({
			async:false,
			cache:false,
			url:"/goods/cart/updateQuantity.action",
			data:{cartitemid:id,quantity:quantity},
			type:"POST",
			dataType:"json",
			success:function (result) {
				$("#"+id+"Quantity").val(result.quantity);
				$("#"+id+"Subtotal").text(result.subtotal);
				showTotal();
            }
		});
	}


	
	/*总计*/
	function showTotal() {
	    var total = 0;
		$(":checkbox[name=checkboxBtn][checked=true]").each(function () {
			var id = $(this).val();
			var text = $("#"+id+"Subtotal").text();
			total = total + Number(text);
        });
		$("#total").text(round(total, 2));/*进行四舍五入，解决误差影响，保留两位小数*/
    }
    /*全选按钮设置*/
	function setItemCheckBox(bool) {
		$(":checkbox[name=checkboxBtn]").attr("checked",bool);
    }
    /*设置结算按钮样式*/
    function setJieSuan(bool) {
		if (bool){
            $("#jiesuan").removeClass("kill").addClass("jiesuan");
            $("#jiesuan").unbind("click");
		}else {
            $("#jiesuan").removeClass("jiesuan").addClass("kill");
            $("#jiesuan").click(function () {
				return false;
            });
		}
    }
    /*批量删除*/
    function batchDelete() {
		var cartItemIdArray = new Array();
        $(":checkbox[name=checkboxBtn][checked=true]").each(function () {
			cartItemIdArray.push($(this).val());
        });
        location = "/goods/cart/deletebatch.action?cartItemIds="+cartItemIdArray;
    }
    /*结算*/
    function jiesuan() {
        var cartItemIdArray = new Array();
        $(":checkbox[name=checkboxBtn][checked=true]").each(function () {
            cartItemIdArray.push($(this).val());
        });
        $("#cartItemIds").val(cartItemIdArray.toString());
        $("#hiddenTotal").val($("#total").text());
        $("#jieSuanForm").submit();
    }

	
</script>


  </head>
  <body>
<c:choose>
	<c:when test="${empty cartItemList}">
		<table width="95%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right">
					<img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
				</td>
				<td>
					<span class="spanEmpty">您的购物车中暂时没有商品</span>
				</td>
			</tr>
		</table>

		<br/>
		<br/>
	</c:when>
	<c:otherwise>
		<table width="95%" align="center" cellpadding="0" cellspacing="0">
			<tr align="center" bgcolor="#efeae5">
				<td align="left" width="50px">
					<input type="checkbox" id="selectAll" checked="checked"/><label for="selectAll">全选</label>
				</td>
				<td colspan="2">商品名称</td>
				<td>单价</td>
				<td>数量</td>
				<td>小计</td>
				<td>操作</td>
			</tr>

			<c:forEach items="${cartItemList}" var="cartItem">
				<tr align="center">
					<td align="left">
						<input value="${cartItem.cartitemid}" type="checkbox" name="checkboxBtn" checked="checked"/>
					</td>
					<td align="left" width="70px">
						<a class="linkImage" href="<c:url value='/book/load.action?bid=${cartItem.bid}'/>"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.imageB}'/>"/></a>
					</td>
					<td align="left" width="400px">
						<a href="<c:url value='/book/load.action?bid=${cartItem.bid}'/>"><span>${cartItem.bname}</span></a>
					</td>
					<td><span>&yen;<span class="currPrice">${cartItem.currprice}</span></span></td>
					<td>
						<a class="jian" id="${cartItem.cartitemid}Jian"></a><input class="quantity" readonly="readonly" id="${cartItem.cartitemid}Quantity" type="text" value="${cartItem.quantity}"/><a class="jia" id="${cartItem.cartitemid}Jia"></a>
					</td>
					<td width="100px">
						<span class="price_n">&yen;<span class="subTotal" id="${cartItem.cartitemid}Subtotal">${cartItem.subtotal}</span></span>
					</td>
					<td>
						<a href="<c:url value='/cart/delete.action?cartItemIds=${cartItem.cartitemid}'/>">删除</a>
					</td>
				</tr>
			</c:forEach>






			<tr>
				<td colspan="4" class="tdBatchDelete">
					<a href="javascript:batchDelete();">批量删除(不要瞎点！)</a>
				</td>
				<td colspan="3" align="right" class="tdTotal">
					<span>总计：</span><span class="price_t">&yen;<span id="total"></span></span>
				</td>
			</tr>
			<tr>
				<td colspan="7" align="right">
					<a href="javascript:jiesuan();" id="jiesuan" class="jiesuan"></a>
				</td>
			</tr>
		</table>
		<form id="jieSuanForm" action="<c:url value='/cart/loadCartItems.action'/>" method="post">
			<input type="hidden" name="cartItemIds" id="cartItemIds"/>
			<input type="hidden" name="total" id="hiddenTotal"/>
			<input type="hidden" name="method" value="loadCartItems"/>
		</form>
	</c:otherwise>
</c:choose>

  </body>
</html>
