<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>システム管理課</title>
    <link rel="stylesheet" href="css/vehicleTop.css">
    <style>
        .error-message {
            color: red; /* 赤文字 */
            margin-bottom: 10px; /* 入力欄とのスペース */
        }
    </style>
    <script>
        function signIn() {
            document.getElementById('loginForm').submit(); // フォームを送信
        }

        function goBack() {
            window.location.href = "k_top.jsp"; // 戻るボタンの遷移先
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>システム管理課</h1>

        <%-- エラーメッセージの表示 --%>
        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
            <p class="error-message"><%= message %></p>
        <%
            }
        %>

        <form id="loginForm" action="PasswordCheckServlet" method="post">
            <input type="text" name="userId" placeholder="ID" required>
            <input type="password" name="password" placeholder="パスワード" required>
            <input type="hidden" name="currentPath" value="<%= request.getRequestURI() %>"> <!-- 現在のパスを隠しフィールドに設定 -->
            <div class="button-container">
                <button type="button" class="go-back" onclick="goBack()">戻る</button>
                <button type="submit">サインイン</button>
            </div>
        </form>
    </div>
</body>
</html>