<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="model.Customer" %>
<%
    String customerName = (String) session.getAttribute("customerName");
	String customerId =(String)session.getAttribute("customerId");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア - 住所/ステーション名から探す</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/P53.css">
</head>
<body>
    <header>
        <img src="img/rog.png" href="home.html" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <h4 id="username"><%=customerName%>さん</h4>
        <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
    </header>
    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
            <li class="nav-item gnav03"><a href="P65.jsp">予約確認・変更・取り消し</a></li>
            <li class="nav-item gnav04"><a href="UseHistory?customerName=<%= customerName %>">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>

    <div class="column">
        <h2 class="column_title001">住所/ステーション名から探す</h2>
        <div>
            <input type="radio" id="adr" name="localSearchKbn" checked="checked" value="1" />
            <label for="adr">住所</label>
            <input type="radio" id="stationNm" name="localSearchKbn" value="2" class="ml20" />
            <label for="stationNm">ステーション名</label>
			<form action="LoginOKStationSearch"method="post">
	            <div class="input-group">
	                <input type="text" id="nameAdr-s" name="stationAddress" placeholder="住所やステーション名を入力" style="ime-mode:active;" />
	                <input type="image" id="doNameAdrSearch" src="img/kensaku.gif"  alt="検索" />
	            </div>
	            <p class="notes">例）有楽町、新宿、六本木</p>
	        </form>
        </div>
    </div>
</body>
</html>
