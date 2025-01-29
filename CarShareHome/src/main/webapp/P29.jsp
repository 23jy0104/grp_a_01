<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <title>TMC カーシェア</title>
    <script>
        function validateForm(event) {
            const emailField = document.getElementById('email');
            const passwordField = document.getElementById('password');
            const errorDisplay = document.getElementById('error-message');
            errorDisplay.innerHTML = ''; // 前のエラーメッセージをクリア

            let hasError = false;

            // フィールドの初期化
            emailField.style.backgroundColor = '';
            passwordField.style.backgroundColor = '';

            // 未入力チェック
            if (!emailField.value || !passwordField.value) {
                errorDisplay.innerHTML = '※メールアドレスまたはパスワードを入力してください。';
                if (!emailField.value) {
                    emailField.style.backgroundColor = '#ffcccc'; // 薄い赤色
                }
                if (!passwordField.value) {
                    passwordField.style.backgroundColor = '#ffcccc'; // 薄い赤色
                }
                hasError = true;
            }

            // ここで適切な認証を行う（仮の例）
            if (!hasError && (emailField.value !== "test@example.com" || passwordField.value !== "password")) {
                errorDisplay.innerHTML = '※メールアドレスまたはパスワードが間違っています。';
                emailField.style.backgroundColor = '#ffcccc'; // 薄い赤色
                passwordField.style.backgroundColor = '#ffcccc'; // 薄い赤色
                hasError = true;
            }

            if (hasError) {
                event.preventDefault(); // フォームの送信を中止
            } else {
                // 正常な場合は次のページに遷移
                location.href = 'P53.html';
            }
        }
    </script>
</head>
<body>
    <img src="img/rog.png" alt="TMC Logo"/>
    <h1>TMC カーシェア</h1>
    <div class="login-container">
        <h2>マイページログイン</h2>
        <div id="error-message" style="color: red;"></div> 
        <div class="o">
            <label for="email">メールアドレス</label>
            <input type="email" id="email" placeholder="Placeholder">
        </div>
        <div class="o">
            <label for="password">パスワード</label>
            <input type="password" id="password" placeholder="Placeholder">
        </div>
        <button type="button" onclick="validateForm(event)" class="btn">ログイン</button>
        <div class="links">
            <a href="P31.jsp">パスワードをお忘れの方</a>
            <a href="P4.jsp">新規会員の方はこちら</a>
            <a href="top.jsp">ホームページへ</a>
        </div>
    </div>
</body>
</html>
