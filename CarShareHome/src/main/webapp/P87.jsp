<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="model.Customer" %>

<% 
String customerName = (String) session.getAttribute("customerName");
String customerKana =(String) session.getAttribute("customerKana");
String email =(String)session.getAttribute("email");

%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/check.css">
    <script>
	    function validateForm() {
	        var emailInput = document.getElementById("email").value;
	        var errorMessage = document.getElementById("error-message");
	
	        // エラーメッセージを初期化
	        errorMessage.textContent = "";
	
	        // メールアドレスの入力がない場合
	        if (emailInput.trim() === "") {
	            errorMessage.textContent = "※メールアドレスは必須です。";
	            errorMessage.style.color = "red"; // 赤色に設定
	            return false; // フォーム送信をキャンセル
	        }
	
	        // メールアドレスの形式をチェック
	        var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	        if (!emailPattern.test(emailInput)) {
	            errorMessage.textContent = "※メールアドレスの形式が不正です。";
	            errorMessage.style.color = "red"; // 赤色に設定
	            return false; // フォーム送信をキャンセル
	        }
	
	        return true; // フォーム送信を続行
	    }
	</script>

</head>
<body>
    <header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <h4 id="username"><%=customerName %>さん</h4>
        <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
    </header>
    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="./P53.jsp">予約・ステーション検索</a></li>
            <li class="nav-item gnav03"><a href="/use/">予約確認・変更・取り消し</a></li>
            <li class="nav-item gnav04"><a href="/car/">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="./P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>
    <h1>メールアドレス情報の変更</h1>
    <div class="usage">
        <form action="EmailUpdate" method="post" onsubmit="return validateForm();"> <!-- validateForm を呼び出す -->
            <table>
                <th class="howtouse">氏名</th>
            </table>
            <p><%=customerName %></p>

            <table>
                <tr>
                    <th class="howtouse">氏名フリガナ</th>
                </tr>
            </table>
            <p><%=customerKana %></p>

            <table>
                <tr>
                    <th class="howtouse">現在のメールアドレス</th>
                </tr>
                <tr>
                    <td>
                        <%= email %>
                    </td>
                </tr>
            </table>
            <table>
                <tr>
                    <th class="howtouse">変更後のメールアドレス<span style="color: red;">※必須</span></th>
                </tr>
            </table>
            <div class="form-group">
                <input type="email" id="email" name="email" placeholder="例：〇〇〇〇〇〇〇〇＠〇〇〇〇〇〇.ne.jp">
            </div>
            <div id="error-message"></div> <!-- エラーメッセージを表示するための要素 -->
            <button class="informationchange" type="submit">変更する</button>    
        </form>
        <button type="button" onclick ="location.href='P76.jsp'">前の画面に戻る</button>
    </div>
</body>
</html>
