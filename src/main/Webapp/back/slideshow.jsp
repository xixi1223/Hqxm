<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;UTF-8" %>
<div class="col-xs-9">
    <div class="page-header">
        <h2>轮播图管理</h2>
    </div>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">轮播图信息</a></li>
        <li role="presentation"><button class="btn" onclick="bb()">导出轮播图信息</button></li>
        <li role="presentation"><a href="#">导出轮播图模板</a></li>
        <li role="presentation"><a href="#">导入轮播图信息</a></li>
    </ul>
    <table id="slideshow"></table>
    <%--分页--%>
    <div id="page"></div>
</div>

<script>
    $(function(){

        $('#slideshow').jqGrid({
            multiselect: true,
            height:400,
            styleUI:"Bootstrap",
            autowidth:true,
            mtype:"post",
            url:"${pageContext.request.contextPath}/back/AllSlideshow",
            datatype:"json",
            colNames:["","标题","状态","描述","图片"],
            colModel:[
                {name:"id",align:"center",search:false},
                {name:"name",align:"center",editable:true,search:false},
                {name:"status",align:"center",search:true,formatter:function (data) {
                  if(data=="1"){
                      return "展示";
                  }else return "隐藏";
                },editable:true,
                    edittype:"select",editoptions:{value:"0:展示;1:隐藏"}
                },
                {name:"introduction",align:"center",editable:true},
                {name:"path",align:"center",search:false,editable:true,edittype:"file",editoptions:{enctype:"multipart/form/data"},
                formatter:function(cellValue){
                return "<img style='height: 80px;width: 180px;' src='"+cellValue+"'/>"
                }},
            ],
            pager:"#page",
            rowNum:5,
            rowList:[2,5,10],
            viewrecords:true,
            cellEdit:true,
            editurl:"${pageContext.request.contextPath}/back/edit",
            autowidth:true,
        }).navGrid(
            "#page",
            {add:true,edit:true,del:true,search:true,refresh:true},
            //编辑功能
            {closeAfterEdit:true,editCaption:"编辑图片",reloadAfterSubmit:true,
                afterSubmit:function (response,postData) {
                    var slideshowID = response.responseJSON.slideshowId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/back/updataLoad",
                        datatype:"json",
                        type:"post",
                        data:{slideshowId:slideshowID},
                        fileElementId:"path",
                        success:function (data) {
                            // 输出上传成功
                            alert("更新成功");
                            // jqgrid重新载入
                            $("#slideshow").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },
            //添加功能
            {closeAfterAdd:true,addCaption:"图片添加",reloadAfterSubmit:true,
                afterSubmit:function (response,postData) {
                    var slideshowID = response.responseJSON.slideshowId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/back/upload",
                        datatype:"json",
                        type:"post",
                        data:{slideshowId:slideshowID},
                        // 指定的上传input框的id
                        fileElementId:"path",
                        success:function (data) {
                            // 输出上传成功
                            alert("更新成功");
                            // jqgrid重新载入
                            $("#slideshow").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },
            {},//删除功能
            {
                sopt:['eq','ne','cn']//配置搜索条件如何
            }
        );
    });


    function bb() {
        $.ajax({
            url:"${pageContext.request.contextPath}/back/daochu",
            method:"post",
            datatype:"json",
            success:function () {
                alert("导出成功！");
            }
        });
    }

</script>