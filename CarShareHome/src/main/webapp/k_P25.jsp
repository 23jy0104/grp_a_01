<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>オペレーター</title>
    <link rel="stylesheet" href="css/costomersearch.css">  
</head>
<body>
	<h1>情報確認画面</h1>
    <table>
        <tr>
            <th colspan="2" class="information">顧客情報</th>
        </tr>
        <tr>
            <th>名前</th>
            <td>${customer.customerName}</td>
        </tr>
        <tr>
            <th>顧客ID</th>
            <td>${customer.customerId}</td>
        </tr>
        <tr>
            <th>電話番号</th>
            <td>${customer.tellNumber}</td>
        </tr>
        <tr>
            <th>メールアドレス</th>
            <td>${customer.email}</td>
        </tr>
        <tr>
            <th>生年月日</th>
            <td>${customer.birthDate}</td>
        </tr>
        <tr>
            <th colspan="2" class="information">利用状況</th>
        </tr>
        <tr>
            <th>ステーション</th>
            <td>${henkyaku.stationName}</td>
        </tr>
        <tr>
            <th>ナンバー</th>
            <td>${henkyaku.number}</td>
        </tr>
        <tr>
        	<th>車種</th>
        	<td>${henkyaku.carName}</td>
        </tr>
        <tr>
            <th>利用開始日時</th>
            <td>${henkyaku.startDate}</td>
        </tr>
    </table>
    <div class="button-container">
        <button onclick="location.href='P29.html'">返却処理</button>
    </div>
</body>
</html>