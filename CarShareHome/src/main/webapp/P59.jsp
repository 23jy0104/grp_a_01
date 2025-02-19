<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Station" %>
<%@ page import="model.CarData" %>
<%@ page import="model.KeyBox" %>
<%@ page import="model.Model" %>

<%
    String customerName = (String) session.getAttribute("customerName");
    String stationName = (String) session.getAttribute("stationName");
    String stationId = (String) session.getAttribute("stationId");
    String img = (String) session.getAttribute("car_img");
    String modelName = (String) session.getAttribute("modelName");
    List<Timestamp[]> availableSlots = (List<Timestamp[]>) request.getAttribute("availableSlots");
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
</head>
<body>
    <header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <h4 id="username"><%= customerName %></h4>
        <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
    </header>

    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="P53.html">予約・ステーション検索</a></li>
            <li class="nav-item gnav03"><a href="P65.html">予約確認・変更・取り消し</a></li>
            <li class="nav-item gnav04"><a href="P74.html">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.html">ご登録情報の確認</a></li>
        </ul>
    </nav>

    <main>
        <h2>該当車種</h2>
        <div class="additional-info-container" id="additionalInfo">
            <div>
                <label><%= modelName %>さん</label>
                <img src="img/<%= img %>" alt="車" />
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
        </div>

        <div class="reservation-inputs">
            <div class="time-group">
                <h3>利用開始情報</h3>
                <label for="startCalendar">利用開始日:</label>
                <select id="startCalendar" name="startCalendar"></select>

                <label for="startTime">利用開始時間:</label>
                <select id="startTime" name="startTime"></select>
            </div>

            <div class="time-group">
                <h3>利用終了情報</h3>
                <label for="endCalendar">利用終了日:</label>
                <select id="endCalendar" name="endCalendar"></select>

                <label for="endTime">利用終了時間:</label>
                <select id="endTime" name="endTime"></select>
            </div>
        </div>

        <div class="button-container" id="actionButtons" style="margin-top: 20px;">
            <button class="back-button" onclick="location.href='P56.jsp'">戻る</button>
            <form action="ReservationCarTime" method="post" style="display:inline;">
                <input type="hidden" name="stationId" value="<%= stationId %>">
                <input type="hidden" name="modelName" value="<%= modelName %>">
                <input type="hidden" name="startCalendar" id="startCalendarInput" value="">
                <input type="hidden" name="startTime" id="startTimeInput" value="">
                <input type="hidden" name="endCalendar" id="endCalendarInput" value="">
                <input type="hidden" name="endTime" id="endTimeInput" value="">
                <button type="submit" class="confirm-button" onclick="setAvailabilityInputs()">空き状況確認</button>
            </form>
        </div>

        <!-- 空き状況表示エリア -->
        <div id="availabilityResults" style="margin-top: 20px;">
            <h2>空いている時間帯</h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>開始時間</th>
                        <th>終了時間</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (availableSlots != null && !availableSlots.isEmpty()) {
                            for (Timestamp[] slot : availableSlots) {
                    %>
                        <tr>
                            <td><%= slot[0] %></td>
                            <td><%= slot[1] %></td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="2">空き状況がありません。</td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </main>

    <script>
        // 今日の日付から1か月間の日付を設定する
        window.onload = function() {
            const startCalendarSelect = document.getElementById('startCalendar');
            const endCalendarSelect = document.getElementById('endCalendar');
            const today = new Date();
            const options = { year: 'numeric', month: '2-digit', day: '2-digit' };

            // 1か月間の日付を追加
            for (let i = 0; i < 30; i++) {
                const date = new Date(today);
                date.setDate(today.getDate() + i);
                const formattedDate = date.toLocaleDateString('ja-JP', options);
                const option = document.createElement('option');
                option.value = formattedDate;
                option.textContent = formattedDate;
                startCalendarSelect.appendChild(option);

                // 終了日も同じリストを使用するため追加
                const endOption = document.createElement('option');
                endOption.value = formattedDate;
                endOption.textContent = formattedDate;
                endCalendarSelect.appendChild(endOption);
            }

            // 時間の選択肢を追加
            addTimeOptions();
            fetchAvailableSlots(); // ページ読み込み時に空き状況を取得
        };

        function addTimeOptions() {
            const startTimeSelect = document.getElementById('startTime');
            const endTimeSelect = document.getElementById('endTime');

            for (let hour = 0; hour < 24; hour++) {
                for (let minute of [0, 15, 30, 45]) {
                    const timeValue = String(hour).padStart(2, '0') + ':' + String(minute).padStart(2, '0');
                    const startOption = document.createElement('option');
                    startOption.value = timeValue;
                    startOption.textContent = timeValue;
                    startTimeSelect.appendChild(startOption);

                    const endOption = document.createElement('option');
                    endOption.value = timeValue;
                    endOption.textContent = timeValue;
                    endTimeSelect.appendChild(endOption);
                }
            }
        }

        function fetchAvailableSlots() {
            const startDate = new Date();
            const endDate = new Date();
            endDate.setHours(startDate.getHours() + 1); // 1時間後までの空き状況を取得

            const apiUrl = `https://api.example.com/availableSlots?start=${startDate.toISOString()}&end=${endDate.toISOString()}`;

            fetch(apiUrl)
                .then(response => {
                    if (!response.ok) throw new Error('Network response was not ok');
                    return response.json();
                })
                .then(data => {
                    const availabilityResults = document.getElementById('availabilityResults');
                    const tableBody = availabilityResults.querySelector('tbody');
                    tableBody.innerHTML = ''; // 既存の行をクリア

                    if (data.length > 0) {
                        data.forEach(slot => {
                            const row = document.createElement('tr');
                            const startCell = document.createElement('td');
                            const endCell = document.createElement('td');
                            
                            // APIからのデータ形式に応じて調整
                            startCell.textContent = new Date(slot.start).toLocaleString('ja-JP');
                            endCell.textContent = new Date(slot.end).toLocaleString('ja-JP');
                            
                            row.appendChild(startCell);
                            row.appendChild(endCell);
                            tableBody.appendChild(row);
                        });
                    } else {
                        const row = document.createElement('tr');
                        const cell = document.createElement('td');
                        cell.colSpan = 2;
                        cell.textContent = '空き状況がありません。';
                        row.appendChild(cell);
                        tableBody.appendChild(row);
                    }
                })
                .catch(error => console.error('Error fetching available slots:', error));
        }

        function setAvailabilityInputs() {
            // フォームの隠しフィールドに値を設定
            document.getElementById('startCalendarInput').value = document.getElementById('startCalendar').value;
            document.getElementById('startTimeInput').value = document.getElementById('startTime').value;
            document.getElementById('endCalendarInput').value = document.getElementById('endCalendar').value;
            document.getElementById('endTimeInput').value = document.getElementById('endTime').value;
        }
    </script>
</body>
</html>
