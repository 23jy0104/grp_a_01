<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>車両管理システム</title>
    <link rel="stylesheet" href="css/vehicleTop.css">
    <style>
        .error-message {
            color: red; /* 赤文字 */
            margin-bottom: 10px; /* 入力欄とのスペース */
        }
    </style>
    <script>
        function goBack() {
            window.location.href = "k_top.jsp";
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>車両管理課</h1>

        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
            <p class="error-message"><%= message %></p>
        <%
            }
        %>

        <form action="PasswordCheckServlet" method="post">
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
