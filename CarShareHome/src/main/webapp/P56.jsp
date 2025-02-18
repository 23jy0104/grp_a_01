<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%
    String customerName = (String) session.getAttribute("customerName");
    String stationIdValue = request.getParameter("stationid");
    String stationNameValue = request.getParameter("stationname");
    String stationDataValue = request.getParameter("stationdata");

    // 現在の日時を取得
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE, 30);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    String minStartDateTime = dateFormat.format(calendar.getTime());

    // セッションに値を設定
    session.setAttribute("stationId", stationIdValue);
    session.setAttribute("stationdata", stationDataValue);
    session.setAttribute("stationName", stationNameValue);
    
    // ステーション情報の取得
    List<String[]> stations = (List<String[]>) session.getAttribute("stations");
    String[][] stationsArray = null;

    if (stations != null) {
        // Listから配列に変換
        stationsArray = new String[stations.size()][];
        stationsArray = stations.toArray(stationsArray);
    }
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア - ステーション情報</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/P56.css">
    <link rel="stylesheet" href="css/timeTable.css">
    <style>
          .reservation-inputs {
            display: flex;
            flex-direction: column;
            gap: 20px; /* 各項目の間隔 */
        }
        .time-group {
            display: flex;
            flex-direction: column;
            gap: 10px; /* 各フィールドの間隔 */
            border: 1px solid #ccc; /* 枠線 */
            padding: 10px; /* パディング */
            border-radius: 5px; /* 角を丸く */
            background-color: #f9f9f9; /* 背景色 */
        }
        .flex-container {
            display: flex;
            justify-content: space-between;
            margin-top: 10px; /* 上下の間隔を縮小 */
        }
        .flex-item {
            flex: 1; /* 各アイテムを均等に配置 */
            margin: 0 5px; /* アイテム間の間隔を縮小 */
        }
        .button-container {
            text-align: center; /* ボタンを中央揃え */
        }
        /* Selectボックスの幅を広くする */
        select {
            width: 100%; /* 親要素の幅に合わせて広くする */
            padding: 8px; /* パディングを追加 */
            border: 1px solid #ccc; /* 枠線 */
            border-radius: 4px; /* 角を少し丸く */
            font-size: 16px; /* フォントサイズを調整 */
        }
    </style>
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
            <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
            <li class="nav-item gnav03"><a href="P65.jsp">予約確認・変更・取り消し</a></li>
            <li class="nav-item gnav04"><a href="P74.jsp">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>

    <main>
        <h2>ステーション情報</h2>
        <button class="back-button" onclick="location.href='P55.jsp'">検索結果一覧に戻る</button>
        <br>
        <% if (stationsArray != null && stationsArray.length > 0) { %>
            <h3><%= stationsArray[0][1] %></h3> <!-- ステーション名 -->
            <table>
                <tr>
                    <th>お知らせ</th>
                    <th>注意点:<br>
                        ・ご乗車の際は、必ずご自身で安全点検をしてください。<br>
                        ・全車禁煙です。喫煙される場合は、クルマをとめ、喫煙設備のある場所でお願いします。<br>
                        ・車内で出た飲み物,食べ物などのゴミは、必ず各自でお持ち帰りください。<br><br>
                        アクセス:<br>
                        大久保駅北口から北西に進んでいただき、大久保通りをまっすぐ進んだ場所にございます。
                    </th>
                </tr>
                <tr>
                    <th>ステーション情報</th>
                    <th><%= stationsArray[0][3] %></th> <!-- ステーションデータ -->
                </tr>
            </table>
        <% } else { %>
            <h3>ステーション情報が見つかりませんでした。</h3>
        <% } %>
        
        <div class="flex-container">
            <div class="flex-item">
                <h3>空き情報から探す</h3>
                <div class="reservation-inputs">
                    <div class="time-group">
                        <label for="startDate">利用開始日:</label>
                        <select id="startDate" name="startDate"></select>

                        <label for="startTime">利用開始時間:</label>
                        <select id="startTime" name="startTime"></select>
                    </div>

                    <div class="time-group">
                        <label for="endDate">利用終了日:</label>
                        <select id="endDate" name="endDate"></select>

                        <label for="endTime">利用終了時間:</label>
                        <select id="endTime" name="endTime"></select>
                    </div>

                    <!-- 検索ボタン -->
                    <div class="button-container">
                        <a href="P57.jsp">
                            <input type="image" id="searchButton1" src="img/kensaku.gif" alt="検索" />
                        </a>
                    </div>
                </div>
            </div>
            <div class="flex-item">
                <h3 class="sub-title">車種から探す</h3>
                <form action="ReservationCar" method="post">
                    <input type="hidden" name="stationId" value="<%= stationIdValue %>">
                    <input type="hidden" name="stationName" value="<%= stationNameValue %>">
                
                    <select id="carType" name="carType">
                        <option value="BNR32型 skyline Nismo">BNR32型 skyline Nismo</option>
                        <option value="NSX NA-1型 type-R">NSX NA-1型 type-R</option>
                        <option value="GT-R R35 Nismo Special Edition T-spec">GT-R R35 Nismo Special Edition T-spec</option>
                    </select>      
                    <!-- 車種選択後の検索ボタン -->
                    <div class="button-container">
                        <input type="image" id="searchButton2" src="img/kensaku.gif" alt="検索" />
                    </div>
                </form>
            </div>
        </div>
    </main>

    <script>
        // 今日の日付から1か月間の日付を設定する
       window.onload = function() {
		    const startDateSelect = document.getElementById('startDate');
		    const endDateSelect = document.getElementById('endDate');
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
		        startDateSelect.appendChild(option);
		
		        // 終了日も同じリストを使用するため追加
		        const endOption = document.createElement('option');
		        endOption.value = formattedDate;
		        endOption.textContent = formattedDate;
		        endDateSelect.appendChild(endOption);
		    }
		
		    // 時間の選択肢を追加
		    addTimeOptions();
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
    </script>
</body>
</html>