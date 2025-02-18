<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.ReservationDAO" %>
<%@ page import="model.Station" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ステーション検索</title>
    <link rel="stylesheet" href="css/search.css">    
</head>
<body>

    <h1>ステーション検索</h1>
    <div>
        <input type="text" id="address" placeholder="住所を入力してください">
        <button id="searchBtn">検索</button>
    </div>

    <table id="resultsTable" style="display: none;">
        <thead>
            <tr>
                <th>ステーション名</th>
                <th>住所</th>
            </tr>
        </thead>
        <tbody id="resultsBody">
            <%
                // 初期データをリクエストから取得
                List<Station> stations = (List<Station>) request.getAttribute("stations");
                if (stations != null) {
                    for (Station station : stations) {
            %>
                        <tr>
                            <td><%= station.getStationName() %></td>
                            <td><%= station.getStationAddress() %></td>
                        </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>

    <script>
        document.getElementById('searchBtn').addEventListener('click', function() {
            const address = document.getElementById('address').value.trim(); // 空白を削除
            const resultsBody = document.getElementById('resultsBody');
            resultsBody.innerHTML = ''; // 既存の結果をクリア

            console.log("検索する住所:", address); // デバッグ出力

            // AJAXリクエストを送信
            fetch('getStations') // サーブレットのURL
                .then(response => {
                    if (!response.ok) {
                        throw new Error('ネットワークエラー: ' + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log("取得したステーション:", data); // 取得したデータを確認

                    // 部分検索に対応
                    const filteredStations = data.filter(station => 
                        station.station_address && station.station_address.includes(address)
                    );

                    console.log("フィルタリングされたステーション:", filteredStations); // フィルタリング後のデータを確認

                    // 結果をテーブルに追加
                    if (filteredStations.length === 0) {
                        console.log("フィルタリング結果が空です。"); // フィルタリング結果が空の場合のデバッグ出力
                    }

                    filteredStations.forEach(station => {
                        const row = document.createElement('tr');
                        row.innerHTML = `<td>${station.station_name}</td><td>${station.station_address}</td>`;
                        resultsBody.appendChild(row);
                    });

                    // 結果があればテーブルを表示
                    if (filteredStations.length > 0) {
                        document.getElementById('resultsTable').style.display = 'table';
                    } else {
                        document.getElementById('resultsTable').style.display = 'none';
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('エラーが発生しました: ' + error.message); // アラートでエラーメッセージを表示
                });
        });
    </script>
    
    <button class="logout" onclick="location.href='k_top.jsp'">サインアウト</button>

</body>
</html>
