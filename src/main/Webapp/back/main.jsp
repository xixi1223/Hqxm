<%@ page pageEncoding="UTF-8" contentType="text/html;UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/back.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/css/jquery-ui.css">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/ajaxfileupload.js"></script>
    <script src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <script charset="UTF-8" src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <title>持名法舟后台管理系统</title>
    <script>
        KindEditor.ready(function(K) {
            window.editor = K.create('#editor_id',{
                width:'100%',
                //图片上传路径
                uploadJson:"${pageContext.request.contextPath}/back/uploadImg",
                allowFileManager:true,
                fileManagerJson:"${pageContext.request.contextPath}/back/showAllImgs",
                afterBlur:function () {
                    this.sync();
                }
            });
        });

        function addArticle() {
            var id=$("#id").val();
            var title=$('#title1').val();
            var status=$('#status').val();
            var createdate=$('#cre').val();
            var author=$('#author').val();
            var cover=$("#coverPic").val();
            var grguId=$('#guruSelect').val();
            var content=$('#editor_id').val();
            $.ajaxFileUpload({
                url:"${pageContext.request.contextPath}/back/addAtticle",
                datatype:"json",
                type:"post",
                fileElementId:"coverPic",
                data:{"id":id,"title":title,"status":status,"createdate":createdate,"author":author,"cover":cover,"grguId":grguId,"content":content},
                success:function () {
                    $("#articleshow").trigger('reloadGrid');
                }
            })
        }
    </script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">驰名法州后台管理系统</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li ><a href="#">欢迎:${username}</a></li>
                <li ><a href="#">退出登录</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container-fluid ">
    <div class="row">
        <div class="col-xs-3">
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseOne">
                                <h3>用户管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/user.jsp');">用户信息管理</a></li>
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/echarts.jsp');">用户注册趋势</a></li>
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/map.jsp');">用户注册分布</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo">
                                <h3>轮播图管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/slideshow.jsp');">轮播图信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree">
                                <h3>上师管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/guru.jsp');">上师信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                                <h3>文章管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/article.jsp');">文章信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive">
                                <h3>专辑管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/special.jsp');">专辑信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="content">

        <div class="col-xs-9">
            <div class="container">
                <div class="jumbotron">
                    <h3>欢迎使用持名法舟后台管理系统！</h3>
                </div>
            </div>
            <div id="myCarousel" class="carousel slide">
                <!-- 轮播（Carousel）指标 -->
                <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                </ol>
                <!-- 轮播（Carousel）项目 -->
                <div class="carousel-inner">
                    <div class="item active">
                        <img src="../img/3e4d03381f30e924eebbff0d40086e061d95f7b0.jpg" alt="First slide" style="width: 1110px;height: 611px;">
                        <div class="carousel-caption">标题 1</div>
                    </div>
                    <div class="item">
                        <img src="../img/009e9dfd5266d016d30938279a2bd40735fa3576.jpg" alt="Second slide" style="width: 1110px;height: 611px;">
                        <div class="carousel-caption">标题 2</div>
                    </div>
                    <div class="item">
                        <img src="../img/098ca7cad1c8a786b4e6a0366609c93d71cf5049.jpg" alt="Third slide" style="width: 1110px;height: 611px;">
                        <div class="carousel-caption">标题 3</div>
                    </div>
                </div>
                <!-- 轮播（Carousel）导航 -->
                <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>

        </div>
    </div>
</div>

<div class="panel-footer" style="margin-top: 10px;">
    <h4 style="text-align: center">百知教育 @baizhiedu.com.cn</h4>
</div>

<%--文章模态框--%>
<div class="modal fade" id="exampleModal" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">文章管理</h4>
            </div>
            <div class="modal-body">
                <form enctype="multipart/form-data" method="post" id="frm1">
                    <div class="form-group" hidden>
                        <label class="control-label">ID</label>
                        <input type="text" class="form-control" name="id" id="id" value="">
                    </div>
                    <div class="form-group">
                        <label class="control-label">标题</label>
                        <input type="text" class="form-control" name="title" id="title1" value="">
                    </div>
                    <div class="form-group">
                        <label  class="control-label">状态</label>
                        <select name="status" id="status">
                            <option value="0">展示</option>
                            <option value="1">隐藏</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label  class="control-label">创建时间</label>
                        <input type="date" name="createdate" id="cre" value="">
                    </div>
                    <div class="form-group">
                        <label class="control-label">作者</label>
                        <input type="text" class="form-control" name="author" id="author" value="">
                    </div>
                    <div class="form-group">
                        <label  class="control-label">封面</label>
                        <input type="file" name="coverPic" id="coverPic">
                    </div>
                    <div class="form-group">
                        <label  class="control-label">上师</label>
                        <select id="guruSelect"></select>
                    </div>
                    <div class="form-group">
                        <label >内容</label>
                        <textarea id="editor_id" name="content">

                        </textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="addArticle()" data-dismiss="modal" >提交</button>
            </div>
        </div>

    </div>
</div>
</body>
</html>