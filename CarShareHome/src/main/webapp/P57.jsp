<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="model.CarData" %>
<%@ page import="model.ReservationData" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Calendar" %>

<%
    String customerName = (String) session.getAttribute("customerName");
    List<CarData> availableCars = (List<CarData>) session.getAttribute("availableCars");
    List<ReservationData> reservations = (List<ReservationData>) session.getAttribute("reservations");
    List<String> availableTimes = (List<String>) session.getAttribute("availableTimes");

    // P56から送信された開始時間と終了時間を取得
    Date startTime = (Date) session.getAttribute("startTime");
    Date endTime = (Date) session.getAttribute("endTime");

    // エラーメッセージの確認
    String errorMessage = null;
    if (startTime == null || endTime == null) {
        errorMessage = "開始時間または終了時間が設定されていません。";
    }

    // メッセージの取得
    String message = request.getParameter("message");
    if (message != null) {
        message = URLDecoder.decode(message, "UTF-8");
    }
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>TMC カーシェア - 車両情報</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/P57.css">
</head>
<body>
    <header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <h4 id="username"><%= customerName %>さん</h4>
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
        <h2>空いている車両情報</h2>

        <% 
            // エラーメッセージがある場合は表示
            if (errorMessage != null) {
        %>
            <div class="error-message">
                <p><%= errorMessage %></p>
            </div>
        <%
            } else {
                // 空いている車両がある場合の処理
                if (availableCars != null && !availableCars.isEmpty()) {
                    for (CarData car : availableCars) {
                        boolean isReserved = false; // 予約があるかどうかのフラグ
        %>
                        <div class="car-item">
                            <h3><%= car.getModelName() != null ? car.getModelName() : "車種情報がありません" %></h3>
                            <img src="img/<%= car.getCarImage() != null ? car.getCarImage() : "default.png" %>" alt="車" />
                            <table class="info-table">
                                <tr>
                                    <td>駆動</td>
                                    <td>4WD</td>
                                </tr>
                                <tr>
                                    <td>安全装備</td>
                                    <td>ドライブレコーダー, ブレーキサポート, バックモニター</td>
                                </tr>
                                <tr>
                                    <td>備考</td>
                                    <td>ETC車載器</td>
                                </tr>
                            </table>

                            <h4>予約状況</h4>
                            <table border="1" style="width: 100%; border-collapse: collapse;">
                                <thead>
                                    <tr>
                                        <th>時間</th>
                                        <th>予約状況</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <%
                                    // 15分単位のタイムテーブルを生成
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(startTime);
                                    Date currentTime = startTime;

                                    // 15分単位でテーブルを生成
                                    while (currentTime.before(endTime)) {
                                        String timeSlot = String.format("%tH:%<tM", currentTime);
                                        boolean isTimeReserved = false;

                                        // 予約状況の確認
                                        for (ReservationData reservation : reservations) {
                                            if (reservation.getCarCode().equals(car.getCarCode())) {
                                                // 予約の開始時間と終了時間を取得
                                                Date reservationStart = reservation.getStartDate();
                                                Date reservationEnd = reservation.getStopDate();

                                                // 15分単位のスロットが予約に含まれるか確認
                                                if (currentTime.after(reservationStart) && currentTime.before(reservationEnd)) {
                                                    isTimeReserved = true;
                                                    break;
                                                }
                                            }
                                        }
                                %>
                                    <tr>
                                        <td><%= timeSlot %></td>
                                        <td><%= isTimeReserved ? "予約あり" : "空き" %></td>
                                    </tr>
                                <%
                                        // 次の15分へ進める
                                        calendar.add(Calendar.MINUTE, 15);
                                        currentTime = calendar.getTime();
                                    }
                                %>
                                </tbody>
                            </table>
                        </div>
        <%
                    }
                } else {
                    out.print("<p>空いている車両はありません。</p>");
                }
            }
        %>

        <h3>空き時間</h3>
        <table border="1" style="width: 100%; border-collapse: collapse;">
            <thead>
                <tr>
                    <th>空き時間</th>
                </tr>
            </thead>
            <tbody>
            <%
                if (availableTimes != null && !availableTimes.isEmpty()) {
                    for (String time : availableTimes) {
            %>
                <tr>
                    <td><%= time %></td>
                </tr>
            <%
                    }
                } else {
                    out.print("<tr><td>空きなし</td></tr>");
                }
            %>
            </tbody>
        </table>
    </main>
</body>
</html>
