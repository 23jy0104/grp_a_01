<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/keybox.css"> 
    <title>オペレーター</title>
    <style>
        .highlight {
            background-color: lightyellow; /* 薄い黄色 */
        }
        .red {
            background-color: red; /* 赤色 */
        }
        .orange {
            background-color: orange; /* オレンジ色 */
        }
    </style>
</head>
<body>
	<div class="container">
        <div class="header">
            <img src="img/rog.png" alt="TMC" class="logo">
            <h1>オペレーター</h1>
        </div>
        <div class="button-container">
			<c:forEach items="${keyboxList}" var="i" varStatus="s">
            <button class="yobi-button ${i.statusColor}" id="yobiBOX${i.keyboxId}" onclick="btnSubmit(${i.keyboxId})" ${buttonDisabled}>yobiBOX${i.keyboxId}</button>
            </c:forEach>
        </div>
        <div class="button-container">
        
        <form class="button-container" action="k_P25Servlet" method="post" name="keyBoxSelect">
            <input type="hidden" name="customerId" value="${customerId}">
            <input type="hidden" name="stationId" value="${stationId}">
            <input type="hidden" name="reservationId" value="${reservationId}">
            <input type="hidden" name="selectKeyBoxId" value="${selectKeyBoxId}">
            <input type="hidden" name="setKeyBoxId" value="${setKeyBoxId}" id="setKeyBoxId">
            <button type="submit" style="display:none;"></button>
        </form>
        <button class="update-button" id="updateButton" onclick="btnSubmit('')">更新</button>
        <form class="button-container" action="ReturnServlet" method="post" name="henkyakuForm" style="display: inline">
            <input type="hidden" name="customerId" value="${customerId}">
            <input type="hidden" name="stationId" value="${stationId}">
            <input type="hidden" name="reservationId" value="${reservationId}">
            <input type="hidden" name="selectKeyBoxId" value="${selectKeyBoxId}">
            
            <button type="submit" class="process-button ${henkyakuColor}" id="processButton" ${henkyakubuttonDisabled}>返却処理</button>
        </form>
        </div>
        <form class="button-container" action="k_P21Servlet" method="post" name="henkyakuForm" style="display: inline">
            <input type="hidden" name="customerId" value="${customerId}">
            
            <button type="submit" class="return-button">顧客情報確認画面に戻る</button>
        </form>
    </div>
   	<form class="button-container" action="TestReturnServlet" method="post" name="henkyakuForm" style="display: inline" target="_brank">
           <input type="hidden" name="customerId" value="${customerId}">
           <input type="hidden" name="stationId" value="${stationId}">
           <input type="hidden" name="reservationId" value="${reservationId}">
           <button type="submit" class="return-button">テスト用顧客キーボックス操作画面</button>
    </form>

    <script>
        let selectedYobiBox = null; // 選択されたyobiBOXを管理

        function btnSubmit(keyBoxId) {
        	document.getElementById('setKeyBoxId').value=keyBoxId;
        	document.keyBoxSelect.submit();
        	
			
        }
    </script>
</body>
</body>
</html>