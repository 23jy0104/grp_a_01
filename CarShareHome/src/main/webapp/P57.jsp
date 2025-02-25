<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="model.CarData" %>

<%
    String customerName = (String) session.getAttribute("customerName");
    List<CarData> availableCars = (List<CarData>) request.getSession().getAttribute("availableCars"); // セッションから空いている車両のリストを取得
    List<String> availableTimes = (List<String>) request.getAttribute("availableTimes"); // 空き時間を取得

    // メッセージの取得
    String message = request.getParameter("message");
    if (message != null) {
        message = URLDecoder.decode(message, "UTF-8"); // URLデコード
    }

    // エラーメッセージの取得
    String errorMessage = request.getParameter("error");
    if (errorMessage != null) {
        errorMessage = URLDecoder.decode(errorMessage, "UTF-8"); // URLデコード
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
            // メッセージがある場合は表示
            if (message != null) {
        %>
            <div class="message">
                <p><%= message %></p>
            </div>
        <%
            }

            // エラーメッセージがある場合は表示
            if (errorMessage != null) {
        %>
            <div class="error-message">
                <p><%= errorMessage %></p>
            </div>
        <%
            }

            // 空いている車両がある場合の処理
            if (availableCars != null && !availableCars.isEmpty()) {
                for (CarData car : availableCars) {
        %>
                <div class="car-item">
                    <h3><%= car.getModelYear() != null ? car.getModelYear() : "車種情報がありません" %></h3>
                    <img src="img/<%= car.getCarImage() != null ? car.getCarImage() : "default.png" %>" alt="車" />
                    <table class="info-table">
                        <tr>
                            <td>駆動</td>
                            <td>4WD</td> <!-- 駆動情報は固定 -->
                        </tr>
                        <tr>
                            <td>安全装備</td>
                            <td>ドライブレコーダー, ブレーキサポート, バックモニター</td> <!-- 安全装備は固定 -->
                        </tr>
                        <tr>
                            <td>備考</td>
                            <td>ETC車載器</td> <!-- 備考は固定 -->
                        </tr>
                    </table>
                </div>
        <%
                }
            } else {
                out.print("<p>空いている車両はありません。</p>");
            }
        %>

        <div class="available-times">
            <h3>空き時間</h3>
            <%
                if (availableTimes != null && !availableTimes.isEmpty()) {
                    out.print("<ul>");
                    for (String time : availableTimes) {
                        out.print("<li>" + time + "</li>");
                    }
                    out.print("</ul>");
                } else {
                    out.print("<p>空きなし</p>");
                }
            %>
        </div>
    </main>
</body>
</html>
