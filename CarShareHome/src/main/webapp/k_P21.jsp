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
            window.location = "k_top.jsp";
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>顧客情報検索画面</h1>
        <form id="searchForm" action="k_P21Servlet" method="post">
            <input type="text" name="name" placeholder="名前(カナ)スペースなし" required>
            <input type="text" name="tell" placeholder="電話番号ハイフンなし" required>

            <div class="button-container">
                <button type="submit" class="search">検索</button>
                <button type="button" class="signout" onclick="signout()">サインアウト</button>
            </div>
        </form>
    </div>
</body>
</html>
