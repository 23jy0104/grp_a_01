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
            event.preventDefault();
            let errorMessages = [];
            const emailField = document.getElementById('email');
            const telField = document.getElementById('tel');
            
            const inputs = [emailField, telField];
            inputs.forEach(input => {
                input.style.backgroundColor = '';
            });

            if (!emailField.value) {
                errorMessages.push("※メールアドレス入力は必須です。<br>");
                emailField.style.backgroundColor = '#ffcccc';
            }

            if (!telField.value) {
                errorMessages.push("電話番号入力は必須です。<br>");
                telField.style.backgroundColor = '#ffcccc';
            }

            const errorContainer = document.getElementById('errorMessages');
            errorContainer.innerHTML = '';
            if (errorMessages.length > 0) {
                errorContainer.innerHTML = errorMessages.join('');
                errorContainer.scrollIntoView({ behavior: 'smooth', block: 'start' });
            } else {
                document.forms[0].submit();
            }
        }
    </script>
</head>
<body>

<div class="maincontents">
    <img src="img/rog.png" alt="TMC Logo"/>
    <button onclick="location.href='P29.jsp'" class="top-right-button">予約・ログイン　▶</button>
    <h1>TMC カーシェア</h1>
    <h2>パスワード再発行(入力)</h2>
    <div id="errorMessages" style="color: red; margin-bottom: 10px;"></div>
    <% String error = request.getParameter("error"); %>
    <% if (error != null) { %>
        <div style="color: red;"><%= error %></div>
    <% } %>
    <div class="login-container">
        <form action="P31Servlet" method="post" onsubmit="validateForm(event);">
            <table>
                <tr>
                    <td><label for="email">ご登録メールアドレス<span style="color: red;">【必須】</span></label></td>
                    <td>
                        <input type="email" id="email" name="email"></br>
                        <span>※半角英数字</span>
                    </td>
                </tr>
                <tr>
                    <td><label for="name">電話番号<span style="color: red;">【必須】</span></label></td>
                    <td>
                        <input type="text" id="tel" name="tel" ></br>
                        <span>例:09012345678 ハイフンなし<span style="color: red;">※半角数字</span>
                    </td>
                </tr>
                <tr>
                    <td><label for="birthday">生年月日<span style="color: red;"></span>【必須】</label></td>
                    <td>
                        <input type="date" id="birthday" name="birthday"></br>
                    </td>
                </tr>
            </table>
            <div class="button-container">
                <button type="button" onclick="location.href='P29.jsp'" class="btn">ログイン画面に戻る</button>
                <button type="submit" class="btn">送信</button>
            </div>
        </form>
    </div>
</div>

</body>
</html>
