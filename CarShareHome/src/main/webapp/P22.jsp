
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="model.Customer"%>
<%
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html; charset=UTF-8");
String customerName = request.getParameter("customerName");
String customerKana = request.getParameter("customerKana");
String gender = request.getParameter("gender");
String email = request.getParameter("email");
String tellNumber = request.getParameter("tellNumber");
String postCode = request.getParameter("postCode");
String customerAddress = request.getParameter("customerAddress");
String licenseNumber = request.getParameter("licenseNumber");
String licenseDate = request.getParameter("licenseDate");
String birthDate = request.getParameter("birthDate");
String password = request.getParameter("password1");

String omote = request.getParameter("omoteBase64");
String ura = request.getParameter("uraBase64");
%>
<!--基本情報の入力-->

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>TMC カーシェア</title>
<link rel="stylesheet" href="css/credit.css">
<script>
	function toggleButton() {
		const checkbox = document.getElementById('myCheckbox');
		const button = document.getElementById('confirmButton');
		button.disabled = !checkbox.checked;

		// チェックが入っている場合、オレンジ色のスタイルを適用
		if (checkbox.checked) {
			button.style.backgroundColor = 'orange';
			;
		} else {
			button.style.backgroundColor = ''; // デフォルトの色に戻す
			button.style.color = ''; // デフォルトの文字色に戻す
		}
	}

	function confirmAction() {
		// 遷移先のURLを指定します
		window.location.href = 'UC03_08.html';
	}
</script>
</head>
<body>

	<header>
		<div class="logo">
			<img src="img/rog.png" alt="TMC Logo">
			<h1>TMC カーシェア</h1>
		</div>
	</header>

	<div class="step-container">
		<div class="step">
			<div class="step-icon">📝</div>
			<div class="step-label">詳細・規約のご確認</div>
		</div>
		<div class="step current">
			<div class="step-icon">🖊️</div>
			<div class="step-label">基本情報のご入力</div>
		</div>
		<div class="step confirmation">
			<div class="step-icon">🔍</div>
			<div class="step-label">ご入力基本情報のご確認</div>
		</div>
		<div class="step credit">
			<div class="step-icon">💳</div>
			<div class="step-label">クレジットカード情報</div>
		</div>
		<div class="step">
			<div class="step-icon">✅</div>
			<div class="step-label">申し込み完了</div>
		</div>

	</div>

	<div class="form-group">
		<form action="CreditNew" method="post">
			<input type="hidden" name="customerName" 		value="<%=customerName%>">
			<input type="hidden" name="customerKana" 		value="<%=customerKana%>">
			<input type="hidden" name="gender" 				value="<%=gender%>">
			<input type="hidden" name="email" 				value="<%=email%>">
			<input type="hidden" name="tellNumber" 			value="<%=tellNumber%>">
			<input type="hidden" name ="postCode"          value="<%= postCode %>">
			<input type="hidden" name="customerAddress" 	value="<%=customerAddress%>">
			<input type="hidden" name="licenseNumber" 		value="<%=licenseNumber%>"> 
			<input type="hidden" name="licenseDate" 		value="<%=licenseDate%>"> 
			<input type="hidden" name="birthDate" 			value="<%=birthDate%>">
			<input type="hidden" name="Password" 			value="<%=password%>"> 
			<input type="hidden" name="omote" 				value="<%=omote%>"> 
			<input type="hidden" name="ura" 				value="<%=ura%>">
				<label for="credit_number">
	            	<span class="required">必須</span>クレジットカード番号<span class="highlight"> ※半角数字、ハイフンなし</span><br>
	        	</label>
				<input type="text"id="password"placeholder="例:mypassword1234"required><br><br>
	        <span class="highlight2"> 注意:ご登録は、お申込ご本人名義のクレジットカードに限ります。</span><br>
	        VISA・JCB・AMEX・MASTER・DINERS・EPOSの6ブランドがご利用いただけます。
	        <label for="credit_date">
	            <span class="required">必須</span>有効期限
	        </label>
	        <input type="month" name="credittime" required><br>
	        <div class="select-container">
	           
	        </div>
	        <br>
	        <label for="security">
	            <span class="required">必須</span>セキュリティコード<span class="highlight"> ※半角数字</span>
	        </label>
	        <input type="number"id="password"placeholder="例:012"required>
	        <div class="warning">
	            <input type="checkbox" id="myCheckbox" onclick="toggleButton()">入力内容をご確認いただき、誤りがない場合はチェックをつけてください。<br>
	            ※チェックが入っていない場合、変更を確定できません。
	        </div>
	        <div class="btn container">
	            <input type="button" id="modoru" value="ご登録情報の確認に戻る" onclick="location.href='UC03_03.html'">
	            <button id="confirmButton" class="confirmbutton" disabled onclick="confirmAction()">確定</button>
	        </div>
	    </form>
    </div>
</body>
</html>