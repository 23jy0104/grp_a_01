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
    <<link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/style2.css"> 
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
    <h4>※ご予約の時間のみ変更が可能となります。開始日と終了日の日付の変更はできません。日付を跨いでの変更、または車種の変更をご希望のお客様は一度予約を取り消してから再度ご予約ください。s</h4>
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
        	<th>予定金額</th>
        	<td><%=price %>
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
    </table
	<div id="errorMessage" style="color: red; font-weight: bold;">
</div>
		<div style="text-align: center;">
        <form action="Henkoukakunin"method ="post">
        	<input type ="hidden" name="reservationId" value="<%=reservationId %>">
        	<input type ="submit"value="変更する">
        
        </form>
    </div>

		</div>
   
        </div>
       
        

    </main>
</body>
</html>
