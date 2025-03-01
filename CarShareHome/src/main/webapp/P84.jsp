<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="model.ReservationTime" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Customer" %>
<%@ page import ="model.CarData" %>


<%
String stationData=(String)session.getAttribute("stationData");
String customerId =(String)session.getAttribute("customerId");
String customerName = (String) session.getAttribute("customerName");
String carCode = (String) session.getAttribute("carCode");
String stationId = (String) session.getAttribute("stationId");
String img = (String) session.getAttribute("img");
String modelName = (String) session.getAttribute("modelName");
String number =(String)session.getAttribute("number");
String stationName =(String)session.getAttribute("stationName");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア - 空車情報</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/P63.css">
    
    <script>
    document.addEventListener('DOMContentLoaded', function () {
        const startDateInput = document.getElementById('startDate');
        const endDateInput = document.getElementById('EndDate');
        const startTimeHourInput = document.getElementById('startTimeHour');
        const startTimeMinuteInput = document.getElementById('startTimeMinute');
        const endTimeHourInput = document.getElementById('EndTimeHour');
        const endTimeMinuteInput = document.getElementById('EndTimeMinute');
        const errorMessage = document.getElementById('errorMessage'); // エラーメッセージ表示用の要素

        // 現在の日付と時間を取得
        const today = new Date();
        const formattedToday = today.toISOString().split('T')[0];
        const currentHour = today.getHours();
        const currentMinute = today.getMinutes();

        // 日付の最小値を今日の日付に設定
        startDateInput.setAttribute('min', formattedToday);
        endDateInput.setAttribute('min', formattedToday);

        // 開始日付が変更されたときの処理
        startDateInput.addEventListener('change', function () {
            const selectedStartDate = new Date(startDateInput.value);
            if (selectedStartDate < today) {
                startDateInput.value = formattedToday; // 今日の日付に戻す
            }
            updateStartTimeOptions();
        });

        // 終了日付が変更されたときの処理
        endDateInput.addEventListener('change', function () {
            const selectedEndDate = new Date(endDateInput.value);
            if (selectedEndDate < today) {
                endDateInput.value = formattedToday; // 今日の日付に戻す
            }
            updateEndTimeOptions();
        });

        function updateStartTimeOptions() {
            const selectedDate = new Date(startDateInput.value);
            const isToday = selectedDate.toISOString().split('T')[0] === formattedToday;

            // 時間の選択肢を更新
            for (let hour = 0; hour < 24; hour++) {
                const hourStr = String(hour).padStart(2, '0');
                const hourOption = startTimeHourInput.querySelector(`option[value="${hourStr}"]`);
                hourOption.disabled = !(isToday && hour > currentHour); // 現在の時間より後の時間のみ選択可能
            }

            // 分の選択肢を更新
            for (let minute of [0, 15, 30, 45]) {
                const minuteStr = String(minute).padStart(2, '0');
                const minuteOption = startTimeMinuteInput.querySelector(`option[value="${minuteStr}"]`);
                minuteOption.disabled = !(isToday && currentHour === hour && minute > currentMinute); // 現在の分より後の分のみ選択可能
            }
        }

        function updateEndTimeOptions() {
            const selectedDate = new Date(endDateInput.value);
            const isToday = selectedDate.toISOString().split('T')[0] === formattedToday;

            // 時間の選択肢を更新
            for (let hour = 0; hour < 24; hour++) {
                const hourStr = String(hour).padStart(2, '0');
                const hourOption = endTimeHourInput.querySelector(`option[value="${hourStr}"]`);
                hourOption.disabled = !isToday || hour < currentHour; // 現在の時間より後の時間のみ選択可能
            }
        }

     // 予約内容確認ボタンのフォームに対してのみイベントリスナーを設定
        document.querySelector('form[action="ReservationOK"]').addEventListener('submit', function(event) {
            const startHour = parseInt(startTimeHourInput.value);
            const startMinute = parseInt(startTimeMinuteInput.value);
            const endHour = parseInt(endTimeHourInput.value);
            const endMinute = parseInt(endTimeMinuteInput.value);
            
            // エラーメッセージの初期化
            errorMessage.textContent = "";

            // 現在の時間より前のチェック
            if (startDateInput.value === formattedToday && (startHour < currentHour || (startHour === currentHour && startMinute <= currentMinute))) {
                event.preventDefault(); // フォーム送信を防ぐ
                errorMessage.textContent = "予約開始時間は現在の時間より後でなければなりません。";
                return;
            }

            // 終了時間が開始時間より前のチェック
            if (endDateInput.value === startDateInput.value && (endHour < startHour || (endHour === startHour && endMinute <= startMinute))) {
                event.preventDefault(); // フォーム送信を防ぐ
                errorMessage.textContent = "予約終了時間は開始時間より後でなければなりません。";
                return;
            }

            // 終了日が開始日より前のチェック
            if (endDateInput.value < startDateInput.value) {
                event.preventDefault(); // フォーム送信を防ぐ
                errorMessage.textContent = "予約終了日は開始日より後でなければなりません。";
                return;
            }
        });





        // 初期状態の時間オプションを設定
        updateStartTimeOptions();
        updateEndTimeOptions();
    });
	</script>



