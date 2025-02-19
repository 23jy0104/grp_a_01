<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.CarInfo"%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>車両情報の確認</title>
    <link rel="stylesheet" href="css/vehicleCon.css">  
</head>
<body>

    <h1>車両情報の確認</h1>

    <table border="1">
        <tr>
            <th>メーカー名</th>
            <th>ナンバープレート</th>
            <th>モデル名</th>
            <th>年式</th>
        </tr>
        <%
            List<CarInfo> carInfoList = (List<CarInfo>) request.getAttribute("carInfoList");
            if (carInfoList != null && !carInfoList.isEmpty()) {
                for (CarInfo carInfo : carInfoList) {
        %>
        <tr>
            <td><%= carInfo.getMakerName() %></td>
            <td><%= carInfo.getNumber() %></td>
            <td><%= carInfo.getModelName() %></td>
            <td><%= carInfo.getModelYear() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="4">データがありません</td>
        </tr>
        <%
            }
        %>
    </table>

    <div class="button-container">
        <button onclick="location.href='k_P7Servlet?stationName=<%= station != null ? station.getStationName() : "" %>&stationAddress=<%= station != null ? station.getStationAddress() : "" %>'">戻る</button>
        <button onclick="location.href='nextStep.jsp'">次のステップ</button>
    </div>

</body>
</html>
