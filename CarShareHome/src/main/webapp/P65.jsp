<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.Reservation"%>
<%@page import="java.util.List"%>
<%@ page import="model.Customer" %>
<%
	String customerId =(String)session.getAttribute("customerId");
	String customerName =(String)session.getAttribute("customerName");
	List<Reservation> list =(List<Reservation>)request.getAttribute("rireki");
    String reservationId = request.getParameter("reservationId");
    // reservationId を使ってデータベースから予約情報を取得し、変更処理を行う
%>
<!DOCTYPE html>
<html>
<!--予約変更・確認・取消-->

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/P65.css">
</head>
<body>

<header>
    <img src="img/rog.png" alt="TMCロゴ">
    <h1>TMC カーシェア</h1>
    <div class="user-info">
        <h4 id="username"><%=customerName %>さん</h4>
        <button class="logout-button" onclick="location.href='P29.jsp'" >ログアウト</button>
    </div>
</header>

<nav class="nav">
    <ul>
        <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
 		<li class="nav-item gnav03"><a href="UserReservation?customerId=<%= customerId%>&customerName=<%=customerName%>">予約確認・変更・取り消し</a></li>           
 		<li class="nav-item gnav04"><a href="UseHistory?customerId=${customerId}&customerName=${customerName}">ご利用履歴</a></li>
        <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
    </ul>
</nav>

<table>
    <div class="history">
        <tr>
            <th>予約の確認・変更・取消</th>
        </tr>
    </div>
</table>
<table class="information">
    <thead>
        <tr>
            <th>予約番号</th>
            <th>予約開始日時</th>
            <th>予約終了日時</th>
            <th>ステーション</th>
            <th>車両</th>
            <th>予約受付日時</th>
            <th>利用予定金額</th>
            <th>情報</th>
        </tr>
    </thead>
    <tbody>
        <tr>
        <% for(Reservation yoyaku: list){
        	
        %>
            <td><%=yoyaku.getReservationId() %></td>
            <td><%=yoyaku.getStartDate() %></td>
            <td><%=yoyaku.getStopDate() %></td>
            <td><%=yoyaku.getStation().getStationName() %></td>
            <td><%= yoyaku.getModelName() %> </td>
            <td><%=yoyaku.getReservationTime() %></td>
            <td><%=yoyaku.getPrice() %>円</td>
            <td><button class="change" onclick="location.href='Henkou?reservationId=<%=yoyaku.getReservationId()%>'">変更</button>
			<br><button class="cancel" onclick="location.href='Sakujo?reservationId=<%=yoyaku.getReservationId()%>'">取消</button></td>

         </tr>
       <%
        }
       %>
       
       

</table>
</body>
</html>
</html>