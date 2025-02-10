<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <link href="css/nav.css" rel="stylesheet" type="text/css">
    <title>TMC カーシェア</title>
</head>
<body>

<div class="maincontents">
    <img src="img/rog.png" alt="TMC Logo"/>
    <button onclick="location.href='P29.jsp'" class="top-right-button">予約・ログイン　▶</button>
    <h1>TMC カーシェア</h1>
    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
            <li class="nav-item gnav03"><a href="P65.jsp">予約確認・変更・取り消し</a></li>
            <li class="nav-item gnav04"><a href="P74.jsp">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>
    <h2>パスワード再設定完了</h2>
    <div class="login-container">
        <h4>パスワードの変更が完了しました</h4>
        <div class="button-container">
            <button type="submit" onclick="location.href='P76.jsp'" class="btn">登録情報確認画面に戻る</button>
        </div>
    </div>
</div>

</body>
</html>