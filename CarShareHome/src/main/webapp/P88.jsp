<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="model.Customer" %>

<%	
	Customer customer = (Customer) session.getAttribute("customer");
	String customerId =(String)session.getAttribute("customerId");
	String customerName = (String) session.getAttribute("customerName");
	String customerKana =(String) session.getAttribute("customerKana");
	String email =request.getParameter("email");
%>
<!--変更後の登録情報の確認-->

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
            <li class="nav-item gnav04"><a href="UseHistory?customerId=<%= customerId %>&customerName=<%= customerName %>">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>
    <h1>メールアドレス変更の確認</h1>
    <div class="usage">
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
                <th class="howtouse">メールアドレス</th>
            </tr>
        </table>
        <p><%=email %>
        </p>
        <form action ="EmailNewUpdate" method ="post">
        	<input type ="hidden" name="customerId" value="<%=customerId %>">
        	<input type ="hidden" name="email" value="<%=email %>">
            <button class="informationchange" onclick="location.href='P76.jsp'">変更確定</button>
         </form>
            <button class="informationchange" onclick="location.href='P76.jsp'">入力画面に戻る</button>
    </div>
</html>