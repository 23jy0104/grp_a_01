<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String customerId =(String)session.getAttribute("customerId");
String customerName = (String) session.getAttribute("customerName");
String reservationId =(String)request.getAttribute("reservationId");
String stationId =(String)request.getAttribute("stationId");
String stationName=(String)request.getAttribute("stationName");
String startDate =(String)request.getAttribute("startDate");
String stopDate =(String)request.getAttribute("stopDate");
String modelName =(String)request.getAttribute("modelName");
String number=(String)request.getAttribute("number");

boolean isDateTime = startDate.contains(" "); // 日付と時間が含まれているか判定

//日付だけを取得する場合
String dateOnly = isDateTime ? startDate.split(" ")[0] : startDate;



%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/style2.css"> <!-- P56.cssを追加 -->
</head>
<body>
   <header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <h4 id="username"><%=customerName%>さん</h4>
        <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
    </header>
    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
 			<li class="nav-item gnav03"><a href="UserReservation?customerId=<%= customerId%>&customerName=<%=customerName%>">予約確認・変更・取り消し</a></li>            
 			<li class="nav-item gnav04"><a href="UseHistory?customerId=${customerId}&customerName=${customerName}">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>
<main>
    <h4>※前後5時間以内のみ変更可能です。車種変更または日時変更をする際は取り消してから予約しなおしてください。</h4>
    <table>
        <tr>
            <th colspan="2">変更前の予約情報</th>
        </tr>
        <tr>
            <th>予約番号</th>
            <td><%=reservationId %>
        </tr>
        <tr>
        	<th>ステーション名</th>
        	<td><%= stationName %></td>
        </tr>
         <tr>
        	<th>予約開始日時</th>
        	<td><%=startDate %>
        </tr>
         <tr>
        	<th>予約終了日時</th>
        	<td><%=stopDate %></td>
        </tr>
         <tr>
        	<th>車種名</th>
        	<td><%=modelName %></td>
        </tr>
         <tr>
        	<th>ナンバー</th>
        	<td><%=number %>
        </tr>
        <tr>
            <td>駆動</td>
            <td>4 WD</td>
        </tr>
        <tr>
            <td>安全整備</td>
            <td>ドライブレコーダー</td>
        </tr>
        <tr>
            <td>備考</td>
            <td>RB26DETT (1990年式)</td>
        </tr>
    </table>
	<div id="errorMessage" style="color: red; font-weight: bold;">
    <%
       String errorMessage = (String) request.getAttribute("errorMessage");
       if (errorMessage != null) {
           out.println(errorMessage);
       }
    %>
</div>
	<h2>予約変更入力（入力）</h2>
	<form action="Henkou" method="get" style="display: inline;" onsubmit="return validateDates();">
	    <label for="startDate">予約開始日:</label>
	    <input type="date" name="startDate" id="startDate" value="<%= dateOnly %>" readonly>
	    <select id="startTimeHour" name="startTimeHour" required style="margin-right: 5px; padding: 5px;">
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
	    <input type="date" name="EndDate" id="EndDate" required>
	    <select id="EndTimeHour" name="EndTimeHour" required style="margin-right: 5px; padding: 5px;">
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
	    <input type ="hidden" name="reservationId" value ="<%=reservationId %>">
	    <input type="submit" value="変更する">
	</form>

		</div>
   
        </div>
        <script>
	        function validateDates() {
	            const startDate = new Date(document.getElementById('startDate').value);
	            const stopDate = new Date(document.getElementById('EndDate').value);
	            
	            const fiveHoursInMillis = 5 * 60 * 60 * 1000; // 5時間をミリ秒に変換
	            
	            // startDateからの5時間前後を計算
	            const minStopDate = new Date(startDate.getTime() - fiveHoursInMillis);
	            const maxStopDate = new Date(startDate.getTime() + fiveHoursInMillis);
	            
	            if (stopDate < minStopDate || stopDate > maxStopDate) {
	                const errorMessageDiv = document.getElementById('errorMessage');
	                errorMessageDiv.innerHTML = '※ 予約終了日時は予約開始日時の前後5時間以内で指定してください。';
	                return false;
	            } else {
	                document.getElementById('errorMessage').innerHTML = ''; // エラーメッセージをクリア
	            }
	            return true;
	        }
		</script>
        

    </main>
</body>
</html>
