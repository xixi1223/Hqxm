<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;UTF-8" %>
<div class="col-xs-9">
    <div class="page-header">
        <h2>用户管理</h2>
    </div>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">用户信息</a></li>
    </ul>
    <table id="usershow"></table>
    <%--分页--%>
    <div id="page"></div>
</div>

<script type="text/javascript">
    $(function (){
        $('#usershow').jqGrid({
            multiselect: false,
            height:400,
            styleUI:"Bootstrap",
            autowidth:true,
            mtype:"post",
            url:"${pageContext.request.contextPath}/back/userAll",
            datatype:"json",
            colNames:["","姓名","昵称","性别","个性签名","电话","地址","状态","创建时间","图片","修改"],
            colModel:[
                {name:"id",align:"center",search:false,hidden:true},
                {name:"name",align:"center",editable:false,search:false},
                {name:"nick",align:"center",editable:false,search:false},
                {name:"sex",align:"center",editable:false,search:false},
                {name:"signature",align:"center",editable:false,search:false},
                {name:"phone",align:"center",editable:false,search:false},
                {name:"address",align:"center",editable:false,search:false},
                {name:"status",align:"center",editable:false},
                {name :'createdate',search:false,align:"center",editable:false,edittype:"date"},
                {name:"headpic",align:"center",search:false,editable:false,edittype:"file",editoptions:{enctype:"multipart/form/data"},
                    formatter:function(cellValue){
                        return "<img style='height: 80px;width: 180px;' src='"+cellValue+"'/>"
                    }},
                {name:"options",align:"center",
                    formatter:function(value,options,rowObject){
                        var id=rowObject.id;
                        return `<button type="button" class="btn btn-default">
                            <span class="glyphicon glyphicon-pencil" onclick="updateUser('`+id+`');"></span>
                            </button>`;
                    }
                },
            ],
            pager:"#page",
            rowNum:4,
            rowList:[2,4,5],
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
    
    function updateUser(id) {
        $.ajax({
            url:"${pageContext.request.contextPath}/back/upUser",
            type:"post",
            data:{id:id},
            success:function () {
                // 输出上传成功
                alert("修改成功");
                // jqgrid重新载入
                $("#usershow").trigger("reloadGrid");
            }
        });
    }
</script>