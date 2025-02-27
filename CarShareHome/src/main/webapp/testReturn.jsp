<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>顧客テスト</title>
    <link rel="stylesheet" href="css/costomersearch.css">
</head>
<body>
	<h1>現在の予約状況</h1>
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
        <c:if test="${empty(keybox.keyboxId)}">
	        <tr>
	            <td colspan="2"><span style="color: red;">キーボックスが解錠されていません。</span></td>
	        </tr>
        </c:if>
        <c:if test="${!empty(keybox.keyboxId)}">
	        <tr>
	            <td colspan="2"><span style="color: red;">${keybox.keyboxId}番の予備ボックスに鍵を返却してください</span>
		            <form class="button-container" action="TestReturnServlet" method="post" style="display: inline">
			           <input type="hidden" name="keyboxId" value="${keybox.keyboxId}">
			           <input type="hidden" name="customerId" value="${customer.customerId}">
			           <input type="hidden" name="stationId" value="${henkyaku.stationId}">
			           <input type="hidden" name="reservationId" value="${henkyaku.reservationId}">
			            
			            <button type="submit" class="return-button">キーボックスを閉じる</button>
	        		</form>
        		</td>
	        </tr>
        </c:if>
    </table>
    
    <div class="button-container">
        <form class="button-container" action="TestReturnServlet" method="post" name="henkyakuForm" style="display: inline">
	           <input type="hidden" name="customerId" value="${customer.customerId}">
	           <input type="hidden" name="stationId" value="${henkyaku.stationId}">
	           <input type="hidden" name="reservationId" value="${henkyaku.reservationId}">
	            
	            <button type="submit" class="return-button">更新</button>
        </form>
    </div>
</body>
</html>