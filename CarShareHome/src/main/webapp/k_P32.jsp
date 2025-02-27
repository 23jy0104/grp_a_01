<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>オペレーター</title>
    <link rel="stylesheet" href="css/costomersearch.css">
</head>
<body>
	<h1>返却処理完了画面</h1>
    <p>下記の返却処理が完了しました。</p>
    <table>
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
        <button onclick="location.href='k_P21.jsp'">顧客情報検索に戻る</button>
    </div>
</body>
</html>