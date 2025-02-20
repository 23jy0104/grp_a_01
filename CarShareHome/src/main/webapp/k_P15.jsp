<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Customer"%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>顧客情報登録画面</title>
    <link rel="stylesheet" href="css/vehicleCon.css">  
    <script>
    function sendLicenseInfo() {
        const form = document.getElementById('licenseForm');
        const formData = new FormData(form);
        
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "processLicenseInfo", true);
        xhr.setRequestHeader("Accept", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    const response = JSON.parse(xhr.responseText);
                    document.getElementById("result").innerText = response.result === 0 ? "判定OK" : "判定NG";
                    document.getElementById("managerCheck").innerText = response.managerCheck;
                } else {
                    console.error("Error occurred: " + xhr.status + " " + xhr.statusText);
                }
            }
        };

        xhr.send(formData); // フォームデータを送信
    }

    </script>
</head>
<body>
    <h1>顧客情報登録画面</h1>
    <table>
        <tr>
            <th>顧客申請者名</th>
            <td><%= request.getAttribute("customerName") != null ? request.getAttribute("customerName") : "未設定" %></td>
        </tr>
        <tr>
            <th>顧客ID</th>
            <td><%= request.getAttribute("customerId") != null ? request.getAttribute("customerId") : "未設定" %></td>
        </tr>
        <tr>
            <th>電話番号</th>
            <td><%= request.getAttribute("tellNumber") != null ? request.getAttribute("tellNumber") : "未設定" %></td>
        </tr>
        <tr>
            <th>メールアドレス</th>
            <td><%= request.getAttribute("email") != null ? request.getAttribute("email") : "未設定" %></td>
        </tr>
        <tr>
            <th>運転免許証番号</th>
            <td><%= request.getAttribute("licenseNumber") != null ? request.getAttribute("licenseNumber") : "未設定" %></td>
        </tr>
        <tr>
            <th>有効期限</th>
            <td><%= request.getAttribute("licenceDate") != null ? request.getAttribute("licenceDate") : "未設定" %></td>
        </tr>
        <tr>
            <th>郵便番号</th>
            <td><%= request.getAttribute("postCode") != null ? request.getAttribute("postCode") : "未設定" %></td>
        </tr>
        <tr>
            <th>住所</th>
            <td><%= request.getAttribute("customerAddress") != null ? request.getAttribute("customerAddress") : "未設定" %></td>
        </tr>
        <tr>
            <th>運転免許証画像(表)</th>
            <td><%= request.getAttribute("omoteJpg") != null ? request.getAttribute("omoteJpg") : "未設定" %></td>
        </tr>
        <tr>
            <th>運転免許証画像(裏)</th>
            <td><%= request.getAttribute("uraJpg") != null ? request.getAttribute("uraJpg") : "未設定" %></td>
        </tr>
    </table>

    <div class="button-container">
    	<% customerId = <%= request.getAttribute("customerId") %> %>>
        <form id="licenseForm" onsubmit="sendLicenseInfo(); return false;">
            <input type="hidden" name="customerId" value="<%= customerId%>">
            <button type="submit" class="send">免許証情報を送る</button>
        </form>
    </div>

    <div id="resultSection" style="margin-top: 20px;">
        <h2>判定結果</h2>
        <p>判定: <span id="result"></span></p>
        <p>管理者チェック: <span id="managerCheck"></span></p>
    </div>
</body>
</html>
