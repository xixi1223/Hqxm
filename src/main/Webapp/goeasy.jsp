<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script charset="UTF-8" src="../echarts/echarts.min.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <title>echarts</title>
    <script>
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io',
            appkey: "BC-36e72a6f73ca455b830f7e36639c7a5f",
        });
        goEasy.subscribe({
            channel: "abcde", //替换为您自己的channel
            onMessage: function (message) {
                // 手动将 字符串类型转换为 Json类型
                var data = JSON.parse(message.content);
                alert("Channel:" + message.channel + " content:" + message.content);
            }
        });
    </script>
</head>
<body>
<h1>GoEasy!</h1>

</body>
</html>