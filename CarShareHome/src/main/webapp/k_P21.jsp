<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/systemsearch.css">
    <title>システム管理課</title>
    <script>
        function signout() {
            window.location = "k_top.jsp"; // サインアウトの処理をここに記述
        }

        function goBack() {
            window.location.href = "k_P25.jsp"; // 戻るボタンでP25.htmlに遷移
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>顧客情報検索画面</h1>
        <input type="text" placeholder="名前(カナ)スペースなし" required>
        <input type="number" placeholder="電話番号ハイフンなし" required>

        <div class="button-container">
            <button class="go-back" onclick="goBack()">検索</button>
            <button class="signout" onclick="signout()">サインアウト</button>
        </div>
    </div>
</body>
</html>
