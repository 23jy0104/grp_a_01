<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/systemsearch.css">
    <title>システム管理課</title>
    <script>
        function signout() {
            window.location = "k_top.jsp";
        }

        function validateForm() {
            const name = document.forms["searchForm"]["name"].value;
            const tell = document.forms["searchForm"]["tell"].value;

            // エラーメッセージの初期化
            document.getElementById("nameError").style.display = "none";
            document.getElementById("tellError").style.display = "none";

            let isValid = true;

            // 名前のチェック（カナのみに制限）
            const namePattern = /^[ァ-ヶー]+$/;
            if (!namePattern.test(name)) {
                document.getElementById("nameError").innerText = "名前はカナ（全角）のみで入力してください。";
                document.getElementById("nameError").style.display = "block";
                isValid = false;
            }

            // 電話番号のチェック（数字のみ、ハイフンなし）
            const tellPattern = /^[0-9]+$/;
            if (!tellPattern.test(tell)) {
                document.getElementById("tellError").innerText = "電話番号は数字のみで入力してください。";
                document.getElementById("tellError").style.display = "block";
                isValid = false;
            }

            return isValid; // すべてのチェックが通った場合のみ送信
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>顧客情報検索画面</h1>
        <c:if test="${!empty(errMessage)}">
	        <tr>
	            <td colspan="2"><span style="color: red;">${errMessage}</span></td>
	        </tr>
        </c:if>
        <form id="searchForm" action="k_P21Servlet" method="post" onsubmit="return validateForm();">
            <input type="text" name="name" placeholder="名前(カナ)スペースなし" required>
            <div id="nameError" class="error-message" style="color: red; display: none;"></div>
            <input type="text" name="tell" placeholder="電話番号ハイフンなし" required>
            <div id="tellError" class="error-message" style="color: red; display: none;"></div>

            <div class="button-container">
                <button type="submit" class="search">検索</button>
                <button type="button" class="signout" onclick="signout()">サインアウト</button>
            </div>
        </form>
    </div>
</body>

</html>