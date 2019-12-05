<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;UTF-8" %>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '用户注册信息'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["1天","7天","30天","1年"]
        },
        yAxis: {},
        series: [],
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    // Ajax异步数据回显
    $.get("${pageContext.request.contextPath}/chart/selectSex",function (data) {
        myChart.setOption({
            series:[
                {
                    name: '男',
                    type: 'bar',
                    data: data.man,
                },{
                    name: '女',
                    type: 'line',
                    data: data.woman,
                }
            ]
        })
    },"json")
</script>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="col-xs-9">
    <div id="main" style="width: 600px;height:400px;"></div>
</div>