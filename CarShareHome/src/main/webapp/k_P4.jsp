<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Station" %>

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
                // リクエスト属性からステーションリストを取得
                List<Station> stations = (List<Station>) request.getAttribute("stations");
                String address = request.getParameter("address"); 

                if (stations != null && !stations.isEmpty()) {
                    for (Station station : stations) {
            %>
            <tr>
                <td>
                    <a href="k_P7Servlet?stationName=<%= station.getStationName() %>&stationAddress=<%= station.getStationAddress() %>">
                        <%= station.getStationName() %>
                    </a>
                </td>
                <td><%= station.getStationAddress() %></td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="2">ステーションが見つかりませんでした。</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <script>
        document.getElementById('searchBtn').addEventListener('click', function() {
            const address = document.getElementById('address').value; 

            if (!address) {
                return;
            }

            fetch('getStations?address=' + encodeURIComponent(address))
                .then(response => {
                    if (!response.ok) {
                        throw new Error('HTTPエラー: ' + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    const resultsBody = document.getElementById('resultsBody');
                    resultsBody.innerHTML = '';

                    if (Array.isArray(data) && data.length > 0) {
                        data.forEach(station => {
                            const row = document.createElement('tr');
                            row.innerHTML = '<td>' +
                                '<a href="k_P7.jsp?stationName=' + encodeURIComponent(station.station_name) + 
                                '&stationAddress=' + encodeURIComponent(station.station_address) + '">' + 
                                station.station_name + 
                                '</a>' +
                                '</td>' +
                                '<td>' + station.station_address + '</td>';
                            resultsBody.appendChild(row);
                        });
                        document.getElementById('resultsTable').style.display = 'table';
                    } else {
                        document.getElementById('resultsTable').style.display = 'none';
                    }
                })
                .catch(error => {
                });
        });
    </script>

    <button class="logout" onclick="location.href='k_top.jsp'">サインアウト</button>

</body>
</html>
