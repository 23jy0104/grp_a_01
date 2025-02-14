<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="model.Customer" %>
<%@ page import ="model.Reservation" %>
<%@ page import ="java.util.List" %>
<% 
    String customerId = (String) request.getAttribute("customerId");
    String customerName = (String) request.getAttribute("customerName");
    List<Reservation> usedReservations = (List<Reservation>) request.getAttribute("usedReservations");
    
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア - ご利用履歴</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/history.css">
    <style>
        .information th {
            background-color: #007BFF;
            color: white;
            padding: 10px;
            text-align: left;
        }

        .information td {
            padding: 10px;
            border: 1px solid #ddd;
        }

        .information tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .information tr:hover {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>
    <header>
        <img src="img/rog.png" href="home.html" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <h4 id="username"><%= customerName %>さん</h4>
        <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
    </header>
    
    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
            <li class="nav-item gnav03"><a href="P65.jsp">予約確認・変更・取り消し</a></li>
            <li class="nav-item gnav04"><a href="UseHistory?customerId=<%= customerId %>&customerName=<%= customerName %>">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="">ご登録情報の確認</a></li>
        </ul>
    </nav>

    <div class="history">
        <h2>ご利用履歴</h2>
    </div>

    <div class="table-container">
        <table class="information">
            <thead>
                <tr>
                    <th>会員名</th>
                    <th>利用開始時間</th>
                    <th>利用終了時間</th>
                    <th>ステーション</th>
                    <th>利用料金</th>
                </tr>
            </thead>
            <tbody>
    <%
        if (usedReservations == null || usedReservations.isEmpty()) {
    %>
                <tr>
                    <td colspan="5">過去の利用履歴はありません。</td>
                </tr>
    <%
        } else {
            for (Reservation reservation : usedReservations) {
    %>
                <tr>
                    <td><%= reservation.getCustomer().getCustomerName() %></td>
                    <td><%= reservation.getStartDate() %></td>
                    <td><%= reservation.getFinishDate() %></td>
                    <td><%= reservation.getStation().getStationName() %></td>
                    <td><%= reservation.getPrice() %> 円</td>
                </tr>
    <%
            }
        }
    %>
            </tbody>
        </table>
    </div>
</body>
</html>
