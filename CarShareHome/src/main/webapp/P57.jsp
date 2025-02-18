<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>

<%
    List<Car> availableCars = (List<Car>) request.getAttribute("availableCars");
    String stationName = (String) request.getAttribute("stationName");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア - 空車情報</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/P57.css">
</head>
<body>
    <header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
    </header>

    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
            <li class="nav-item gnav03"><a href="P65.jsp">予約確認・変更・取り消し</a></li>
            <li class="nav-item gnav04"><a href="P74.jsp">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>

    <main>
        <h2><%= stationName %> の空いている車両一覧</h2>

        <%
            if (availableCars != null && !availableCars.isEmpty()) {
                for (Car car : availableCars) {
        %>
            <div class="car-info">
                <h3><%= car.carInfo %></h3>
                <table>
                    <tr>
                        <td>駆動方式:</td>
                        <td><%= car.driveType %></td>
                    </tr>
                    <tr>
                        <td>安全装備:</td>
                        <td><%= car.safetyFeatures %></td>
                    </tr>
                    <tr>
                        <td>備考:</td>
                        <td><%= car.notes %></td>
                    </tr>
                </table>
                <h4>空き時間</h4>
                <table>
                    <tr>
                        <th>時間帯</th>
                        <th>空き状況</th>
                    </tr>
                    <%
                    for (String time : car.availableTimes) {
                    %>
                    <tr>
                        <td><%= time %></td>
                        <td>空きあり</td>
                    </tr>
                    <%
                    }
                    for (int hour = 0; hour < 24; hour++) {
                        String timeSlot = String.format("%02d:00:00", hour);
                        if (!car.availableTimes.contains(timeSlot)) {
                    %>
                    <tr>
                        <td><%= timeSlot %></td>
                        <td>空きなし</td>
                    </tr>
                    <%
                        }
                    }
                    %>
                </table>
            </div>
        <%
                }
            } else {
        %>
            <p>空き情報はありません。</p>
        <%
            }
        %>

        <div class="button-container">
            <button onclick="location.href='P56.jsp'">戻る</button>
        </div>
    </main>
</body>
</html>
