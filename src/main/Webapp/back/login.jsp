<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript">
        function changeImage(){
            //根据id获得验证码图片对象
            var captchaImg= document.getElementById('imgVcode');
            captchaImg.src='${pageContext.request.contextPath }/admin/YanZheng?'+Math.random();
        }
        //登录
        function login() {
            $.ajax({
                url:"${pageContext.request.contextPath}/admin/login",
                type:"POST",
                datatype:"JSON",
                data:$("#loginForm").serialize(),
                success:function (data) {
                    console.log(data);
                    if (data!=null&data!=""){
                        $("#msg").html("<span class='error' style='color: #4297d7'>"+data+"</span>")
                    }else {
                        location.href = "${pageContext.request.contextPath}/back/main.jsp";
                    }
                }
            })
        }
    </script>
</head>
<body style=" background: url(${pageContext.request.contextPath}/img/009e9dfd5266d016d30938279a2bd40735fa3576.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="${pageContext.request.contextPath}/admin/login">
            <div class="modal-body" id = "model-body">
                <div class="form-group">
                    <input type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username" >
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="验证码" autocomplete="off" name="yzm" style="width: 40%">
                    <div class="Captcha-operate" style="float: left; margin-left: 230px; margin-top: -31px;">
                        <div class="Captcha-imageConatiner">
                            <a class="code_pic" id="vcodeImgWrap" name="change_code_img" href="javascript:void(0);">
                                <img id="imgVcode" src="${pageContext.request.contextPath}/admin/YanZheng" class="Ucc_captcha Captcha-image" onClick="changeImage()">
                            </a>
                            <a id="vcodeImgBtn" name="change_code_link" class="code_picww" href="javascript:changeImage()">换张图</a>
                            <span id="spn_vcode_ok" class="icon_yes pin_i" style="display: none;"></span>
                            <span id="J_tipVcode" class="cue warn"></span>
                        </div>
                    </div>
                </div>
                <span id="msg"></span>
            </div>
            <div class="modal-footer">
                <div class="form-group">
                    <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
