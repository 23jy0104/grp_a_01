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

        // エラーがない場合はサーブレットにデータを送信
        if (!hasError) {
            event.preventDefault(); // フォームの送信を中止

            const email = emailField.value;
            const password = passwordField.value;

            // サーブレットにデータを送信
            fetch('LoginDataBase', { // サーブレットのパスを確認
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password }),
            })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    // ログイン成功時の処理
                    location.href = 'P53.html'; // 次のページに遷移
                } else {
                    // ログイン失敗時の処理
                    errorDisplay.innerHTML = '※メールアドレスまたはパスワードが間違っています。';
                    emailField.style.backgroundColor = '#ffcccc'; // 薄い赤色
                    passwordField.style.backgroundColor = '#ffcccc'; // 薄い赤色
                }
            })
            .catch(error => {
                console.error('Error:', error);
                errorDisplay.innerHTML = '※エラーが発生しました。';
            });
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
