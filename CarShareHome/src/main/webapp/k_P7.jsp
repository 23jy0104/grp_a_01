<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ステーション詳細</title>
</head>
<body>

    <h1>ステーション詳細</h1>
    <div>
        <%
            String stationName = request.getParameter("stationName");
            String stationAddress = request.getParameter("stationAddress");

            if (stationName != null && stationAddress != null) {
        %>
        <p>ステーション名: <%= stationName %></p>
        <p>住所: <%= stationAddress %></p>
        <%
            } else {
        %>
        <p>ステーション情報が見つかりません。</p>
        <%
            }
        %>
    </div>

    <button onclick="location.href='k_P4.jsp'">戻る</button>

</body>
</html>
