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

   	<div class="input-container">
    <div class="select-container">
        ナンバープレート　　<select id="plate">
            <option value="">--選択してください--</option>
            <% 
                List<CarInfo> carInfoListFromRequest = (List<CarInfo>) request.getAttribute("carInfoList");
                if (carInfoListFromRequest != null) {
                    for (CarInfo carInfo : carInfoListFromRequest) {
                        String number = carInfo.getNumber();
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
        <form id="searchForm" action="CarInfoServlet" method="post">
            <input type="hidden" name="selectedPlate" id="selectedPlate">
            <input type="hidden" name="stationName" value="<%= stationName != null ? stationName : "" %>">
            <input type="hidden" name="stationAddress" value="<%= stationAddress != null ? stationAddress : "" %>">
            <button type="button" onclick="submitForm()">検索</button>
        </form>
    </div>
</div>


    <div id="result-container">
        <% 
            CarInfo carInfo = (CarInfo) request.getAttribute("carInfo");
            if (carInfo != null) {
        %>
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
            <div class="button-container">
                <button onclick="register()">登録する</button>
            </div>
        <% 
            } else {
                // 車両情報がない場合の処理
            }
        %>
    </div>

    <script>
	    function submitForm() {
	        const plate = document.getElementById('plate').value;
	        if (plate) {
	            document.getElementById('selectedPlate').value = plate;
	            document.getElementById('searchForm').submit(); // フォームを送信
	        } else {
	            alert('ナンバープレートを選択してください。');
	        }
	    }

        function register() {
            const plate = "<%= carInfo != null ? carInfo.getNumber() : "" %>";
            const stationName = "<%= stationName != null ? stationName : "" %>";
            const stationAddress = "<%= stationAddress != null ? stationAddress : "" %>";
            const makerName = "<%= carInfo != null ? carInfo.getMakerName() : "" %>";
            const modelName = "<%= carInfo != null ? carInfo.getModelName() : "" %>";
            const modelYear = "<%= carInfo != null ? carInfo.getModelYear() : "" %>";

            if (plate) {
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = 'RegisterCarServlet'; // サーブレットのURL

                // 各パラメータをフォームに追加
                form.appendChild(createHiddenInput('selectedPlate', plate));
                form.appendChild(createHiddenInput('stationName', stationName));
                form.appendChild(createHiddenInput('stationAddress', stationAddress));
                form.appendChild(createHiddenInput('makerName', makerName));
                form.appendChild(createHiddenInput('modelName', modelName));
                form.appendChild(createHiddenInput('modelYear', modelYear));

                document.body.appendChild(form);
                form.submit(); // フォームを送信
            } else {
                alert('ナンバープレートを選択してください。');
            }
        }

        function createHiddenInput(name, value) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = name;
            input.value = value;
            return input;
        }
    </script>
</body>
</html>
