
$(function () {
    $(".errorClass").each(function () {
        shouError($(this));
    });

    $("#submitBtn").hover(
        function() {
            $("#submitBtn").attr("src", "/goods/images/regist2.jpg");
        },
        function() {
            $("#submitBtn").attr("src", "/goods/images/regist1.jpg");
        }
    );
    $(".inputClass").focus(function () {
        // alert($(this).attr("id"));
        var labelId = $(this).attr("id")+"Error";
        $("#"+labelId).text("");
        shouError($("#"+labelId));
    });

    $(".inputClass").blur(function () {
        var id = $(this).attr("id");
        var funName = "validate"+id.substring(0,1).toUpperCase()+id.substring(1)+"()";
        eval(funName);//将字符串当成JavaScript代码执行
    });

    $("#registForm").submit(function () {
        var bool = true;
        if(!validateLoginname()){
            bool = false;
        }
        if(!validateLoginpass()){
            bool = false;
        }
        if(!validateReloginpass()){
            bool = false;
        }
        if(!validateEmail()){
            bool = false;
        }
        if(! validateVerifyCode()){
            bool = false;
        }
        return bool;
    });

});

function validateLoginname() {
    /*非空校验，长度校验，是否注册校验*/
    var id = "loginname";
    var value = $("#"+id).val();//获取输入框内容
    if(!value){
        $("#"+id+"Error").text("用户名不能为空");
        shouError($("#"+id+"Error"));
        return false;
    }
    if(value.length<3||value.length>20){
        $("#"+id+"Error").text("用户名长度在3-20之间！");
        shouError($("#"+id+"Error"));
        return false;
    }
    $.ajax({
        url:"/goods/user/regist2.action",
        data:{method:"regist2",loginname:value},
        type:"POST",
        dataType:"json",
        async:false,
        cache:false,
        success:function (result) {
            if(!result){
                $("#"+id+"Error").text("用户名已被注册！");
                shouError($("#"+id+"Error"));
                return false;
            }else {
                $("#"+id+"Error").text("用户名可以注册！");
                shouError($("#"+id+"Error"));
            }

        }
    });
    return true;
}

function validateLoginpass() {
    var id = "loginpass";
    var value = $("#"+id).val();//获取输入框内容
    if(!value){
        $("#"+id+"Error").text("密码不能为空");
        shouError($("#"+id+"Error"));
        return false;
    }
    if(value.length<3||value.length>20){
        $("#"+id+"Error").text("密码长度在3-20之间！");
        shouError($("#"+id+"Error"));
        return false;
    }


    return true;
}

function validateReloginpass() {
    var id = "reloginpass";
    var value = $("#"+id).val();//获取输入框内容
    if(!value){
        $("#"+id+"Error").text("确认密码不能为空");
        shouError($("#"+id+"Error"));
        return false;
    }
    if(value!=$("#loginpass").val()){
        $("#"+id+"Error").text("两次输入不一致！");
        shouError($("#"+id+"Error"));
        return false;
    }

    return true;
}

function validateEmail(){
    var id = "email";
    var value = $("#"+id).val();//获取输入框内容
    if(!value){
        $("#"+id+"Error").text("Email不能为空");
        shouError($("#"+id+"Error"));
        return false;
    }

    if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)){
        $("#"+id+"Error").text("错误的Email格式！");
        shouError($("#"+id+"Error"));
        return false;
    }
    $.ajax({
        url:"/goods/user/regist3.action",
        data:{method:"regist3",email:value},
        type:"POST",
        dataType:"json",
        async:false,
        cache:false,
        success:function (result) {
            if(!result){
                $("#"+id+"Error").text("Email已被注册！");
                shouError($("#"+id+"Error"));
                return false;
            }else {
                $("#"+id+"Error").text("Email可以注册！");
                shouError($("#"+id+"Error"));
            }

        }
    });
    return true;
}

function validateVerifyCode() {
    var id = "verifyCode";
    var value = $("#"+id).val();//获取输入框内容
    if(!value){
        $("#"+id+"Error").text("验证码不能为空");
        shouError($("#"+id+"Error"));
        return false;
    }

    if(value.length!=4){
        $("#"+id+"Error").text("错误的验证码！");
        shouError($("#"+id+"Error"));
        return false;
    }
    $.ajax({
        url:"/goods/user/regist4.action",
        data:{method:"regist4",verifyCode:value},
        type:"POST",
        dataType:"json",
        async:false,
        cache:false,
        success:function (result) {
            if(!result){
                $("#"+id+"Error").text("验证码错误！");
                shouError($("#"+id+"Error"));
                return false;
            }else {
                $("#"+id+"Error").text("验证码正确！");
                shouError($("#"+id+"Error"));
            }

        }
    });
    return true;
}



function shouError(ele) {
    var text = ele.text();
    if(!text){
        ele.css("display","none");
    }else {
        ele.css("display","");
    }
}

function _hyz() {
    $('#imgVerifyCode').attr("src", "/goods/getVerifyCode/getVerifyCode"+"?timestamp=" + genTimestamp());
}
function genTimestamp() {
    var time = new Date();
    return time.getTime();
}