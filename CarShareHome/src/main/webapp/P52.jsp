<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%	
	List<String[]> stations = (List<String[]>) session.getAttribute("stations"); // ステーション情報を取得
	String stationNameValue = request.getParameter("stationname"); // URLからステーション名を取得
	String stationDataValue = request.getParameter("stationdata"); // URLからステーションデータを取得
	
	// セッションに値を設定
	session.setAttribute("stationdata", stationDataValue);
	session.setAttribute("stationName", stationNameValue);
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/top.css">
    <link rel="stylesheet" href="css/P56.css"> <!-- P56.cssを追加 -->
    <link rel="stylesheet" href="css/P52.css">
</head>
<body>
    <header>
    <img src="img/rog.png" alt="TMCロゴ">
    <h1>TMC カーシェア</h1>
    <div class="login-buttons">
        <button class="member" onclick="location.href='P4.jsp'" >会員登録申請はこちら</button>
        <button class="login" onclick="location.href='P29.jsp'">予約・ログイン ▶</button>
    </div>
</header>

<nav class="nav">
    <ul>
        <li class="nav-item"><a href="P49.jsp">ステーション検索</a></li>
        <li class="nav-item"><a href="P40.jsp">料金</a></li>
        <li class="nav-item"><a href="P43.jsp">利用方法</a></li>
        <li class="nav-item active"><a href="P46.jsp">車種</a></li>
    </ul>
</nav>
    <main>
        <h2><%=stationName %></h2>
        <button class="back-button" onclick="location.href='P49.jsp'">検索結果一覧に戻る</button>
        <table>
            <tr>
                <th>お知らせ</th>
                <th>注意点:<br>
                    　・ご乗車の際は、必ずご自身で安全点検をしてください。<br>
                    　・全車禁煙です。喫煙される場合は、クルマをとめ、喫煙設備のある場所でお願いします。<br>
                    　・車内で出た飲み物,食べ物などのゴミは、必ず各自でお持ち帰りください。<br><br>
                    アクセス:<br>
                    　大久保駅北口から北西に進んでいただき、大久保通りをまっすぐ進んだ場所にございます。
                </th>
                <th><%=stationDataValue %></th>
            </tr>
        </table>
        <button class="login-button" onclick="location.href='P29.jsp'">ログインして予約</button>
    </main>
</body>
</html>
