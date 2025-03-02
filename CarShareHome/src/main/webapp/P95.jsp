<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 
 String customerId =(String)session.getAttribute("customerId");
 String customerName=(String)session.getAttribute("customerName");


%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="css/nav.css">
    <title>TMC カーシェア</title>
</head>
<body>
	<header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <h4 id="username"><%= customerName %>さん</h4>        
        <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
    </header>
    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
			 <li class="nav-item gnav03"><a href="UserReservation?customerId=<%= customerId%>&customerName=<%=customerName%>">予約確認・変更・取り消し</a></li>
			 <li class="nav-item gnav04"><a href="UseHistory?customerId=${customerId}&customerName=${customerName}">ご利用履歴</a></li>
             <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>
    <script>
        function validateForm(event) {
            event.preventDefault();
            let errorMessages = [];
            const passwordField = document.getElementById('password');
            const newPasswordField = document.getElementById('new-password');
            const confirmPasswordField = document.getElementById('confirm-password');
            
            const inputs = [passwordField, newPasswordField, confirmPasswordField];
            inputs.forEach(input => {
                input.style.backgroundColor = '';
            });

            if (!passwordField.value) {
                errorMessages.push("※現在のパスワード入力は必須です。<br>");
                passwordField.style.backgroundColor = '#ffcccc';
            }

            if (!newPasswordField.value) {
                errorMessages.push("※新規パスワード入力は必須です。<br>");
                newPasswordField.style.backgroundColor = '#ffcccc';
            }

            if (!confirmPasswordField.value) {
                errorMessages.push("※新規パスワード再入力は必須です。<br>");
                confirmPasswordField.style.backgroundColor = '#ffcccc';
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
	<div class="maincontents">
	    <h2>パスワード再発行(入力)</h2>
	    <div id="errorMessages" style="color: red; margin-bottom: 10px;"></div>
	    
	    <% String error = request.getParameter("error"); %>
	    <% if (error != null) { %>
	        <div style="color: red;"><%= error %></div>
	    <% } %>
	    
	    <div class="login-container">
	        <form method="post" action="P95" onsubmit="validateForm(event);">
	            <table>
	                <tr>
	                    <td><label for="password">現在のパスワード入力<span style="color: red;">【必須】</span></label></td>
	                    <td>
	                        <input type="password" id="password" name="password"></br>
	                        <span>※半角数字8字～12字まで</span>
	                    </td>
	                </tr>
	                <tr>
	                    <td><label for="new-password">新規パスワード入力<span style="color: red;">【必須】</span></label></td>
	                    <td>
	                        <input type="password" id="new-password" name="new-password" ></br>
	                        <span>※半角数字8字～12字まで</span>
	                    </td>
	                </tr>
	                <tr>
	                    <td><label for="confirm-password">新規パスワード再入力<span style="color: red;">【必須】</span></label></td>
	                    <td>
	                        <input type="password" id="confirm-password" name="confirm-password"></br>
	                        <span>※半角数字8字～12字まで</span>
	                    </td>
	                </tr>
	            </table>
	            <input type="submit" value="再設定">
	            
	            
	        </form>
	    </div>
</div>
</body>
</html>
