<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;UTF-8" %>
<div class="col-xs-9">
    <div class="page-header">
        <h2>上师管理</h2>
    </div>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">上师信息</a></li>

        <li role="presentation">
            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#exampleModal">添加</button>
        </li>
    </ul>
    <table id="gurushow"></table>
    <%--分页--%>
    <div id="page"></div>


    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">上师管理</h4>
                </div>
                <div class="modal-body">
                    <form enctype="multipart/form-data" method="post" id="frm">
                        <div class="form-group" hidden>
                            <label class="control-label">ID</label>
                            <input type="text" class="form-control" name="id" id="id" value="">
                        </div>
                        <div class="form-group">
                            <label class="control-label">姓名</label>
                            <input type="text" class="form-control" name="name" id="name" value="">
                        </div>
                        <div class="form-group">
                            <label  class="control-label">法号</label>
                            <input type="text" class="form-control" name="monastic" id="mon" value="">
                        </div>
                        <div class="form-group">
                            <label  class="control-label">头像</label>
                            <input type="file" name="upheadpic" id="upheadpic">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="addRow()" data-dismiss="modal" >提交</button>
                </div>
            </div>
        </div>
    </div>

</div>
<script>
    $(function(){
        $('#gurushow').jqGrid({
            multiselect: false,
            height:400,
            styleUI:"Bootstrap",
            autowidth:true,
            mtype:"post",
            url:"${pageContext.request.contextPath}/back/AllGuru",
            datatype:"json",
            colNames:["","姓名","法号","头像","操作"],
            colModel:[
                {name:"id",align:"center",search:false,hidden:true},
                {name:"name",align:"center",editable:true,search:false},
                {name:"monastic",align:"center",editable:true},
                {name:"headpic",align:"center",search:false,editable:true,edittype:"file",editoptions:{enctype:"multipart/form/data"},
                    formatter:function(cellValue){
                        return "<img style='height: 80px;width: 180px;' src='"+cellValue+"'/>"
                    }},
                {name:"options",align:"center",
                    formatter:function(value,options,rowObject){
                        var id=rowObject.id;
                        return `<button type="button" class="btn btn-success" data-toggle="modal" data-target="#exampleModal" onclick="Shangshi('`+id+`')">
                            修改</button>&nbsp;&nbsp;<button class='btn btn-danger' onclick="delRow('`+id+`');">删除</button>`;
                    }
                },
            ],
            pager:"#page",
            rowNum:2,
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
    
    function Shangshi(id) {
        $('#frm')[0].reset();
        $.ajax({
            url:"${pageContext.request.contextPath}/back/SelectShangshi",
            method:"post",
            data:{"id":id},
            success:function(data){
                $('#id').attr("value",data.id);
                $('#name').attr("value",data.name);
                $('#mon').attr("value",data.monastic);
            }
        });
    }

    function addRow() {

        var id=$("#id").val();
        var name=$('#name').val();
        var monastic=$('#mon').val();
        var headpic=$("#upheadpic").val();
        $.ajaxFileUpload({
            url:"${pageContext.request.contextPath}/back/upShangshi",
            datatype:"json",
            type:"post",
            fileElementId:"upheadpic",
            data:{"id":id,"name":name,"monastic":monastic,"headpic":headpic},
            success:function () {
                alert("OK!")
                $("#gurushow").trigger('reloadGrid');
            }
        })
    }
    
    function delRow(id) {
        $.ajax({
            url:"${pageContext.request.contextPath}/back/deleteShangshi",
            datatype:"json",
            type:"post",
            data:{"id":id},
            success: function () {
                $("#gurushow").trigger('reloadGrid');
            }
        });
        
    }

</script>