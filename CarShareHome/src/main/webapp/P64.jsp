<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="model.Customer" %>
<%@ page import ="model.Station" %>
<%@ page import ="model.Reservation" %>
<%@ page import ="model.CarData" %>
<%
 String customerId=(String)session.getAttribute("customerId");
 String customerName =(String)session.getAttribute("customerName");
 String carCode =(String)session.getAttribute("carCode");
 String stationId = (String)session.getAttribute("stationId");
 String img = (String)session.getAttribute("img");
 String modelName = (String)session.getAttribute("modelName");
 String number =(String)session.getAttribute("number");
 String stationName=(String)session.getAttribute("stationName");
 String startDate =(String)session.getAttribute("startDate");
 String endDate =(String)session.getAttribute("endDate");
 Integer totalCost = (Integer) session.getAttribute("totalCost");
 
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/P63.css">
</head>
<body>
    <header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <div class="user-info">
            <h4 id="username"><%=customerName %>さん</h4>
            <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
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
    <main>
        <center><h1>予約を受け付けました。</h1></center>
        <h2 style="text-decoration: underline; text-align: left;">ご登録内容</h2>
        <div class="content">
            <div class="flex-container">
                <div class="image-section"></div>
                    <img src="img/<%=img %>" alt="車両" style="width: 400px; height: auto; margin-top: 10px;">
                    <p><%=img %><br><%=number %><br></p>
                </div>
                <div class="table-section">
                    <table>
                        <tr>
                            <td class="highlight">ステーション名</td>
                            <td>${stationName }</td>
                        </tr>
                        <tr>
                            <td class="highlight">利用開始日時</td>
                            <td>${startDate }</td>
                        </tr>
                        <tr>
                            <td class="highlight">利用終了日時</td>
                            <td>${endDate }</td>
                        </tr>
                        <tr>
                            <td class="highlight">課金予定時間</td>
                            <td>${totalCost }円</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="button-container">
    		<button class="action-button" onclick="window.location.href='UserReservation?customerId=<%= customerId %>&customerName=<%= customerName %>'">予約確認画面へ</button>
		</div>
    </main>
</body>
</html>

</html>