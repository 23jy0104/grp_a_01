<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>料金結果</title>
</head>
<body>
    <h1>料金計算結果</h1>
    <p>合計料金: <%= request.getAttribute("totalCost") %> 円</p>
    <a href="index.jsp">戻る</a>
</body>
</html>
