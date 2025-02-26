<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.Calendar" %>

<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="java.util.List" %>

<%@ page import="java.util.ArrayList" %>

<%@ page import="java.util.Locale" %>
<%@ page import ="model.Station" %>



<%

 String customerName = (String) session.getAttribute("customerName");

 String stationIdValue = request.getParameter("stationid");
 System.out.println(stationIdValue);
 String stationNameValue =request.getParameter("stationname");
 String stationDataValue =request.getParameter("stationdata");
 // 現在の日時を取得
 Calendar calendar = Calendar.getInstance();

 session.setAttribute("stationId", stationIdValue);
 session.setAttribute("stationName", stationNameValue);
 session.setAttribute("stationData", stationDataValue);
 calendar.add(Calendar.MINUTE, 30);

 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

 String minStartDateTime = dateFormat.format(calendar.getTime());

String stationId =  (String)session.getAttribute("stationId");
String stationData =(String)session.getAttribute("stationData");
String stationName =(String)session.getAttribute("stationName");
 // 日付関連の変数を定義

 Calendar today = Calendar.getInstance();

 Calendar oneMonthLater = Calendar.getInstance();

 oneMonthLater.add(Calendar.MONTH, 1);

 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//P56.jsp

%>



<!DOCTYPE html>

	<html lang="ja">
	
	<head>
	
		 <meta charset="UTF-8">
		
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		 <title>TMC カーシェア - ステーション情報</title>
		
		 <link rel="stylesheet" href="css/nav.css">
		
		 <link rel="stylesheet" href="css/P56.css">
		
		 <link rel="stylesheet" href="css/timeTable.css">
		
		 <style>
		
			 .reservation-inputs {
			
			 display: flex;
			
			 flex-direction: column;
			
			 gap: 20px;
			
			 }
			
			
			
			 .time-group {
			
			 display: flex;
			
			 flex-direction: column;
			
			 gap: 10px;
			
			 border: 1px solid #ccc;
			
			 padding: 10px;
			
			 border-radius: 5px;
			
			 background-color: #f9f9f9;
			
			 }
			
			
			
			 .flex-container {
			
			 display: flex;
			
			 justify-content: space-between;
			
			 margin-top: 10px;
			
			 }
			
			
			
			 .flex-item {
			
			 flex: 1;
			
			 margin: 0 5px;
			
			 }
			
			
			
			 .button-container {
			
			 text-align: center;
			
			 }
			
			
			
			 select {
			
			 width: 100%;
			
			 padding: 8px;
			
			 border: 1px solid #ccc;
			
			 border-radius: 4px;
			
			 font-size: 16px;
			
			 }
			
			
			
			 .calendar-container {
			
			 display: flex;
			
			 justify-content: center;
			
			 flex-wrap: nowrap;
			
			 padding: 10px;
			
			 margin: 0 auto;
			
			 }
			
			
			
			 .calendar {
			
			 display: inline-block;
			
			 margin: 10px;
			
			 width: 350px;
			
			 box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
			
			 }
			
			
			
			 .days {
			
			 display: grid;
			
			 grid-template-columns: repeat(7, 1fr);
			
			 gap: 5px;
			
			 }
			
			
			
			 .day {
			
			 width: 40px;
			
			 height: 40px;
			
			 display: flex;
			
			 justify-content: center;
			
			 align-items: center;
			
			 cursor: pointer;
			
			 border: 1px solid #ccc;
			
			 border-radius: 5px;
			
			 font-size: 14px;
			
			 }
			
			
			
			 .disabled {
			
			 background-color: lightgray;
			
			 cursor: not-allowed;
			
			 }
			
			
			
			 .header {
			
			 display: grid;
			
			 grid-template-columns: repeat(7, 1fr);
			
			 font-weight: bold;
			
			 margin-bottom: 5px;
			
			 }
			
			
			
			 .header div {
			
			 text-align: center;
			
			 }
			
			
			
			 .timetable {
			
			 display: none;
			
			 margin-top: 200px;
			
			 width: 60%;
			
			 max-width: 700px;
			
			 margin-left: 0;
			
			 margin-right: auto;
			
			 }
			
			
			
			 .timetable table {
			
			 width: 100%;
			
			 border-collapse: collapse;
			
			 }
			
			
			
			 .timetable th,
			
			 .timetable td {
			
			 border: 1px solid #ccc;
			
			 padding: 8px;
			
			 text-align: center;
			
			 }
			
			
			
			 .reserved {
			
			 background-color: red;
			
			 color: white;
			
			 cursor: not-allowed;
			
			 }
			
			
			
			 .available {
			
			 background-color: blue;
			
			 color: white;
			
			 cursor: pointer;
			
			 }
			
			
			
			 .selected {
			
			 background-color: green;
			
			 }
			
		</style>
	
	
	
		</head>
	
		<body>
	
			 <header>
			
				 <img src="img/rog.png" alt="TMCロゴ">
				
				 <h1>TMC カーシェア</h1>
				
				 <h4 id="username"><%= customerName %>さん</h4>
				
				 <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
			
			 </header>



			 <nav class="nav">
			
				 <ul>
				
					 <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
					
					 <li class="nav-item gnav03"><a href="P65.jsp">予約確認・変更・取り消し</a></li>
					
					 <li class="nav-item gnav04"><a href="P74.jsp">ご利用履歴</a></li>
					
					 <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
				
				 </ul>
			
			 </nav>



		 <main>
		
			 <h2>ステーション情報</h2>
			
			 <button class="back-button" onclick="location.href='P55.jsp'">検索結果一覧に戻る</button>
			
		 	<br>
			
			 <h3><%if(stationNameValue!=null){
				 	%>
				 	<%=stationNameValue%>
				 	<%
			 	   }else{
			 		   %>
			 		   
			 		  <%=stationName%>
			 		  <%
			 	   }
				 %></h3>
		
		 <table>
		
			 <tr>
			
				 <th>お知らせ</th>
				
				 <th>注意点:<br>
			
					 ・ご乗車の際は、必ずご自身で安全点検をしてください。<br>
					
					 ・全車禁煙です。喫煙される場合は、クルマをとめ、喫煙設備のある場所でお願いします。<br>
					
					 ・車内で出た飲み物,食べ物などのゴミは、必ず各自でお持ち帰りください。<br><br>
					
					 アクセス:<br>
					
					 大久保駅北口から北西に進んでいただき、大久保通りをまっすぐ進んだ場所にございます。
					
			 	 </th>
			
			 </tr>
			
			 <tr>
			
			 	 <th>ステーション情報</th>
			
			 	 <th><%if(stationDataValue!=null){
				 	%>
				 	<%=stationDataValue%>
				 	<%
			 	   }else{
			 		   %>
			 		   
			 		  <%=stationData%>
			 		  <%
			 	   }
				 %></th>
			
			 </tr>
			
		 </table>
		
			 <div class="flex-container">
			
				 <div class="flex-item">
				
					 <h3>空き情報から探す</h3>
					
					 <div class="calendar-container">
		
						 <%
						
						 for (int monthOffset = 0; monthOffset < 2; monthOffset++) {
						
						 Calendar monthCalendar = Calendar.getInstance();
						
						 monthCalendar.add(Calendar.MONTH, monthOffset);
						
						 monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
						
						 int daysInMonth = monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
						
						 int firstDayOfWeek = monthCalendar.get(Calendar.DAY_OF_WEEK);
						
						 
						
						 out.println("<div class='calendar'>");
						
						 out.println("<h3>" + monthCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.JAPANESE) + " " + monthCalendar.get(Calendar.YEAR) + "</h3>");
						
						 out.println("<div class='header'>");
						
						 out.println("<div>日</div><div>月</div><div>火</div><div>水</div><div>木</div><div>金</div><div>土</div>");
						
						 out.println("</div>");
						
						 out.println("<div class='days'>");
						
						
						
						 // 空白を表示
						
						 for (int i = 1; i < firstDayOfWeek; i++) {
						
						 out.println("<div class='day disabled'></div>");
						
						 }
						
						
						
						 // 日付を表示
						
						 for (int day = 1; day <= daysInMonth; day++) {
						
						 monthCalendar.set(Calendar.DAY_OF_MONTH, day);
						
						 String dateStr = sdf.format(monthCalendar.getTime());
						
						
						
						 // 今日以前または1か月後以降の日付は無効化
						
						 boolean isDisabled = monthCalendar.before(today) || monthCalendar.after(oneMonthLater);
						
						 String className = isDisabled ? "day disabled" : "day";
						
						 
						
						 out.println("<div class='" + className + "' " + (isDisabled ? "" : "onclick='selectDate(\"" + dateStr + "\")'") + ">" + day + "</div>");
						
						 }
						
						
						
						 out.println("</div></div>");
						
						 }
						
						 %>
		
					 </div>
					
				</div>
					
		 <div class="flex-item">
		
		 <h3 class="sub-title">車種から探す</h3>
		
		 <form action="ReservationCar" method="post"> <!-- ここはReservationCarに送信 -->
		
		 <input type="hidden" name="stationId" value="<%= stationIdValue %>">
		
		 <input type="hidden" name="stationName" value="<%= stationNameValue %>">
		 
		 <input type ="hidden" name ="stationData" value ="<%=stationDataValue %>">
		
		 
		
		 <select id="carType" name="carType" required>
		
		 <option value="BNR32型 skyline Nismo">BNR32型 skyline Nismo</option>
		
		 <option value="NSX NA-1型 type-R">NSX NA-1型 type-R</option>
		
		 <option value="GT-R R35 Nismo Special Edition T-spec">GT-R R35 Nismo Special Edition T-spec</option>
		
		 </select> 
		
		 <div class="button-container">
		
		 <button type="submit">検索</button> <!-- フォーム送信ボタン -->
		
		 </div>
		
		 </form>
		
		 </div>
		
		 </div>
		
		
		
		 <!-- タイムテーブル表示用のHTML -->
		
		<div id="timetable" class="timetable" style="display:none;">
		
		 <h3 id="timetable-title"></h3>
		
		 <table>
		
		 <thead>
		
		 <tr id="timetable-header"></tr>
		
		 </thead>
		
		 <tbody id="timetable-body"></tbody>
		
		 </table>
		
		 <div id="reserve-button-container" style="display:none;">
		
		 <button type="button" onclick="submitReservation()">予約する</button>
		
		 </div>
		
		</div>
		
		
		
		<script>
		
		 const selectedTimes = [];
		
		
		
		 function selectDate(date) {
		
		 document.getElementById("timetable-title").textContent = date + " のタイムテーブル";
		
		 const timetableBody = document.getElementById("timetable-body");
		
		 timetableBody.innerHTML = ""; // 既存の行をクリア
		
		
		
		 // 1時間単位の時間を表示
		
		 const timeRow = document.createElement("tr");
		
		 for (let hour = 0; hour < 24; hour++) {
		
		 const timeCell = document.createElement("th");
		
		 timeCell.colSpan = 4; // 15分単位で4つのセルをまとめる
		
		 timeCell.textContent = (hour < 10 ? "0" : "") + hour + ":00"; // 1時間単位で表示
		
		 timeRow.appendChild(timeCell);
		
		 }
		
		 timetableBody.appendChild(timeRow);
		
		
		
		 const statusRow = document.createElement("tr");
		
		 for (let hour = 0; hour < 24; hour++) {
		
		 for (let quarter = 0; quarter < 4; quarter++) {
		
		 const statusCell = document.createElement("td");
		
		 const isReserved = (hour % 2 === 0 && quarter === 0); // 例: 偶数時間の最初の15分を予約済みとする
		
		
		
		 // 背景色を設定
		
		 if (isReserved) {
		
		 statusCell.className = "reserved"; // 予約済みの場合、赤色背景
		
		 } else {
		
		 statusCell.className = "available"; // 空きの場合、青色背景
		
		 statusCell.style.cursor = "pointer"; // 空きの場合、クリック可能に
		
		
		
		 // 時間のクリックイベント
		
		 statusCell.onclick = function() {
		
		 const selectedTime = hour * 4 + quarter; // 15分単位の時間を計算
		
		 const selectedIndex = selectedTimes.indexOf(selectedTime);
		
		 
		
		 if (selectedIndex > -1) {
		
		 // 既に選択されている場合は解除
		
			 selectedTimes.splice(selectedIndex, 1);
			
			 statusCell.classList.remove("selected");
		
		 } else {
		
		 // 未選択の場合は選択
		
			 selectedTimes.push(selectedTime);
			
			 statusCell.classList.add("selected");
		
		 }
			
		 // 予約ボタンの表示/非表示
		
		 document.getElementById("reserve-button-container").style.display = selectedTimes.length > 0 ? "block" : "none";
		
		 };
		
		 }
		 statusRow.appendChild(statusCell);
		
		 }
		
		 }
		
		 timetableBody.appendChild(statusRow);		
		 // タイムテーブルを表示
		
		 document.getElementById("timetable").style.display = "block";
		
		 }
		
		 function submitReservation() {
		
		 // 選択された時間をフォームに設定
		
		 const selectedTimesString = selectedTimes.join(","); // カンマ区切りで時間を結合
		
		 document.getElementById('startTime').value = selectedTimesString; // 開始時間に設定
		
		 document.getElementById('endTime').value = selectedTimesString; // 終了時間にも設定（必要に応じて変更）		
		
		 // フォームを送信
		
		 document.getElementById("reservationForm").submit(); // フォーム送信
		
		 }
		
		</script>
	</body>

</html>