</head>

<body>
<style>
/* フォーム全体のスタイル */
.table-section {
    margin-top: 20px;
}

/* 各入力フィールドのスタイル */
input[type="submit"] {
    background-color: #4CAF50; /* デフォルトのボタンの背景色 */
    color: white; /* テキストの色 */
    padding: 10px 15px; /* 内側の余白 */
    border: none; /* ボーダーなし */
    border-radius: 5px; /* 角を丸く */
    cursor: pointer; /* カーソルをポインタに */
    font-size: 16px; /* フォントサイズ */
    transition: background-color 0.3s; /* ホバー時のスムーズなトランジション */
}

/* 空き状況確認ボタンの特別なスタイル */
input[type="submit"][value="空き状況確認へ戻る"] {
    background-color: #ffa24b; /* 青色 */
}

/* ホバー効果 */
input[type="submit"]:hover {
    background-color: #45a049; /* ホバー時の色 */
}

/* 空き状況確認ボタンのホバー効果 */
input[type="submit"][value="空き状況確認へ戻る"]:hover {
    background-color: #e67812; /* ホバー時の青色 */
}

</style>
    <header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
    </header>

    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
 			<li class="nav-item gnav03"><a href="UserReservation?customerId=<%= customerId%>&customerName=<%=customerName%>">予約確認・変更・取り消し</a></li>            
            <li class="nav-item gnav04"><a href="UseHistory?customerId=<%= customerId %>&customerName=<%= customerName %>">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>
    <h2 style="text-decoration: underline; text-align: left;">予約入力画面</h2>
    <div class="content">
            <div class="flex-container">
                <div class="image-section"></div>
                    <img src="img/<%=img %>" alt="サンプル画像" style="width: 400px; height: auto; margin-top: 10px;">
                    <p><%=modelName %><br><%= number%><br></p>
                </div>
				<div class="table-section">
				    <table>
				        <tr>
				            <td class="highlight">ステーション名</td>
				            <td><%=stationName %></td>
				        </tr>
				    </table>
				<div style="display: flex; justify-content: flex-start; align-items: center; margin-top: 20px;">
				    <!-- 空き状況確認ボタン -->
				    <form action="ReservationCar" method="post" style="margin-right: 10px; margin-top: 104px;"> <!-- マージンを調整 -->
				        <input type="hidden" name="stationId" value="<%= stationId %>">
				        <input type="hidden" name="stationName" value="<%= stationName %>">
				        <input type="hidden" name="stationData" value="<%= stationData %>">
				        <input type="hidden" name="carType" value="<%= modelName %>">
				        <input type="submit" value="空き状況確認へ戻る">
				    </form>
				
				    <!-- 予約内容確認ボタン -->
				    <form action="ReservationOK" method="get" style="display: inline;">
				
				        <label for="startDate">予約開始日:</label>
				        <input type="date" name="startDate" id="startDate">
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
				        <input type="date" name="EndDate" id="EndDate">
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
				        <input type="hidden" id="stationId" name="stationId" value="<%= stationId %>">
				        <input type="hidden" id="carCode" name="carCode" value="<%= carCode %>">
				        <input type="hidden" id="img" name="img" value="<%= img %>">
				        <input type="hidden" id="stationName" name="stationName" value="<%= stationName %>">
				        <input type="hidden" id="modelName" name="modelName" value="<%= modelName %>">
				        <input type="hidden" id="number" name="number" value="<%= number %>">
				        <input type="submit" value="予約内容確認画面へ">
				    </form>
				</div>
				<div id="errorMessage" style="color: red; font-weight: bold;">
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
                    out.println(errorMessage);
                }
            %>
        </div>
            </div>
        </div>
    
