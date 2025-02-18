<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@ page import="javax.servlet.http.HttpSession" %>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html; charset=UTF-8");
    // セッションからデータを取得
    String customerName = request.getParameter("customerName");
<<<<<<< HEAD
    String customerKana = (String) session.getAttribute("customerNameKana");
    String gender = (String) session.getAttribute("gender");
    String email = (String) session.getAttribute("email");
    String tellNumber = (String) session.getAttribute("tellNumber");
    String customerAddress = (String) session.getAttribute("customerAddress");
    String licenseNumber = (String) session.getAttribute("licenseNumber");
   	String licenseDate = (String) session.getAttribute("licenseDate");
    String birthDate = (String) session.getAttribute("birthDate"); // 生年月日も取得
    String hashedPassword = (String) session.getAttribute("hashedPassword");
	System.out.println(customerName);
=======
    String customerKana = request.getParameter("customerNameKana");
    String gender =request.getParameter("gender");
    String email = request.getParameter("email");
    String postcode =request.getParameter("postcode");
    String tellNumber =request.getParameter("tellNumber");
    String customerAddress = request.getParameter("customerAddress");
    String licenseNumber =request.getParameter("licenseNumber");
   	String licenseDate = request.getParameter("licenseDate");
    String birthDate = request.getParameter("birthDate"); // 生年月日も取得
    String password = request.getParameter("password");

>>>>>>> branch 'main' of https://github.com/23jy0104/grp_a_01.git
%>
<!-- クレジットカード情報入力ページ -->
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/credit.css">
</head>
<body>

    <header>
        <div class="logo">
            <img src="img/rog.png" alt="TMC Logo">
            <h1>TMC カーシェア</h1>
        </div>
    </header>

    <form action="CreditNew" method="post">
        <input type="hidden" name="customerName" value="<%= customerName %>">
        <input type="hidden" name="customerKana" value="<%= customerKana %>">
        <input type="hidden" name="gender" value="<%= gender %>">
        <input type="hidden" name="email" value="<%= email %>">
        <input type="hidden" name ="postcode" value ="<%=postcode %>">
        <input type ="hidden" name="password" value ="<%=password %>">
        <input type="hidden" name="tellNumber" value="<%= tellNumber %>">
        <input type="hidden" name="customerAddress" value="<%= customerAddress %>">
        <input type="hidden" name="licenseNumber" value="<%= licenseNumber %>">
        <input type="hidden" name="licenseDate" value="<%= licenseDate %>">
        <input type="hidden" name="birthDate" value="<%= birthDate %>"> <!-- 生年月日を隠しフィールドとして追加 -->
        <input type="hidden" name="hashedPassword" value="<%= password %>"> <!-- ハッシュ化されたパスワードを保持 -->

        <div class="form-group">
            <label for="credit_number">
                <span class="required">必須</span>クレジットカード番号<span class="highlight"> ※半角数字、ハイフンなし</span>
            </label>
            <input type="text" id="credit_number" name="credit_number" placeholder="例:1234567891234567" required><br><br>
            <span class="highlight2"> 注意:ご登録は、お申込ご本人名義のクレジットカードに限ります。</span><br>
            VISA・JCB・AMEX・MASTER・DINERS・EPOSの6ブランドがご利用いただけます。

            <label for="credit_date">
                <span class="required">必須</span>有効期限
            </label>
            <input type="month" name="credittime" required><br>

            <label for="security">
                <span class="required">必須</span>セキュリティコード<span class="highlight"> ※半角数字</span>
            </label>
            <input type="number" id="security" name="security" placeholder="例:012" required>

            <div class="warning">
                <input type="checkbox" id="myCheckbox" onclick="toggleButton()">入力内容をご確認いただき、誤りがない場合はチェックをつけてください。<br>
                ※チェックが入っていない場合、変更を確定できません。
            </div>

            <div class="btn container">
                <input type="button" id="modoru" value="ご登録情報の確認に戻る" onclick="location.href='P20.jsp'">
                <button id="confirmButton" class="confirmbutton" disabled type="submit">確定</button>
            </div>
        </div>
    </form>

    <div id="errorContainer" class="error-message"></div> <!-- エラーメッセージ表示用 -->

    <script>
        function toggleButton() {
            const checkbox = document.getElementById('myCheckbox');
            const button = document.getElementById('confirmButton');
            button.disabled = !checkbox.checked;

            // チェックが入っている場合、オレンジ色のスタイルを適用
            if (checkbox.checked) {
                button.style.backgroundColor = 'orange';
            } else {
                button.style.backgroundColor = ''; // デフォルトの色に戻す
                button.style.color = ''; // デフォルトの文字色に戻す
            }
        }
    </script>

</body>
</html>
