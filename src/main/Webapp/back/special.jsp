<%@ page pageEncoding="UTF-8" contentType="text/html;UTF-8" %>
<div class="col-xs-9">
    <div class="page-header">
        <h2>专辑章节管理</h2>
    </div>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">专辑章节信息</a></li>
    </ul>
    <table id="special"></table>
    <%--分页--%>
    <div id="page"></div>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <audio src="" id="myaudio" controls></audio>
    </div>
</div>

<script>
    $(function () {
        // 创建父级JqGrid表格
        $("#special").jqGrid(
            {
                url :"${pageContext.request.contextPath}/back/AllSpecial",
                datatype : "json",
                height : 500,
                colNames : [ 'id', '标题','封面','作者','播音','章节数','简介','发布时间','星级' ],
                colModel : [
                    {name : 'id',hidden:true},
                    {name : 'title',align:"center",search:true,editable:true,},
                    {name:"cover",align:"center",search:false,editable:true,edittype:"file",editoptions:{enctype:"multipart/form/data"},
                        formatter:function(cellValue){
                            return "<img style='height: 80px;width: 180px;' src='"+cellValue+"'/>"
                        }},
                    {name : 'author',search:true,align:"center",editable:true,},
                    {name : 'beam',search:true,align:"center",editable:true,},
                    {name : 'number',search:false,editable:true,},
                    {name : 'intro',search:true,align:"center",editable:true,},
                    {name : 'createdate',search:false,align:"center",editable:true,edittype:"date"},
                    {name : 'start',search:false,align:"center",editable:true,},
                ],
                rowNum : 2,
                rowList : [ 2, 5, 10 ],
                pager : '#page',
                sortname : 'id',
                viewrecords : true,
                sortorder : "desc",
                multiselect : false,
                // 开启多级表格支持
                subGrid : true,
                autowidth:true,
                styleUI:"Bootstrap",
                editurl:"${pageContext.request.contextPath}/back/editSpecial",
                // 重写创建子表格方法
                subGridRowExpanded : function(subgrid_id, row_id) {
                    addTable(subgrid_id,row_id);
                },
                // 删除表格方法
                subGridRowColapsed : function(subgrid_id, row_id) {

                }
            });
        $("#special").jqGrid(
            'navGrid',
            '#page',
            {add:true,edit:true,del:true,search:true,refresh:true},
            //编辑功能
            {closeAfterEdit:true,editCaption:"编辑图片",reloadAfterSubmit:true,
                afterSubmit:function (response,postData) {
                    var slideshowID = response.responseJSON.specialId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/back/updateSpe",
                        datatype:"json",
                        type:"post",
                        data:{specialId:slideshowID},
                        fileElementId:"cover",
                        success:function (data) {
                            // 输出上传成功
                            alert("更新成功");
                            // jqgrid重新载入
                            $("#special").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },
            //添加功能
            {closeAfterAdd:true,addCaption:"图片添加",reloadAfterSubmit:true,
                afterSubmit:function (response,postData) {
                    var slideshowID = response.responseJSON.specialId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/back/uploadSpecial",
                        datatype:"json",
                        type:"post",
                        data:{specialId:slideshowID},
                        // 指定的上传input框的id
                        fileElementId:"cover",
                        success:function (data) {
                            // 输出上传成功
                            alert("添加成功");
                            // jqgrid重新载入
                            $("#special").trigger("reloadGrid");
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
    })
    // subgrid_id 下方空间的id  row_id 当前行id数据
    function addTable(subgrid_id,row_id) {

        // 声明子表格|工具栏id
        var subgridTable = subgrid_id + "table";
        var subgridPage = subgrid_id + "page";
        // 根据下方空间id 创建表格及工具栏
        $("#"+subgrid_id).html("<table id='"+subgridTable+"'></table><div style='height: 50px' id='"+subgridPage+"'></div>")
        // 子表格JqGrid声明
        $("#"+subgridTable).jqGrid({
            url : "${pageContext.request.contextPath}/back/AllChapter?Sid="+row_id,
            datatype : "json",
            colNames : [ 'id', '章节','大小','时长','路径','操作' ],
            colModel : [
                {name : "id",hidden:true},
                {name : "name",editable:true},
                {name : "sizez"},
                {name : "duration"},
                {name:"href",align:"center",editable:true,edittype:"file",editoptions:{enctype:"multpart/form-data"},
                formatter:function (value) {
                    let names=value.split("_");
                    return names[names.length-1];
                }
                },
                {name : "href",align:"center",formatter:function (data) {
                    var result="";
                        result += "<a href='javascript:void(0)' onclick=\"playAudio('" + data + "')\" class='btn btn-lg' title='播放'><span class='glyphicon glyphicon-play-circle'></span></a>";
                    result += "<a href='javascript:void(0)' onclick=\"downloadAudio('" + data + "')\" class='btn btn-lg' title='下载'><span class='glyphicon glyphicon-download'></span></a>";
                    return result;
                    }
                },
            ],
            rowNum : 10,
            pager : "#"+subgridPage,
            sortname : 'num',
            sortorder : "asc",
            height : '100%',
            styleUI:"Bootstrap",
            editurl:"${pageContext.request.contextPath}/back/editChapter?Sid="+row_id,
            autowidth:true
        });
        $("#" + subgridTable).jqGrid(
            'navGrid', "#" + subgridPage,
            {edit : true, add : true, del : true, search:false},
            //编辑功能
            {closeAfterEdit:true,editCaption:"编辑图片",reloadAfterSubmit:true,
                afterSubmit:function (response,postData) {
                    var chapterID = response.responseJSON.chapterId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/back/updateChe",
                        datatype:"json",
                        type:"post",
                        data:{chapterId:chapterID},
                        fileElementId:"href",
                        success:function (data) {
                            // 输出上传成功
                            alert("更新成功");
                            // jqgrid重新载入
                            $("#special").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },
            //添加功能
            {closeAfterAdd:true,addCaption:"图片添加",reloadAfterSubmit:true,
                afterSubmit:function (response,postData) {
                    var chapterID = response.responseJSON.chapterId;

                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/back/addChapter",
                        datatype:"json",
                        type:"post",
                        data:{chapterId:chapterID},
                        // 指定的上传input框的id
                        fileElementId:"href",
                        success:function () {
                            // 输出上传成功
                            alert("添加成功");
                            // jqgrid重新载入
                            $("#subgridTable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },
            {},//删除功能
            {}//搜索功能
        );
    }

    function playAudio(data) {
        $("#myModal").modal("show");
        $("#myaudio").attr("src",data);
    }
    function downloadAudio(data) {
        location.href = "${pageContext.request.contextPath}/back/download?href="+data;
    }

</script>

