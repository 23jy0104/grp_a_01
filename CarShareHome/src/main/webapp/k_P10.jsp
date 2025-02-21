<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>車両登録</title>
    <link rel="stylesheet" href="css/vehicleCon.css">  
</head>
<body>
    <h1>車両登録情報</h1>

    <% 
        String selectedPlate = request.getParameter("selectedPlate");
        String stationName = request.getParameter("stationName");
        String stationAddress = request.getParameter("stationAddress");
        String makerName = request.getParameter("makerName");
        String modelName = request.getParameter("modelName");
        String modelYear = request.getParameter("modelYear");
    %>
    <h1><%= stationName != null ? stationName : "ステーション名がありません" %>ステーション　車両登録情報</h1>
    <table>
        <tr>
            <td>車両メーカー</td>
            <td><%= makerName != null ? makerName : "未設定" %></td>
        </tr>
        <tr>
            <td>車両モデル</td>
            <td><%= modelName != null ? modelName : "未設定" %></td>
        </tr>
        <tr>
            <td>年式</td>
            <td><%= modelYear != null ? modelYear : "未設定" %>年</td>
        </tr>
        <tr>
            <td>ナンバープレート</td>
            <td><%= selectedPlate != null ? selectedPlate : "未設定" %></td>
        </tr>
    </table>

    <div class="button-container">
        <button onclick="document.location.href='k_P4.jsp'">検索画面に戻る</button>
        <button onclick="document.location.href='k_P7Servlet?stationName=<%= stationName != null ? stationName : "" %>&stationAddress=<%= stationAddress != null ? stationAddress : "" %>'">続けて登録</button>
    </div>
</body>
</html>