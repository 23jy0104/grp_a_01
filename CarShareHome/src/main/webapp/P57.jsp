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
String stationName=(String)request.getSession().getAttribute("stationName");
String stationId = (String) request.getSession().getAttribute("stationId");
String stationData = (String) session.getAttribute("stationData");
String stopDateParam = (String) request.getAttribute("stopDate");

// 車両情報リストを取得
List<CarData> carData = (List<CarData>) request.getAttribute("carData");

// jspで入力した日時
String selectedDate = (String)request.getAttribute("selectedDate");
String startTimeHour = (String)request.getAttribute("startTimeHour");
String startTimeMinute = (String)request.getAttribute("startTimeMinute");

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
		 <li class="nav-item gnav03"><a href="UserReservation?customerId=<%= customerId%>&customerName=<%=customerName%>">予約確認・変更・取り消し</a></li>        
		 <li class="nav-item gnav04"><a href="UseHistory?customerId=<%= customerId %>&customerName=<%= customerName %>">ご利用履歴</a></li>
        <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
    </ul>
</nav>

<main>
    <%
		boolean hasAvailableCars = carData != null && !carData.isEmpty();
	%>

<main>
    <h2><%= selectedDate %></h2>
    <h2><%= stationName %> の空いている車両一覧</h2>

    <%
    if (hasAvailableCars) {
        for (CarData car : carData) {
    %>  
        <img class="car-image" src="img/<%= car.getCarImage() %>" alt="車" />
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
    } else {
    %>
        <div style="color: red; font-weight: bold;">空車はありませんので日付を変更してお試しください。</div>
    <%
    }
    %>
    
    <%
    // 車両がある場合のみ、予約フォームを表示
    if (hasAvailableCars) {
    %>
    <form action="DiscountCalculatorServlet" method="post" onsubmit="return validateForm()">
	<input type="hidden" name ="stationName" value="<%=stationName %>">
	<input type="hidden" name ="stationData" value ="<%=stationData %>">
    <select id="carType" name="carType" required>
        <option value="">-- 車種を選択してください。 --</option>
        <%
        for (CarData car : carData) {              
        %>
        <option value="<%= car.getModelName() %>"><%= car.getModelName() %></option>
        <%
        }
        %>
    </select>
    <div id="carTypeError" style="color: red; display: none;">※ 車種を選択してください。</div><br>

    <label for="startDate">予約開始日:</label>
    <input type="date" name="startDate" value="<%= selectedDate %>"readonly>
    <br>
    
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
    <div id="startTimeHourError" style="color: red; display: none;">※ 予約開始時間を選択してください。</div>

    <select id="startTimeMinute" name="startTimeMinute" required style="padding: 5px;">
        <option value="">-- 分を選択 --</option>
        <option value="00">00分</option>
        <option value="15">15分</option>
        <option value="30">30分</option>
        <option value="45">45分</option>
    </select>
    <div id="startTimeMinuteError" style="color: red; display: none;">※ 予約開始分を選択してください。</div><br>
    
    <label for="EndDate">予約終了日:</label>
    <input type="date" name="EndDate" id="EndDate" min="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" required>
    <div id="endDateError" style="color: red; display: none;">※ 予約終了日を選択してください。</div>

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
    <div id="EndTimeHourError" style="color: red; display: none;">※ 予約終了時間を選択してください。</div>

    <select id="EndTimeMinute" name="EndTimeMinute" required style="padding: 5px;">
        <option value="">-- 分を選択 --</option>
        <option value="00">00分</option>
        <option value="15">15分</option>
        <option value="30">30分</option>
        <option value="45">45分</option>
    </select>
    <div id="EndTimeMinuteError" style="color: red; display: none;">※ 予約終了分を選択してください。</div>

    <input type="submit" value="予約内容を確認する">
</form>

<script>
		function validateForm() {
		    let isValid = true;
		
		    // 車種のバリデーション
		    const carType = document.getElementById('carType');
		    const carTypeError = document.getElementById('carTypeError');
		    if (carType.value === "") {
		        carTypeError.style.display = 'block';
		        isValid = false;
		    } else {
		        carTypeError.style.display = 'none';
		    }
		
		    // 予約開始時間のバリデーション
		    const startTimeHour = document.getElementById('startTimeHour');
		    const startTimeHourError = document.getElementById('startTimeHourError');
		    if (startTimeHour.value === "") {
		        startTimeHourError.style.display = 'block';
		        isValid = false;
		    } else {
		        startTimeHourError.style.display = 'none';
		    }
		
		    // 予約開始分のバリデーション
		    const startTimeMinute = document.getElementById('startTimeMinute');
		    const startTimeMinuteError = document.getElementById('startTimeMinuteError');
		    if (startTimeMinute.value === "") {
		        startTimeMinuteError.style.display = 'block';
		        isValid = false;
		    } else {
		        startTimeMinuteError.style.display = 'none';
		    }
		
		    // 予約終了日のバリデーション
		    const endDate = document.getElementById('EndDate');
		    const endDateError = document.getElementById('endDateError');
		    if (endDate.value === "") {
		        endDateError.style.display = 'block';
		        isValid = false;
		    } else {
		        endDateError.style.display = 'none';
		    }
		
		    // 予約終了時間のバリデーション
		    const endTimeHour = document.getElementById('EndTimeHour');
		    const endTimeHourError = document.getElementById('EndTimeHourError');
		    if (endTimeHour.value === "") {
		        endTimeHourError.style.display = 'block';
		        isValid = false;
		    } else {
		        endTimeHourError.style.display = 'none';
		    }
		
		    // 予約終了分のバリデーション
		    const endTimeMinute = document.getElementById('EndTimeMinute');
		    const endTimeMinuteError = document.getElementById('EndTimeMinuteError');
		    if (endTimeMinute.value === "") {
		        endTimeMinuteError.style.display = 'block';
		        isValid = false;
		    } else {
		        endTimeMinuteError.style.display = 'none';
		    }
		
		    return isValid;
		}
</script>

<div id="errorMessage" style="color: red; font-weight: bold;">
    <div class="button-container">
        <% String detailUrl = "P56.jsp?stationid=" + stationId + "&stationname=" + stationName + "&stationdata=" + stationData; %>
        <a href="<%= detailUrl %>"> 
            <input type="button" value="戻る">
        </a>
    </div>
</div>

    <%
    }
    %>
</main>

</body>
 </html>