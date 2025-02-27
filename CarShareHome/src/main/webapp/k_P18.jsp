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
        window.location.href = "k_P13Servlet"; // サーブレットにリダイレクト
    }
    </script>
</head>
<body>
    <h1>顧客登録完了画面</h1>
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
        <button onclick="sendLicenseInfo()" class="send">申請一覧に戻る</button>
    </div>
</body>
</html>
