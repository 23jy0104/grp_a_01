<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Station" %>
<%@ page import="model.CarData" %>
<%@ page import="model.KeyBox" %>
<%@ page import="model.Model" %>
<%@ page import="java.util.Locale" %>

<%
    String customerName = (String) session.getAttribute("customerName");
    String stationId = (String) session.getAttribute("stationId");
    String img = (String) session.getAttribute("car_img");
    String modelName = (String) session.getAttribute("modelName");
    String stationName = (String) session.getAttribute("stationName");
    String stationCode = (String) session.getAttribute("stationCode");
    List<Timestamp[]> availableSlots = (List<Timestamp[]>) request.getAttribute("availableSlots");

    // カレンダーの日付処理
    Calendar today = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar oneMonthLater = Calendar.getInstance();
    oneMonthLater.add(Calendar.MONTH, 1);
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
        /* 既存のCSSスタイルはそのまま */
        .calendar-container {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
        }

        .calendar {
            display: inline-block;
            margin: 10px;
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
        }

        .disabled {
            background-color: lightgray;
            cursor: not-allowed;
        }

        .header {
            display: grid;
            grid-template-columns: repeat(7, 1fr);
            font-weight: bold;
            margin-bottom: 5px;
        }

        .header div {
            text-align: center;
        }

        .timetable {
            display: none;
            margin-top: 20px;
        }

        .timetable table {
            width: 100%;
            border-collapse: collapse;
        }

        .timetable th, .timetable td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }

        .reserved {
            background-color: red;
            color: white;
            cursor: not-allowed;
        }

        .available {
            background-color: blue;
            color: white;
            cursor: pointer;
        }

        .selected {
            background-color: green;
        }

        .header-container {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .back-button {
            margin-left: 10px;
        }

        .button-container {
            margin-top: 10px;
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

        <h2>予約したい日付をクリックしてください。</h2>
        <br>
        <div class="calendar-container">
        <%
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

                for (int i = 1; i < firstDayOfWeek; i++) {
                    out.println("<div class='day disabled'></div>");
                }

                for (int day = 1; day <= daysInMonth; day++) {
                    monthCalendar.set(Calendar.DAY_OF_MONTH, day);
                    String dateStr = sdf.format(monthCalendar.getTime());

                    boolean isDisabled = monthCalendar.before(today) || monthCalendar.after(oneMonthLater);
                    String className = isDisabled ? "day disabled" : "day";
                    
                    out.println("<div class='" + className + "' " + (isDisabled ? "" : "onclick='selectDate(\"" + dateStr + "\")'") + ">" + day + "</div>");
                }

                out.println("</div></div>");
            }
        %>
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
            </form>
        </div>
        <!-- タイムテーブルの表示 -->
        <div class="timetable" id="timetable">
            <h3 id="timetable-title">選択した日付のタイムテーブル</h3>
            <table>
                <tbody id="timetable-body">
                    <!-- JavaScriptで動的に行を追加 -->
                </tbody>
            </table>
        </div>

        <!-- 予約ボタン -->
        <div id="reserve-button-container" style="display: none; margin-top: 20px;">
            <button id="reserve-button" onclick="reserve()">予約する</button>
        </div>

        <script>
            const selectedTimes = [];

            function selectDate(date) {
                // 選択した日付を隠しフィールドに設定
                document.getElementById("startCalendarInput").value = date + " 00:00:00";
                document.getElementById("endCalendarInput").value = date + " 23:59:59";

                // タイムテーブルのタイトルを設定
                document.getElementById("timetable-title").textContent = date + " のタイムテーブル";
                const timetableBody = document.getElementById("timetable-body");
                timetableBody.innerHTML = ""; 

                // 1時間単位の時間を表示
                const timeRow = document.createElement("tr");
                for (let hour = 0; hour < 24; hour++) {
                    const timeCell = document.createElement("th");
                    timeCell.colSpan = 4; // 15分単位で4つのセルをまとめる
                    timeCell.textContent = (hour < 10 ? "0" : "") + hour + ":00"; // 1時間単位で表示
                    timeRow.appendChild(timeCell);
                }
                timetableBody.appendChild(timeRow);

                const statusRow = document.createElement("tr");
                for (let hour = 0; hour < 24; hour++) {
                    for (let quarter = 0; quarter < 4; quarter++) {
                        const statusCell = document.createElement("td");
                        const isReserved = checkIfReserved(hour, quarter); // 予約済みかどうかをチェックする関数を作成

                        // 背景色を設定
                        if (isReserved) {
                            statusCell.className = "reserved"; // 予約済みの場合、赤色背景
                        } else {
                            statusCell.className = "available"; // 空きの場合、青色背景
                            statusCell.style.cursor = "pointer"; // 空きの場合、クリック可能に

                            // 時間のクリックイベント
                            statusCell.onclick = function() {
                                const selectedTime = hour * 15 + quarter; // 15分単位の時間を計算
                                const selectedIndex = selectedTimes.indexOf(selectedTime);
                                
                                if (selectedIndex > -1) {
                                    // 既に選択されている場合は解除
                                    selectedTimes.splice(selectedIndex, 1);
                                    statusCell.classList.remove("selected");
                                } else {
                                    // 未選択の場合は選択
                                    selectedTimes.push(selectedTime);
                                    statusCell.classList.add("selected");
                                }

                                // 予約ボタンの表示/非表示
                                document.getElementById("reserve-button-container").style.display = selectedTimes.length > 0 ? "block" : "none";
                            };
                        }

                        statusRow.appendChild(statusCell);
                    }
                }
                timetableBody.appendChild(statusRow);

                // タイムテーブルを表示
                document.getElementById("timetable").style.display = "block";

                // フォームをサーブレットに送信する場合
                // 例: 予約状況を確認するボタンを自動的にクリック
                // ここではボタンをクリックする代わりに、タイムテーブルを表示するためにサーブレットにデータを送信するように調整できます
                document.querySelector('form[action="ReservationCarTime"]').submit();
            }

            function checkIfReserved(hour, quarter) {
                // サーブレットから取得したデータを用いて予約済みかどうかを判定するロジックを実装
                // ここでは仮のデータとして1時間の最初の15分を予約済みとしています
                return (hour % 2 === 0 && quarter === 0); // 例として
            }

            function reserve() {
                if (selectedTimes.length === 0) {
                    alert("予約する時間を選択してください。");
                    return;
                }

                // 予約処理のロジックをここに追加
                alert("予約を完了しました。選択した時間: " + selectedTimes.map(t => {
                    const hour = Math.floor(t / 4);
                    const minutes = (t % 4) * 15;
                    return (hour < 10 ? "0" : "") + hour + ":" + (minutes < 10 ? "0" : "") + minutes; // 時間を表示
                }).join(", "));
                
                // 必要に応じて、サーバーに予約情報を送信する処理を追加することができます。
                // 例えば、AJAXを使用してサーバーにリクエストを送るなど。

                // 予約完了後、選択をクリア
                selectedTimes.length = 0;
                document.getElementById("reserve-button-container").style.display = "none"; // ボタンを非表示に
                const timetableBody = document.getElementById("timetable-body");
                timetableBody.innerHTML = ""; // タイムテーブルをクリア
                document.getElementById("timetable").style.display = "none"; // タイムテーブルを非表示に
            }
        </script>

    </main>
</body>
</html>
