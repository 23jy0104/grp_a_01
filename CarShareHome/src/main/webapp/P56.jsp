<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import ="model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Station" %>

<%	
	String customerName = (String) session.getAttribute("customerName");
	List<String[]> stations = (List<String[]>) session.getAttribute("stations"); // ステーション情報を取得
	String stationIdValue   =request.getParameter("stationid");
	String stationNameValue = request.getParameter("stationname"); // URLからステーション名を取得
	String stationDataValue = request.getParameter("stationdata"); // URLからステーションデータを取得
	
	// セッションに値を設定
	session.setAttribute("stationId",   stationIdValue);
	session.setAttribute("stationdata", stationDataValue);
	session.setAttribute("stationName", stationNameValue);
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/P56.css">
    <link rel="stylesheet" href="css/timeTable.css">
</head>
<body>
    <header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <h4 id="username"><%=customerName %></h4>
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
        <br><h3><%=stationNameValue %></h3>
        <table>
            <tr>
                <th>お知らせ</th>
                <th>注意点:<br>
                    　・ご乗車の際は、必ずご自身で安全点検をしてください。<br>
                    　・全車禁煙です。喫煙される場合は、クルマをとめ、喫煙設備のある場所でお願いします。<br>
                    　・車内で出た飲み物,食べ物などのゴミは、必ず各自でお持ち帰りください。<br><br>
                </th>
                <th>ステーション情報</th>
                <th><%=stationDataValue %></th>
            </tr>
        </table>
        
        <div class="flex-container">
            <div class="flex-item">
                <h3>空き情報から探す</h3>
                <div class="button-container">
				    <form action="ReservationTime" method="post">
				    	<input type="hidden" name="stationid" value ="<%=stationIdValue %>">
				        <input type="hidden" name="stationname" value="<%= stationNameValue %>">
				        利用開始日時:<br>
				        <input type="datetime-local" id="startdate" name="startdate" required><br>
				        利用終了日時:<br>
				        <input type="datetime-local" id="stopdate" name="stopdate" required>
				        
				        <br><input type="image" id="searchButton1" src="img/kensaku.gif" alt="検索" />
				    </form>
				</div>

            </div>

            <div class="button-container">
			    <form action="ReservationCar" method="post">
			    	<input type="hidden" name="stationid" value ="<%=stationIdValue %>"
			        <input type="hidden" name="stationname" value="<%= stationNameValue %>">
			        <select id="carType" name="carType">
			            <option value="BNR32型 skyline Nismo">BNR32型 skyline Nismo</option>
			            <option value="NSX NA-1型 type-R">NSX NA-1型 type-R</option>
			            <option value="GT-R R35 Nismo Special Edition T-spec">GT-R R35 Nismo Special Edition T-spec</option>
			        </select>
			        <br><input type="image" id="searchButton2" src="img/kensaku.gif" alt="検索" />
			    </form>
			</div>

        </div>
    </main>
</body>
</html>
