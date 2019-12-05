<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;UTF-8" %>
<div class="col-xs-9">
    <div class="page-header">
        <h2>文章管理</h2>
    </div>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">文章信息</a></li>

        <li role="presentation">
            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#exampleModal" >添加</button>
        </li>
    </ul>
    <table id="articleshow"></table>
    <%--分页--%>
    <div id="page"></div>
</div>

<script>
    $(function(){
        $('#articleshow').jqGrid({
            multiselect: false,
            height:400,
            styleUI:"Bootstrap",
            autowidth:true,
            mtype:"post",
            url:"${pageContext.request.contextPath}/back/AllArticle",
            datatype:"json",
            colNames:["id","标题","状态","上传时间","作者","内容","封面","操作"],
            colModel:[
                {name:"id",align:"center",search:false,hidden:true},
                {name:"title",align:"center",editable:true,search:false},
                {name:"status",align:"center",search:true,formatter:function (data) {
                    if(data=="0"){
                        return "展示";
                    }else return "隐藏";
                },editable:true,
                    edittype:"select",editoptions:{value:"0:展示;1:隐藏"}
                },
                {name :"createdate",search:false,align:"center",editable:true,edittype:"date"},
                {name:"author",align:"center"},
                {name:"content",align:"center",hidden:true},

                {name:"cover",align:"center",search:false,editable:true,edittype:"file",editoptions:{enctype:"multipart/form/data"},
                    formatter:function(cellValue){
                        return "<img style='height: 80px;width: 180px;' src='"+cellValue+"'/>"
                    }},

                {name:"options",align:"center",
                    formatter:function(value,options,rowObject){
                        var id=rowObject.id;
                        return `<button type="button" class="btn btn-default" data-toggle="modal" data-target="#exampleModal">
                            <span class="glyphicon glyphicon-th left" onclick="ShowArticle('`+id+`');"></span>
                            </button>`;
                    }
                },
            ],
            pager:"#page",
            rowNum:5,
            rowList:[2,5,10],
            viewrecords:true,
            cellEdit:true,
            editurl:"",
            autowidth:true,
        }).navGrid(
            "#page",
            {add:false,edit:false,del:false,search:false,refresh:true},
            //编辑功能
            {},
            //添加功能
            {},
            {},//删除功能
            {}
        );
    });

    $.post("${pageContext.request.contextPath}/back/allsh",function (result) {
        $.each(result,function (i,guru) {
            var option = $("<option/>").val(guru.id).text(guru.name);
            $("#guruSelect").append(option);
        });
    },"JSON");

    function ShowArticle(id) {
        $('#frm1')[0].reset();
        KindEditor.html("#editor_id","");
        $.ajax({
            url:"${pageContext.request.contextPath}/back/SelectArticle",
            data:{id:id},
            method:"post",
            success:function(data){
                $('#id').attr("value",data.id);
                $('#title1').attr("value",data.title);
                $('#status').attr("value",data.status);
                $('#cre').attr("value",data.createdate);
                $('#author').attr("value",data.author);
                $('#guruSelect').attr("value",data.grguId);
                KindEditor.html("#editor_id",data.content);
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
            }
        });


    }
</script>