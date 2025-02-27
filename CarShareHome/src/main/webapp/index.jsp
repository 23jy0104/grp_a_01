<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>料金計算</title>
</head>
<body>
    <h1>料金計算</h1>
    <form action="DiscountCalculatorServlet" method="post">
        <label for="startTime">開始時間 (yyyy-MM-dd HH:mm:ss):</label>
        <input type="text" id="startTime" name="startTime" required>
        <br>
        <label for="endTime">終了時間 (yyyy-MM-dd HH:mm:ss):</label>
        <input type="text" id="endTime" name="endTime" required>
        <br>
        <button type="submit">計算</button>
    </form>
</body>
</html>
