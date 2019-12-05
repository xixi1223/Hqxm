<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <!-- 引入 echarts.js -->
    <script src="${pageContext.request.contextPath}/echarts/echarts.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="../echarts/china.js" charset="UTF-8"></script>
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script type="text/javascript">
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('userMap'));
            var option = {
                title: {
                    text: '用户分布图',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: []
                },
                visualMap: {
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: '用户注册分布',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: []
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);

            $.ajax({
                type: "post",
                async: false, //同步执行
                url: "${pageContext.request.contextPath}/chart/address",
                dataType: "json", //返回数据形式为json
                success: function(result) {
                    myChart.hideLoading(); //隐藏加载动画
                    myChart.setOption({ //渲染数据
                        series: [{
                            // 根据名字对应到相应的系列
                            data: result
                        }]
                    });
                },
                error: function() {
                    alert("请求数据失败!");
                }
            });
        });
    </script>
</head>

<div class="col-xs-9">
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="userMap" style="width: 600px;height:400px;"></div>

</body>
</div>
</html>