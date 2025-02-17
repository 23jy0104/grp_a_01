<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/top.css">
    <link rel="stylesheet" href="css/price.css">
    <link rel="stylesheet" href="css/not_login.css">
    <link rel="stylesheet" href="css/P53.css">
</head>
<body>

<header>
    <img src="img/rog.png" alt="TMCロゴ">
    <h1>TMC カーシェア</h1>
    <div class="login-buttons">
        <button class="member" onclick="location.href='P4.jsp'">会員登録申請はこちら</button>
        <button class="login" onclick="location.href='P29.jsp'">予約・ログイン ▶</button>
    </div>
</header>

<nav class="nav">
    <ul>
        <li class="nav-item gnav02"><a href="P49.jsp">予約・ステーション検索</a></li>
        <li class="nav-item gnav03"><a href="P39.jsp">料金</a></li>
        <li class="nav-item gnav04"><a href="P43.">利用方法</a></li>
        <li class="nav-item gnav05"><a href="P46.jsp">車種</a></li>
    </ul>
</nav>

    <div class="column">
        <h2 class="column_title001">住所/ステーション名から探す</h2>
        <div>
            <input type="radio" id="adr" name="localSearchKbn" checked="checked" value="1" />
            <label for="adr">住所</label>
            <input type="radio" id="stationNm" name="localSearchKbn" value="2" class="ml20" />
            <label for="stationNm">ステーション名</label>
			<form action="LoginNGStaionSarch"method="post">
	            <div class="input-group">
	                <input type="text" id="nameAdr-s" name="stationAddress" placeholder="住所やステーション名を入力" style="ime-mode:active;" />
	                <input type="image" id="doNameAdrSearch" src="img/kensaku.gif" onclick="location.href='P51.jsp'" alt="検索" />
	            </div>
	            <p class="notes">例）有楽町、新宿、六本木</p>
	        </form>
        </div>
    </div>
</body>
</html>
