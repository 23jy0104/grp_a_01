<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <title>TMC カーシェア</title>
</head>
<body>
    <img src="img/rog.png" alt="TMC Logo"/>
    <h1>TMC カーシェア</h1>
    <div class="login-container">
        <h2>マイページログイン</h2>
        <div id="error-message" style="color: red;"></div> 
        <form action="Login" method="post">
	        <div class="o">
	            <label for="email">メールアドレス</label>
	            <input type="email" id="email" placeholder="Placeholder">
	        </div>
	        <div class="o">
	            <label for="password">パスワード</label>
	            <input type="password" id="password" placeholder="Placeholder">
	        </div>
	        <button type="button" onclick="LoginOK" class="btn">ログイン</button>
	        <div class="links">
	            <a href="P31.jsp">パスワードをお忘れの方</a>
	            <a href="P4.jsp">新規会員の方はこちら</a>
	            <a href="top.jsp">ホームページへ</a>
	        </div>
	     </form>
	  </div>
	    
</body>
</html>
