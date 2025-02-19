<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.CarInfo"%>
<%@page import="model.Station"%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>車両管理システム</title>
    <link rel="stylesheet" href="css/vehicleAdd.css">  
    <style>
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px auto;
            border: 1px solid #ccc;
            background-color: #fff;
        }
        td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
            white-space: nowrap;
        }
    </style>
</head>
<body>
    <% 
        // ステーション名と住所の取得
        String stationName = (String) request.getAttribute("stationName");
        String stationAddress = (String) request.getAttribute("stationAddress");

        System.out.println("ステーション名: " + (stationName != null ? stationName : "未設定"));
        System.out.println("ステーション住所: " + (stationAddress != null ? stationAddress : "未設定"));
    %>
    <h1><%= stationName != null ? stationName : "ステーション名がありません" %></h1>
    <p>住所: <%= stationAddress != null ? stationAddress : "住所がありません" %></p>

    <div class="input-container">
        <div class="select-container">
            ナンバープレート　　<select id="plate">
                <option value="">--選択してください--</option>
                <% 
                    List<CarInfo> carInfoListFromRequest = (List<CarInfo>) request.getAttribute("carInfoList");
                    if (carInfoListFromRequest != null) {
                        for (CarInfo carInfo : carInfoListFromRequest) {
                            String number = carInfo.getNumber();
                            System.out.println("ナンバープレート: " + number);
                %>
                <option value="<%= number %>"><%= number %></option>
                <%
                        }
                    } else {
                %>
                <option value="">ナンバープレート情報がありません</option>
                <%
                    }
                %>
            </select>
        </div>
        <div class="button-container">
            <form action="k_P7Servlet" method="post">
                <input type="hidden" name="selectedPlate" id="selectedPlate">
                <button type="button" onclick="submitForm()">検索</button>
            </form>
        </div>
    </div>

    <div id="result-container" style="display: none;">
        <div id="result-table-container"></div>
        <div class="button-container">
            <button onclick="register()">登録する</button>
        </div>
    </div>

    <script>
        function submitForm() {
            const plate = document.getElementById('plate').value;
            if (plate) {
                document.getElementById('selectedPlate').value = plate;
                document.forms[0].submit(); // フォームを送信
            } else {
                alert('ナンバープレートを選択してください。');
            }
        }
    </script>

    <% 
        CarInfo carInfo = (CarInfo) request.getAttribute("carInfo");
        if (carInfo != null) {
    %>
        <div id="result-container">
            <table>
                <tr>
                    <td>車両メーカー</td>
                    <td><%= carInfo.getMakerName() %></td>
                </tr>
                <tr>
                    <td>車両モデル</td>
                    <td><%= carInfo.getModelName() %></td>
                </tr>
                <tr>
                    <td>年式</td>
                    <td><%= carInfo.getModelYear() %></td>
                </tr>
                <tr>
                    <td>ナンバープレート</td>
                    <td><%= carInfo.getNumber() %></td>
                </tr>
            </table>
        </div>
    <% 
        } else {
    %>
        <p>選択されたナンバープレートに対する情報が見つかりませんでした。</p>
    <% 
        }
    %>

    <script>
        function register() {
            window.location.href = "P10.html"; // 登録処理（ページ遷移）
        }
    </script>
</body>
</html>
