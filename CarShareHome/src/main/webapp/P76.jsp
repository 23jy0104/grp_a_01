<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="model.Customer" %>
<%@ page import="java.util.Date" %>
<%
    String customerName = (String) session.getAttribute("customerName");
	String customerKana =(String) session.getAttribute("customerKana");
	String email =(String)session.getAttribute("email");
	String tellNumber =(String)session.getAttribute("tellNumber");
	String postCode =(String)session.getAttribute("postCode");
	String customerAddress =(String)session.getAttribute("customerAddress");
	Date licenseDate =(Date)session.getAttribute("licenseDate");
%>
<!--登録情報変更入力画面-->

<!--登録情報の確認-->

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/check.css">
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
            <li class="nav-item gnav04"><a href="P74.jsp">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>
    <h1>登録情報の確認</h1>
    <div class="usage">
        <table>
            <tr>
                <th class="howtouse">決済方法</th>
            </tr>
        </table>
        <p>クレジット
            <button class="change" onclick="location.href='P88.jsp'" style="float: right;">変更する</button>
        </p>
        
        <table>
            <th class="howtouse">氏名</th>
            </tr>
        </table>
        <p><%=customerName %></p>
        <table>
            <tr>
                <th class="howtouse">氏名フリガナ</th>
            </tr>
        </table>
        <p><%=customerKana %></p>
        <table>
            <tr>
                <th class="howtouse">住所</th>
            </tr>
        </table>
        <p><%=postCode %>
            <br><%=customerAddress %>
        </p>
        <table>
            <tr>
                <th class="howtouse">電話番号(携帯)</th>
            </tr>
        </table>
        <p><%=tellNumber %></p>
        <table>
            <tr>
                <th class="howtouse">運転免許証有効期限</th>
            </tr>
        </table>
        <p><%=licenseDate %></p>
        <table>
            <tr>
                <th class="howtouse">安心補償サービス</th>
            </tr>
        </table>
            <p>自動加入</p>
            <table>
                <tr>
                    <th class="howtouse">ご登録メールアドレス</th>
                </tr>
            </table>
            <p><%=email %></p>
            <button class="informationchange" onclick="location.href='UC07_01.html'">会員情報の変更</button>    
    </div>
    <h3>各種手続き</h3>
    <a href="P95.html">パスワード変更</a><br>
    <a href="top.html">ログアウト</a>
</body>
</html>