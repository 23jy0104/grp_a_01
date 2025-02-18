<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    String customerName = (String) session.getAttribute("customerName");
    String stationIdValue = request.getParameter("stationid");
    String stationNameValue = request.getParameter("stationname"); // URLからステーション名を取得
    String stationDataValue = request.getParameter("stationdata"); // URLからステーションデータを取得

    // 現在の日時を取得
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE, 30); // 現在の時間に30分追加
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    String minStartDateTime = dateFormat.format(calendar.getTime()); // 30分後の日時をフォーマット

    // セッションに値を設定
    session.setAttribute("stationId", stationIdValue);
    session.setAttribute("stationdata", stationDataValue);
    session.setAttribute("stationName", stationNameValue);
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア - ステーション情報</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/P56.css">
    <link rel="stylesheet" href="css/timeTable.css">
</head>
<body>
    <header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <h4 id="username"><%= customerName %></h4>
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
        <h2>ステーション情報</h2>
        <button class="back-button" onclick="location.href='P55.jsp'">検索結果一覧に戻る</button>
        <h3><%= stationNameValue %></h3>
        
        <table>
            <tr>
                <th>お知らせ</th>
                <th>注意点</th>
                <th>ステーション情報</th>
                <th><%= stationDataValue %></th>
            </tr>
            <tr>
                <td>ご乗車の際は、必ずご自身で安全点検をしてください。</td>
                <td>
                    ・全車禁煙です。喫煙される場合は、クルマをとめ、喫煙設備のある場所でお願いします。<br>
                    ・車内で出た飲み物,食べ物などのゴミは、必ず各自でお持ち帰りください。
                </td>
                <td>ステーションの詳細情報がここに入ります。</td>
            </tr>
        </table>

        <div class="flex-container">
            <div class="flex-item">
                <h3>空き情報から探す</h3>
                <div class="input-container">
                    <form action="CarAvailabilityServlet" method="post">
                        <div class="datetime-input">
                            <label for="datetime1">利用開始日時:</label>
                            <input type="datetime-local" id="datetime1" name="datetime1" min="<%= minStartDateTime %>" required>
                        </div>
                        <div class="datetime-input">
                            <label for="datetime2">利用終了日時:</label>
                            <input type="datetime-local" id="datetime2" name="datetime2" min="<%= minStartDateTime %>" required>
                        </div>
                        <input type="hidden" name="stationName" value="<%= stationNameValue %>">
                        <div class="button-container">
                            <button type="submit" id="searchButton1">
                                <img src="img/kensaku.gif" alt="検索" />
                            </button>
                        </div>
                    </form>
                </div>
            </div>

<<<<<<< HEAD
            <div class="button-container">
			    <form action="ReservationCar" method="post">
			    	<input type="hidden" name="stationid" value ="<%=stationIdValue %>"
			        <input type="hidden" name="stationname" value="<%= stationNameValue %>">
			        <select id="carType" name="carType">
			            <option value="BNR32型 skyline Nismo">BNR32型 skyline Nismo</option>
			            <option value="NSX NA-1型 type-R">NSX NA-1型 type-R</option>
			            <option value="GT-R R35 Nismo Special Edition T-spec">GT-R R35 Nismo Special Edition T-spec</option>
			        </select>
			        <br><input type="image" id="searchButton2" src="img/kensaku.gif" alt="検索" />
			    </form>
			</div>
=======
            <div class="flex-item">
                <h3>車種から探す</h3>
                <select id="carType" name="carType">
                    <option value="BNR32型 skyline Nismo">BNR32型 skyline Nismo</option>
                    <option value="NSX NA-1型 type-R">NSX NA-1型 type-R</option>
                    <option value="GT-R R35 Nismo Special Edition T-spec">GT-R R35 Nismo Special Edition T-spec</option>
                </select>
>>>>>>> branch 'newmain' of https://github.com/23jy0104/grp_a_01.git

                <div class="button-container">
                    <a href="P59.jsp">
                        <input type="image" id="searchButton2" src="img/kensaku.gif" alt="検索" />
                    </a>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
