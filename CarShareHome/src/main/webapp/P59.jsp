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
    String stationName = (String) session.getAttribute("stationName"); // 追加
    String stationCode = (String) session.getAttribute("stationCode"); // 追加
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
            justify-content: center; /* 中央配置 */
            flex-wrap: wrap; /* 複数カレンダーの場合に折り返す */
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
            width: 60px; /* マス目の幅を大きく */
            height: 60px; /* マス目の高さを大きく */
            display: flex;
            justify-content: center;
            align-items: center;
            cursor: pointer;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 18px; /* フォントサイズを調整 */
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

        /* タイムテーブルのスタイル */
        .timetable {
            display: none; /* 初期状態では非表示 */
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
            background-color: red; /* 予約不可の色 */
            color: white;
            cursor: not-allowed;
        }

        .available {
            background-color: blue; /* 予約可能の色 */
            color: white;
            cursor: pointer;
        }

        .selected {
            background-color: green; /* 選択した時間の色 */
        }

        .header-container {
            display: flex;
            align-items: center; /* 縦方向の中央揃え */
            justify-content: space-between; /* 左右にスペースを均等に配置 */
            margin-bottom: 20px; /* 下に余白を追加 */
        }

        .back-button {
            margin-left: 10px; /* ボタンに少し余白を追加 */
        }

        .button-container {
            margin-top: 10px; /* ボタンの上に少し余白を追加 */
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
            <!-- 戻るボタンはここに配置 -->
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
        <div class="calendar-container"> <!-- カレンダーを囲むコンテナ -->
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
                    String className = isDisabled ? "day disabled" : "day";
                    
                    out.println("<div class='" + className + "' " + (isDisabled ? "" : "onclick='selectDate(\"" + dateStr + "\")'") + ">" + day + "</div>");
                }

                out.println("</div></div>");
            }
        %>
        </div> <!-- カレンダーを囲むコンテナの終了 -->

        <!-- 戻るボタンをカレンダーの下に配置 -->
                <!-- 戻るボタンをカレンダーの下に配置 -->
        <div class="button-container">
            <button class="back-button" onclick="location.href='P56.jsp'">戻る</button> <!-- 戻るボタン -->
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
    // 選択された時間を保持する配列
    const selectedTimes = [];

    function selectDate(date) {
        document.getElementById("timetable-title").textContent = date + " のタイムテーブル";
        const timetableBody = document.getElementById("timetable-body");
        timetableBody.innerHTML = ""; 
        // 既存の行をクリア

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
                const isReserved = (hour % 2 === 0 && quarter === 0); // 例: 偶数時間の最初の15分を予約済みとする

                // 背景色を設定
                if (isReserved) {
                    statusCell.className = "reserved"; // 予約済みの場合、赤色背景
                } else {
                    statusCell.className = "available"; // 空きの場合、青色背景
                    statusCell.style.cursor = "pointer"; // 空きの場合、クリック可能に

                    // 時間のクリックイベント
                    statusCell.onclick = function() {
                        const selectedTime = hour * 15 + quarter; // 15分単位の時間を計算
                        const timeDisplay = (hour < 10 ? "0" : "") + hour + ":" + (quarter * 15 < 10 ? "0" : "") + (quarter * 15); // フォーマットを作成
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

