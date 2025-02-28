<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Car" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    // セッションから顧客情報を取得
    String customerName = (String) session.getAttribute("customerName");
    String customerId = (String) session.getAttribute("customerId");

    // リクエストから車両情報を取得
    String stationName = (String) request.getAttribute("stationName");
    String stationId = (String) request.getAttribute("stationId");
    String stationData = (String) request.getAttribute("stationData");

    // 車両情報リストを取得
    List<Car> availableCars = (List<Car>) request.getAttribute("availableCars");

    // jspで入力した日時
    String selectedDate = request.getParameter("selectedDate"); // 選択された日付
    String startTimeHour = request.getParameter("startTimeHour"); // 開始時間（時）
    String startTimeMinute = request.getParameter("startTimeMinute"); // 開始時間（分）

    // 開始時間をTimestampに変換
    int startHour = Integer.parseInt(startTimeHour);
    int startMinute = Integer.parseInt(startTimeMinute);
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア - 空車情報</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/P57.css">
    <style>
        .car-item {
            display: flex;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            padding: 10px;
            border-radius: 5px;
        }
        .car-image {
            width: 150px;
            height: auto;
            margin-right: 20px;
        }
        .car-details {
            flex: 1;
        }
        .time-table {
            margin-top: 10px;
            border-collapse: collapse;
            width: 100%;
        }
        .time-table th, .time-table td {
            border: 1px solid #ccc;
            padding: 5px;
            text-align: center;
        }
        .available {
            background-color: blue;
            color: white;
        }
        .unavailable {
            background-color: red;
            color: white;
        }
    </style>
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
            <li class="nav-item gnav04"><a href="UseHistory?customerId=${customerId}&customerName=${customerName}">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>

    <main>
        <h2><%= stationName %> の空いている車両一覧</h2>

        <div class="car-list">
            <% for (Car car : availableCars) { %>
                <div class="car-item">
                    <img src="<%= car.getImageUrl() %>" alt="<%= car.getCarModel() %>の画像" class="car-image">
                    <div class="car-details">
                        <h3>車種: <%= car.getCarModel() %></h3>
                        <h4><%= selectedDate %> のタイムテーブル</h4>
                        <table class="time-table">
                            <tr>
                                <th>時間</th>
                                <th>状態</th>
                            </tr>
                            <% 
                            // タイムテーブルのロジックをここに追加
                            // 例: 09:00から20:00までの時間を表示
                            for (int hour = 9; hour <= 20; hour++) {
                                String timeSlot = String.format("%02d:00", hour);
                                // ステータスを決定するロジックを追加
                                String statusClass = "available"; // 仮に全て空いているとする
                            %>
                                <tr>
                                    <td><%= timeSlot %></td>
                                    <td class="<%= statusClass %>">空いています</td>
                                </tr>
                            <% } %>
                        </table>
                    </div>
                </div>
            <% } %>
        </div>

        <div class="button-container">
              <% String detailUrl = "P56.jsp?stationid=" + stationId + "&stationname=" + stationName + "&stationdata=" + stationData; %>
              <a href="<%= detailUrl %>"> 
                  <input type="button" value="戻る">
              </a>
        </div>
    </main>
</body>
</html>
