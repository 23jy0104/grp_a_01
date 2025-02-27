<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.ReservationTime" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    // セッションから顧客情報を取得
    String customerName = (String) session.getAttribute("customerName");
    String customerId = (String) session.getAttribute("customerId");

    // リクエストから車両情報を取得
    String stationName = (String) request.getAttribute("stationName");
    List<String> availableCarModels = (List<String>) request.getAttribute("availableCarModels");
    List<String> carCodes = (List<String>) request.getAttribute("carCodes");
    List<String> carImages = (List<String>) request.getAttribute("carImages");
    List<String> carModels = (List<String>) request.getAttribute("carModels");
    List<ReservationTime> combinedList = (List<ReservationTime>) request.getAttribute("combinedList"); // 予約情報と空車情報
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
            <li class="nav-item gnav04"><a href="UseHistory?customerId=<%= customerId %>&customerName=<%= customerName %>">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>

    <main>
        <h2><%= stationName %> の空いている車両一覧</h2>

        <div class="car-list">
            <%
                if (combinedList == null || combinedList.isEmpty()) {
                    out.println("<p>空いている車両はありません。</p>");
                } else {
                    for (ReservationTime reservationTime : combinedList) {
                        String status = reservationTime.getStatus(); // "予約可能" または "予約不可"
                        String startTime = reservationTime.getStartDateTime();
                        String endTime = reservationTime.getEndDateTime();
                        String carModel = reservationTime.getModelName(); // モデル名を取得

                        String carImg = ""; // 各車両の画像パスを取得する処理を追加する必要があります
                        for (int i = 0; i < carModels.size(); i++) {
                            if (carModels.get(i).equals(carModel)) {
                                carImg = carImages.get(i);
                                break;
                            }
                        }
            %>

            <div class="car-item">
                <h3><%= carModel %></h3>
                <img src="img/<%= carImg %>" alt="<%= carModel %>の画像" class="car-image">
                <h4>タイムテーブル</h4>
                <table>
                    <tr>
                        <th>開始時間</th>
                        <th>終了時間</th>
                        <th>状態</th>
                    </tr>
                    <tr>
                        <td><%= startTime %></td>
                        <td><%= endTime %></td>
                        <td><%= status %></td>
                    </tr>
                </table>
            </div>

            <%
                    } // 車両ごとのループ終了
                }
            %>
        </div>

        <div class="button-container">
            <button onclick="location.href='P56.jsp'">戻る</button>
        </div>
    </main>
</body>
</html>
