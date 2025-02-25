<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <title>TMC カーシェア</title>
    <script>
        function validateForm(event) {
            const passwordField = document.getElementById('new-password');
            const confirmPasswordField = document.getElementById('confirm-password');
            const errorMessages = document.getElementById('errorMessages');

            errorMessages.innerHTML = ''; // エラーメッセージをリセット

            if (passwordField.value.length < 8 || passwordField.value.length > 12) {
                errorMessages.innerHTML += "※パスワードは8字以上12字以下である必要があります。<br>";
            }
            if (passwordField.value !== confirmPasswordField.value) {
                errorMessages.innerHTML += "※新規パスワードと確認用パスワードが一致しません。<br>";
            }

            if (errorMessages.innerHTML !== '') {
                event.preventDefault(); // エラーがある場合は送信を防ぐ
            }
        }
    </script>
</head>
<body>

<div class="maincontents">
    <img src="img/rog.png" alt="TMC Logo"/>
    <h1>TMC カーシェア</h1>
    <h2>パスワード再発行(入力)</h2>
    
    <div id="errorMessages" style="color: red; margin-bottom: 10px;"></div>
    
    <div class="login-container">
        <form method="post" action="PasswordServlet" onsubmit="validateForm(event);">
            <input type="hidden" name="customerId" value="${customerId}">
            <table>
                <tr>
                    <td><label for="new-password">新規パスワード入力<span style="color: red;">【必須】</span></label></td>
                    <td>
                        <input type="password" id="new-password" name="new-password" required></br>
                        <span>※半角英数字8字～12字まで</span>
                    </td>
                </tr>
                <tr>
                    <td><label for="confirm-password">新規パスワード再入力<span style="color: red;">【必須】</span></label></td>
                    <td>
                        <input type="password" id="confirm-password" name="confirm-password" required></br>
                        <span>※半角英数字8字～12字まで</span>
                    </td>
                </tr>
            </table>
            <div class="button-container">
                <button type="submit" class="btn">再設定</button>
            </div>
        </form>
    </div>
</div>

</body>
</html>
