<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.CarData" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>

<%
// セッションから顧客情報を取得
String customerName = (String) session.getAttribute("customerName");
String customerId = (String) session.getAttribute("customerId");


// リクエストから車両情報を取得
String stationName = (String) request.getAttribute("stationName");
String stationId = (String) request.getAttribute("stationId");
String stationData = (String) request.getAttribute("stationData");
String stopDateParam = (String) request.getAttribute("stopDate");

// 車両情報リストを取得
List<CarData> carData = (List<CarData>) request.getAttribute("carData");

// jspで入力した日時
String selectedDate = request.getParameter("selectedDate");
String startTimeHour = request.getParameter("startTimeHour");
String startTimeMinute = request.getParameter("startTimeMinute");

// 開始時間を取得
int startHour = Integer.parseInt(startTimeHour);
int startMinute = Integer.parseInt(startTimeMinute);

// stopDateの処理
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
Date stopDate = sdf.parse(stopDateParam);
int stopHour = stopDate.getHours();

// 時間スロットの生成
List<String> timeSlots = new ArrayList<>();
for (int hour = startHour; hour <= stopHour; hour++) {
    for (int minute = 0; minute < 60; minute += 30) { // 30分単位
        String formattedTime = String.format("%02d:%02d", hour, minute);
        timeSlots.add(formattedTime);
    }
}
%>

<!DOCTYPE html>
 <html lang="ja">
  <head> 
  <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
   <title>TMC カーシェア - 空車情報</title>
    <link rel="stylesheet" href="css/nav.css">
     <link rel="stylesheet" href="css/P57.css"> 
     <style> 
     	.car-item {
			 display: flex;
			 margin-bottom: 20px;
			 border: 1px solid #ccc;
			 padding: 10px;
			 border-radius: 5px;
		}
			   
		.car-image {
			 width: 150px;
			 height: auto;
			 margin-right: 20px;
		} .car-details {
			 flex: 1;
		} .time-table {
			 margin-top: 10px;
			 border-collapse: collapse;
			 width: 100%; 
		}.time-table th, .time-table td {
			 border: 1px solid #ccc;
			 padding: 10px;
			 text-align: center;
		} .available {
			 background-color: blue;
			 color: white;
		}
    /* 既存のスタイルに追加 */
		 input[type="date"],
		    select {
		        padding: 10px;
		        margin: 5px 0;
		        border: 1px solid #ccc;
		        border-radius: 5px;
		        width: calc(100% - 22px); /* 幅を調整 */
		        box-sizing: border-box; /* パディングとボーダーを含む */
		 }
		
		 input[type="date"]:focus,
		    select:focus {
		        border-color: #007bff; /* フォーカス時のボーダーカラー */
		        outline: none; /* デフォルトのアウトラインを削除 */
		 }
		
		 .button-container {
		    margin-top: 20px; /* ボタンの上にスペースを追加 */
		 }
		
		 input[type="button"], 
		    input[type="submit"] {
		        background-color: #007bff; /* ボタンの背景色 */
		        color: white; /* ボタンの文字色 */
		        border: none; /* ボーダーをなしに */
		        padding: 10px 20px; /* パディング */
		        border-radius: 5px; /* 角を丸く */
		        cursor: pointer; /* カーソルをポインタに */
		        font-size: 16px; /* フォントサイズ */
		 }
		
		input[type="button"]:hover, 
		    input[type="submit"]:hover {
		        background-color: #0056b3; /* ホバー時の背景色 */
		}
</style>
		

</style>
</head> <body> <header> <img src="img/rog.png" alt="TMCロゴ"> <h1>TMC カーシェア</h1> <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button> </header>

<nav class="nav">
    <ul>
        <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
        <li class="nav-item gnav03"><a href="P65.jsp">予約確認・変更・取り消し</a></li>
        <li class="nav-item gnav04"><a href="UseHistory?customerId=<%= customerId %>&customerName=<%= customerName %>">ご利用履歴</a></li>
        <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
    </ul>
</nav>

<main>
    <h2><%= selectedDate %></h2>
    <h2><%= stationName %> の空いている車両一覧</h2>
	
    <%
    for (CarData car : carData) {
    %>  <img class="car-image" src="img/<%= car.getCarImage() %>" alt="車" />
        <div class="car-item">
            <div class="car-details">
                <label>車種名：<%= car.getModelName() %></label>
                <br>
			                    <label>予約可能時間：</label>
				<table class="time-table">
				    <thead>
				        <tr>
				            <th>時間</th>
				            <%
				            for (String time : timeSlots) {
				            %>
				                <th><%= time %></th>
				            <%
				            }
				            %>
				        </tr>
				    </thead>
				    <tbody>
				        <tr>
				            <td>予約可能</td>
				            <%
				            for (String time : timeSlots) {
				            %>
				                <td class="available"></td>
				            <%
				            }
				            %>
				        </tr>
				    </tbody>
				</table>
			  </div>
        </div>
    <%
    }
    %>
    
	<form action ="DiscountCalculatorServlet" method ="post">
		<input type="hidden" name="stationId" value="<%= stationId %>">
		<input type ="hidden" name ="stationName" value ="<%=stationName %>">
		<select id="carType" name="carType" required>
				<option value="BNR32型 skyline Nismo">BNR32型 skyline Nismo</option>
				<option value="NSX NA-1型 type-R">NSX NA-1型 type-R</option>
				<option value="GT-R R35 Nismo Special Edition T-spec">GT-R R35 Nismo Special Edition T-spec</option>
		</select>
		
		<div id="errorMessage" style="color: red; font-weight: bold;"></div>
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
              </div>	
	</form>
    <div class="button-container">
        <% String detailUrl = "P56.jsp?stationid=" + stationId + "&stationname=" + stationName + "&stationdata=" + stationData; %>
        <a href="<%= detailUrl %>">
            <input type="button" value="戻る">
        </a>
    </div>
</main>
</body>
 </html>