<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/style.css" rel="stylesheet" type="text/css"> <!-- CSSファイルを正しくリンク -->
    <title>TMC カーシェア</title>
    <script>
        function validateForm() {
            var email = document.getElementById("email").value;
            var password = document.getElementById("customerpassword").value; // IDを修正
            var errorMessage = document.getElementById("error-message");

            errorMessage.textContent = ""; // エラーメッセージをクリア

            if (!email || !password) {
                errorMessage.textContent = "※メールアドレスとパスワードを入力してください。";
                return false; // フォーム送信をキャンセル
            }
            return true; // フォーム送信を許可
        }
    </script>
    <style>
    	input[type="submit"] {
		    background-color: yellow;
		    border: 2px solid black;
		    padding: 10px 15px;
		    border-radius: 0;
		    cursor: pointer;
			    width: 200px;
		}
	
		input[type="submit"]:hover {
		    background-color: #e0e0e0;
		}
		.error {
		    color: red; /* 赤字に設定 */
		    font-weight: bold; /* 太字に設定 */
		}
    </style>
</head>
<body>
    <img src="img/rog.png" alt="TMC Logo"/>
    <h1>TMC カーシェア</h1>
    <div class="login-container">
        <h2>マイページログイン</h2>
        <div id="error-message" class="error"></div> <!-- エラーメッセージ -->
        <%
        String loginError = (String) request.getAttribute("loginfalse");
        if (loginError != null) {
    %>
        <p class="error"><%= loginError %></p> <!-- エラーメッセージを赤字で表示 -->
    <%
        }
    %>
        <form action="Login" method="post" onsubmit="return validateForm();"> <!-- ここでバリデーション -->
          <div class="o">
              <label for="email">メールアドレス</label>
              <input type="email" id="email" name="email" placeholder="Placeholder">
          </div>
          <div class="o">
              <label for="customerpassword">パスワード</label>
              <input type="password" id="customerpassword" name="customerpassword" placeholder="Placeholder">
          </div>
          <input type="submit" value="ログイン"> <!-- スタイルが適用されるボタン -->
      </form>
      <div class="links">
          <a href="P31.jsp">パスワードをお忘れの方</a>
          <a href="P4.jsp">新規会員の方はこちら</a>
          <a href="top.jsp">ホームページへ</a>
      </div>
   </div>
</body>
</html>
