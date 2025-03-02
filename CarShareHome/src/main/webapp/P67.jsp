<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String customerId =(String)session.getAttribute("customerId");
String customerName = (String) session.getAttribute("customerName");
String reservationId =(String)request.getAttribute("reservationId");
String stationId =(String)request.getAttribute("stationId");
String stationName=(String)request.getAttribute("stationName");
String oldstartDate =(String)request.getAttribute("startDate");
String oldstopDate =(String)request.getAttribute("stopDate");
String modelName =(String)request.getAttribute("modelName");
String number=(String)request.getAttribute("number");
String carCode =(String)request.getAttribute("carCode");
int price =(Integer)request.getAttribute("price");

boolean isStartDateTime = oldstartDate.contains(" "); // 日付と時間が含まれているか判定

//日付だけを取得する場合
String startdateOnly = isStartDateTime ? oldstartDate.split(" ")[0] : oldstartDate;

boolean isStopDateTime = oldstopDate.contains(" ");

String stopdateOnly = isStopDateTime ? oldstopDate.split(" ")[0] : oldstopDate;

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
    <h4>※ご予約の時間のみ変更が可能となります。開始日と終了日の日付の変更はできません。日付を跨いでの変更、または車種の変更をご希望のお客様は一度予約を取り消してから再度ご予約ください。</h4>
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
        	<td><%=oldstartDate %>
        </tr>
         <tr>
        	<th>予約終了日時</th>
        	<td><%=oldstopDate %></td>
        </tr>
         <tr>
        	<th>車種名</th>
        	<td><%=modelName %></td>
        </tr>
         <tr>
        	<th>ナンバー</th>
        	<td><%=number %></td>
        </tr>
        <tr>
        	<th>予定金額</th>
        	<td><%=price %>円</td>
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
   <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="error-message">
        <%= request.getAttribute("errorMessage") %>
    </div>
<% } %>
</div>
	<h2>予約変更入力（入力）</h2>
	<form action="Henkou" method="post" style="display: inline;>
	    <label for="startDate">予約開始日:</label>
	    <input type="date" name="startDate" id="startDate" value="<%= startdateOnly %>" readonly>
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
	    <label for="endDate">予約終了日:</label>
	    <input type="date" name="endDate" id="EndDate" value ="<%=stopdateOnly%>"readonly>
	    <select id="endTimeHour" name="endTimeHour" required style="margin-right: 5px; padding: 5px;">
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
	    <select id="EndTimeMinute" name="endTimeMinute" required style="padding: 5px;">
	        <option value="">-- 分を選択 --</option>
	        <option value="00">00分</option>
	        <option value="15">15分</option>
	        <option value="30">30分</option>
	        <option value="45">45分</option>
	    </select>
	    <br>
	    <input type ="hidden" name="reservationId" value ="<%=reservationId %>">
	    <input type ="hidden" name="stationId" value="<%=stationId %>">
	    <input type ="hidden" name="carCode" value="<%=carCode %>">
	    <input type="hidden" name="customerId" value ="<%=customerId %>">
	    <input type ="hidden" name ="modelName" value ="<%=modelName %>">
	    <input type ="hidden" name="number" value ="<%=number %>">
	    <input type ="hidden" name ="stationName" value="<%=stationName %>">
	    <input type ="hidden" name ="price" value="<%=price %>">
	    <input type="hidden" name ="oldstartDate" value="<%=oldstartDate %>">
	    <input type ="hidden" name ="oldstopDate" value="<%=oldstopDate %>">
	    
	    <input type="submit" value="変更する">
	</form>

		</div>
   
        </div>
       
        

    </main>
</body>
</html>
