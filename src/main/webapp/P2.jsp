<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--車両管理ログインページ-->

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>車両管理システム</title>
    <link rel="stylesheet" href="css/vehicleTop.css">
    <script>
        function signIn() {
            window.location="P4.html";
        }

        function goBack() {
            window.location.href = "top.jsp"; // kanri.htmlに遷移
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>車両管理課</h1>
        <input type="text" placeholder="ID" required>
        <input type="password" placeholder="パスワード" required>
        <div class="button-container">
            <button class="go-back" onclick="goBack()">戻る</button>
            <button onclick="signIn()">サインイン</button>
        </div>
    </div>
</body>
</html>