<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import ="model.ReservationTime" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import ="model.Model" %>
<%@ page import ="model.CarData" %>
<%@ page import="java.util.ArrayList" %>

<%
    // セッションから顧客情報を取得
    String customerName = (String) session.getAttribute("customerName");
    String customerId = (String) session.getAttribute("customerId");

    // リクエストから車両情報を取得
    List<ReservationTime> combinedList = (List<ReservationTime>) request.getAttribute("combinedList");
    String stationName = (String) request.getAttribute("stationName");
    String stationId = (String) request.getAttribute("stationId");

    // 他の車両情報をリクエストから取得
    List<String> carCodes = (List<String>) request.getAttribute("carCodes");
    List<String> carImages = (List<String>) request.getAttribute("carImages");
    List<String> carModels = (List<String>) request.getAttribute("carModels");
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
                // 車両ごとの情報を保持するマップを作成
                Map<String, List<ReservationTime>> carMap = new HashMap<>();

                // combinedListをループして、車両ごとに予約時間をマップに追加
                for (ReservationTime reservation : combinedList) {
                    String carCode = reservation.getCarCode(); // 車両コードを取得
                    
                    if (!carMap.containsKey(carCode)) {
                        carMap.put(carCode, new ArrayList<ReservationTime>());
                    }
                    carMap.get(carCode).add(reservation);
                }

                // 車両ごとにループして表示
                for (Map.Entry<String, List<ReservationTime>> entry : carMap.entrySet()) {
                    String carCode = entry.getKey();
                    List<ReservationTime> reservations = entry.getValue();
                    String carName = reservations.get(0).getModelName(); // 最初の予約からモデル名を取得
                    String carImg = ""; // 各車両の画像パスを設定

                    // carImagesから画像を取得
                    for (int i = 0; i < carCodes.size(); i++) {
                        if (carCodes.get(i).equals(carCode)) {
                            carImg = carImages.get(i);
                            System.out.println(carImg);
                            break;
                        }
                    }
            %>

            <div class="car-item">
                <h3><%= carName %></h3>
                <img src="img/<%= carImg %>" alt="<%= carName %>の画像" class="car-image">
                <h4>タイムテーブル</h4>
                <table>
                    <tr>
                        <th>開始時間</th>
                        <th>終了時間</th>
                        <th>状態</th>
                    </tr>
                    <%
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        for (ReservationTime reservation : reservations) {
                            String startTime = reservation.getStartDateTime();
                            String endTime = reservation.getEndDateTime();
                            String status = reservation.getStatus();
                    %>
                    <tr>
                        <td><%= startTime %></td>
                        <td><%= endTime %></td>
                        <td><%= status %></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>

            <%
                } // 車両ごとのループ終了
            %>
        </div>

        <div class="button-container">
            <button onclick="location.href='P56.jsp'">戻る</button>
        </div>
    </main>
</body>
</html>
