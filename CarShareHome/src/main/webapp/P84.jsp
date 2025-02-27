<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="model.ReservationTime" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Customer" %>
<%@ page import ="model.CarData" %>


<%
String customerId =(String)session.getAttribute("customerId");
String customerName = (String) session.getAttribute("customerName");
String carCode = (String) session.getAttribute("carCode");
String stationId = (String) session.getAttribute("stationId");
String img = (String) session.getAttribute("img");
String modelName = (String) session.getAttribute("modelName");
String number =(String)session.getAttribute("number");
String stationName =(String)session.getAttribute("stationName");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア - 空車情報</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/P63.css">
</head>
<body>
<style>
/* フォーム全体のスタイル */
.table-section {
    margin-top: 20px;
}

/* 各入力フィールドのスタイル */
input[type="date"],
select {
    width: 150px; /* 幅を指定 */
    padding: 10px; /* 内側の余白 */
    margin: 5px 0; /* 上下の余白 */
    border: 1px solid #ccc; /* ボーダー */
    border-radius: 5px; /* 角を丸く */
    font-size: 16px; /* フォントサイズ */
}

/* フォームのラベルスタイル */
label {
    font-weight: bold; /* 太字 */
    margin-right: 10px; /* ラベルとフィールドの間の余白 */
}

/* ボタンのスタイル */
input[type="submit"] {
    background-color: #4CAF50; /* ボタンの背景色 */
    color: white; /* テキストの色 */
    padding: 10px 15px; /* 内側の余白 */
    border: none; /* ボーダーなし */
    border-radius: 5px; /* 角を丸く */
    cursor: pointer; /* カーソルをポインタに */
    font-size: 16px; /* フォントサイズ */
}

/* ボタンのホバー効果 */
input[type="submit"]:hover {
    background-color: #45a049; /* ホバー時の背景色 */
}

</style>
    <header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
    </header>

    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
            <li class="nav-item gnav03"><a href="P65.jsp">予約確認・変更・取り消し</a></li>
            <li class="nav-item gnav04"><a href="UseHistory?customerId=<%= customerId %>&customerName=<%= customerName %>">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>
    <h2 style="text-decoration: underline; text-align: left;">予約入力画面</h2>
    <div class="content">
            <div class="flex-container">
                <div class="image-section"></div>
                    <img src="img/<%=img %>" alt="サンプル画像" style="width: 400px; height: auto; margin-top: 10px;">
                    <p><%=modelName %><br><%= number%><br></p>
                </div>
                <div class="table-section">
                    <table>
                        <tr>
                            <td class="highlight">ステーション名</td>
                            <td><%=stationName %></td>
                        </tr>
                    </table>
                    <form action ="ReservationOK" methdo ="post">
                    	<label for="startDate">予約開始日:</label>
                    	<input type="date" name="startDate" id="startDate">
                    	<select id="startTimeHour" name="startTimeHour" required style="margin-right: 5px; padding: 5px;">
	                       <label for="startTimeHour">予約開始時間:</label>
	                       <option value="">-- 選択 --</option>
	                        <%
	                        for (int hour = 0; hour < 24; hour++) {
	                            String hourStr = String.format("%02d", hour);
	                        %>
	                            <option value="<%= hourStr %>"><%= hourStr %></option>
	                        <%
	                        }
	                        %>
                    	</select>
                    
	                    <select id="startTimeMinute" name="startTimeMinute" required style="padding: 5px;">
	                        <option value="">-- 分を選択 --</option>
	                        <option value="00">00分</option>
	                        <option value="15">15分</option>
	                        <option value="30">30分</option>
	                        <option value="45">45分</option>
	                    </select>
                    	<br>
                    	<label for="EndDate">予約終了日:</label>
                    	<input type="date" name="EndDate" id="EndDate">
                    	<select id="EndTimeHour" name="EndTimeHour" required style="margin-right: 5px; padding: 5px;">
	                         <label for="EndTimeHour">予約終了時間:</label>
	                        	<option value="">-- 選択 --</option>
	                        <%
	                        for (int hour = 0; hour < 24; hour++) {
	                            String hourStr = String.format("%02d", hour);
	                        %>
	                            <option value="<%= hourStr %>"><%= hourStr %></option>
	                        <%
	                        }
	                        %>
                    	</select>
                    
	                    <select id="EndTimeMinute" name="EndTimeMinute" required style="padding: 5px;">
	                        <option value="">-- 分を選択 --</option>
	                        <option value="00">00分</option>
	                        <option value="15">15分</option>
	                        <option value="30">30分</option>
	                        <option value="45">45分</option>
	                    </select>
	                    <br>
	                    <input type ="hidden" id="stationId" name ="stationId" value="<%=stationId %>">
	                    <input type ="hidden" id ="carCode" name ="carCode" value="<%=carCode %>">
	                    <input type ="hidden" id="img" name="img" value="<%=img %>">
	                    <input type ="hidden" id="stationName" name="stationName" value="<%=stationName %>">
	                    <input type ="hidden" id="modelName" name="modelName" value="<%=modelName %>">
	                    <input type ="hidden" id="number" name="number" value="<%=number %>">
	                    <input type ="submit" value ="予約内容確認画面へ">
                    </form>
                </div>
            </div>
        </div>
    
