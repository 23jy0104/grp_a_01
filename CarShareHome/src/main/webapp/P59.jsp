<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="model.ReservationTime" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import ="model.Customer" %>
<%@ page import ="model.Station" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
String stationName=(String)session.getAttribute("stationName");
String stationData=(String)session.getAttribute("stationData");
String customerId =(String)session.getAttribute("customerId");
String customerName = (String) session.getAttribute("customerName");
String carCode = (String) session.getAttribute("carCode");
String stationId = (String) session.getAttribute("stationId");
String img = (String) session.getAttribute("car_img");
String modelName = (String) session.getAttribute("modelName");
String selectedDate = (String) request.getAttribute("selectedDate"); // selectedDateを取得
List<ReservationTime> combinedList = (List<ReservationTime>) request.getAttribute("combinedList"); // 予約状況を取得
// カレンダーの日付処理
Calendar today = Calendar.getInstance();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Calendar oneMonthLater = Calendar.getInstance();
oneMonthLater.add(Calendar.MONTH, 1); 

// セッションから開始日と終了日を取得
String startDate = (String) session.getAttribute("startDate");
String endDate = (String) session.getAttribute("endDate");
%>


<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/P56.css">
    <link rel="stylesheet" href="css/P57.css">
    <link rel="stylesheet" href="css/P59.css">
    <link rel="stylesheet" href="css/timeTable.css">
    <style>
        .calendar-container {
            display: flex;
            justify-content: center; /* 中央配置 */
            flex-wrap: wrap; /* 複数カレンダーの場合に折り返す */
        }

        .calendar {
            display: inline-block;
            margin: 10px;
            border: 1px solid #ccc; /* カレンダーの枠 */
            border-radius: 5px;
            padding: 10px;
        }

        .days {
            display: grid;
            grid-template-columns: repeat(7, 1fr);
            gap: 5px;
        }

        .day {
            width: 60px;
            height: 60px;
            display: flex;
            justify-content: center;
            align-items: center;
            cursor: pointer;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 18px;
            background-color: white; /* 背景色を追加 */
        }

        .disabled {
            background-color: lightgray;
            cursor: not-allowed;
        }

        .selectedStart {
            background-color: orange; /* 開始日の色 */
        }

        .header {
            display: grid;
            grid-template-columns: repeat(7, 1fr);
            font-weight: bold;
            margin-bottom: 5px;
        }

        .button-container {
            margin-top: 10px;
        }

	    #carShareTable {
	        border-collapse: collapse; /* ボーダーの重なりをなくす */
	        width: 100%; /* テーブルの幅を100%に */
	    }
	    th, td {
	        border: 1px solid #000; /* セルのボーダー */
	        padding: 8px; /* パディング */
	        text-align: left; /* 左寄せ */
	    }
	    .available {
	        background-color: lightgreen; /* 予約可能の色 */
	    }
	    .unavailable {
	        background-color: red; /* 予約不可の色 */
	    }
	    .booked {
	        background-color: blue; /* 予約済みの色 */
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
        <div class="header-container">
            <h2>該当車種</h2>
        </div>
        <div class="additional-info-container" id="additionalInfo">
            <div>
                <label><%= modelName %></label>
                <img src="img/<%= img %>" alt="車" />
            </div>
        </div>
        <table class="info-table">
                <tr>
                    <td>駆動</td>
                    <td>4WD</td>
                </tr>
                <tr>
                    <td>安全装備</td>
                    <td>ドライブレコーダー, ブレーキサポート, バックモニター</td>
                </tr>
                <tr>
                    <td>備考</td>
                    <td>ETC車載器</td>
                </tr>
            </table>

        <h2>空き状況を確認したい日付をクリックしてください。</h2>
        <br>
        <div class="calendar-container">
        <%
            // 予約済み時間帯のリストを作成
            List<String> bookedDates = new ArrayList<>();
            if (combinedList != null) {
                for (ReservationTime reservation : combinedList) {
                    bookedDates.add(reservation.getStartDateTime()); // 予約の開始時間をリストに追加
                }
            }

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
                    String className = "day";
                    
                    // 予約済みの確認
                    if (bookedDates.contains(dateStr)) {
                        className += " disabled"; // 予約済みの場合は無効化
                    } else if (startDate != null && startDate.equals(dateStr)) {
                        className += " selectedStart"; // 開始日選択
                    }

                    // 日付がクリック可能であればイベントを追加
                    if (!isDisabled) {
                        out.println("<div class='" + className + "' onclick='handleDateClick(\"" + dateStr + "\")'>" + day + "</div>");
                    } else {
                        out.println("<div class='" + className + "'>" + day + "</div>");
                    }
                }

                out.println("</div></div>");
            }
        %>
        </div> <!-- カレンダーを囲むコンテナの終了 -->

        <div class="button-container">
              <div class="button-container">
			    <% String detailUrl = "P56.jsp?stationid="+stationId+"&stationname=" + stationName +"&stationdata="+ stationData ;  %>
			    <a href ="<%=detailUrl %>">
			    	<input type ="submit" value="戻る">
			    </a>
			</div>
        </div>
        
        <!-- 開始時間の入力フィールド -->
        <div id="startTimeContainer" style="display:none; margin-top: 20px; border: 1px solid #ccc; padding: 15px; border-radius: 5px; background-color: #f9f9f9;">
            <h3>空き状況確認</h3>
            <form action="ReservationCarTime" method="post">
                <label for="startTime" style="font-weight: bold;">確認したい開始時間:</label>
                <div style="display: flex; align-items: center; margin: 10px 0;">
                    <select id="startTimeHour" name="startTimeHour" required style="margin-right: 5px; padding: 5px;">
                        <option value="">-- 時間を選択 --</option>
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
                </div>
                
                <input type="hidden" id="selectedDate" name="selectedDate">
                <input type="hidden" id="stationId" name="stationId" value="<%= stationId %>"> <!-- stationIdを隠しフィールドに追加 -->
                <input type="hidden" id="car_code" name="carCode" value="<%=carCode %>"> <!-- carCodeを隠しフィールドに追加 -->
                <button type="submit" style="padding: 10px 15px; background-color: orange; color: white; border: none; border-radius: 5px; cursor: pointer;">検索</button>
            </form>
        </div>

        <script>
            let isDateSelected = false; // 日付が選択されたかどうかのフラグ

            function handleDateClick(selectedDate) {
                if (!isDateSelected) {
                    // 開始日が未選択なら、開始時間の入力フィールドを表示
                    document.getElementById('startTimeContainer').style.display = 'block';
                    document.getElementById('selectedDate').value = selectedDate; // 隠しフィールドに選択した日付を設定
                    isDateSelected = true; // 日付を選択したフラグを立てる
                } else {
                	document.getElementById('startTimeContainer').style.display = 'block';
                    document.getElementById('selectedDate').value = selectedDate; // 隠しフィールドに選択した日付を設定
                    isDateSelected = true; // 日付を選択したフラグを立てる
                }
            }
        </script>
        <!-- サーブレットの結果を表示するタイムテーブル -->
		<table id="carShareTable">
		    <tbody>
		        <tr>
		            <td colspan="96" class="time-cell">
		                <%= selectedDate != null ? selectedDate : "日付と空き状況を確認したい開始時間を入力してください。" %>
		            </td>
		        </tr>
		       <tr>
				    <%
				    // 予約状況を表示するためのタイムテーブルを動的に生成
				    if (combinedList != null && !combinedList.isEmpty()) {
				        // 1行目を飛ばすため、インデックスを1から開始
				        for (int i = 1 ; i < combinedList.size(); i++) {
				            ReservationTime time = combinedList.get(i);
				            String startDateTime = time.getStartDateTime(); // "yyyy-MM-dd HH:mm" 形式
				            String endDateTime = time.getEndDateTime();     // "yyyy-MM-dd HH:mm" 形式
				
				            // 時間を表示するためのフォーマット設定
				            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
				
				            // 文字列をDateに変換
				            Date startDateObj = inputFormat.parse(startDateTime);
				            Date endDateObj = inputFormat.parse(endDateTime);
				
				            // フォーマットした時間を取得
				            String formattedStartTime = outputFormat.format(startDateObj);
				            String formattedEndTime = outputFormat.format(endDateObj);
				
				            // 時間帯を表示
				            String displayTime = formattedStartTime + " ～ " + formattedEndTime;
				    %>
				            <td class="time-cell" colspan="4"><%= displayTime %></td>
				    <%
				        }
				    }
				    %>
				</tr>

		        <tr>
		            <%
		            // 状態を表示する行
		            
		            if (combinedList != null && !combinedList.isEmpty()) {
		                // 状態行も1行目を飛ばすため、インデックスを1から開始
		                for (int i = 1; i < combinedList.size(); i++) {
		                    ReservationTime time = combinedList.get(i);
		                    String status = time.getStatus();
		
		                    // 予約状態に応じて状態のクラスを設定
		                    String rowClass;
		                    if (status.equals("予約不可")) {
		                        rowClass = "unavailable"; // 予約不可色変更
		                    } else if (status.equals("予約済み")) {
		                        rowClass = "booked"; // 予約済み
		                    } else {
		                        rowClass = "available"; // 予約可能
		                    }
		
		                    // 15分ごとに状態を表示
		                    for (int j = 0; j < 4; j++) { // 1時間を4つの15分に分割
		            %>
		                <td class="<%= rowClass %>"></td> <!-- 状態 -->
		            <%
		                    }
		                }
		            %>
		        </tr>
		    </tbody>
		</table>
		
		<a href="ReservationCon?stationId=<%= stationId %>&carCode=<%= carCode %>&img=<%= img %>&modelName=<%= modelName %>" id="reservationLink" style="display:none;">予約入力画面へ</a>
		
		<%
		    } // combinedList のチェックが終わったら閉じる
		%>
		<script>
		    // タイムテーブルが生成された後にリンクを表示
		    document.getElementById('reservationLink').style.display = 'block';
		</script>

    </main>
</body>
</html>

