<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.sql.SQLException"%>
<%@page import="model.Customer"%>
<%@page import="java.util.Base64"%>
<%
    Customer customer = (Customer) session.getAttribute("customer");

    String customerName = customer.getCustomerName();
    String customerKana = customer.getCustomerKana();
    String gender = customer.getGender();
    String email = customer.getEmail();
    String tellNumber = customer.getTellNumber();
    String customerAddress = customer.getCustomerAddress();
    String birthDate = customer.getBirthDate();
    String licenseNumber = customer.getLicenseNumber();
    String licenseDate = customer.getLicenceDate();

    // Blobをbyte[]に変換してBase64エンコード
    String omoteBase64 = null;
    String uraBase64 = null;
    Blob omote = customer.getOmote();
    Blob ura = customer.getUra();
    try {
        if (omote != null) {
            byte[] omoteBytes = omote.getBytes(1, (int) omote.length());
            omoteBase64 = Base64.getEncoder().encodeToString(omoteBytes);
        }
        if (ura != null) {
            byte[] uraBytes = ura.getBytes(1, (int) ura.length());
            uraBase64 = Base64.getEncoder().encodeToString(uraBytes);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // エラーメッセージを表示する場合
        omoteBase64 = "画像の取得に失敗しました";
        uraBase64 = "画像の取得に失敗しました";
    }
%>

<!--基本情報の確認ページ-->
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/P20.css"> 
</head>
<body>

<header>
    <div class="logo">
        <img src="img/rog.png" alt="TMC Logo">
        <h1>TMC カーシェア</h1>
    </div>
    <a href="P29.html" class="btn">予約・ログイン ▶</a>
</header>

<div class="step-container">
    <div class="step">
        <div class="step-icon">📝</div>
        <div class="step-label">詳細・規約のご確認</div>
    </div>
    <div class="step current">
        <div class="step-icon">🖊️</div>
        <div class="step-label">基本情報のご入力</div> 
    </div>
    <div class="step confirmation">
        <div class="step-icon">🔍</div>
        <div class="step-label">ご入力基本情報のご確認</div>
    </div>
    <div class="step credit">
        <div class="step-icon">💳</div>
        <div class="step-label">クレジットカード情報</div>
    </div>
    <div class="step">
        <div class="step-icon">✅</div>
        <div class="step-label">申し込み完了</div>
    </div>
</div>

<div class="form-container">
    <div class="form-wrapper">
        <div class="form-group">
            <h1>基本情報の確認</h1>
            <div class="form-section">
                <label for="fullname">氏名</label>
                <div class="input-container"><%= customerName %></div>
                <label for="fullname-kana">氏名(フリガナ)</label>
                <div class="input-container"><%= customerKana %></div>
                <label for="address">住所</label>
                <div class="input-container"><%= customerAddress %></div>
                <label for="TEL">携帯電話番号</label>
                <div class="input-container"><%= tellNumber %></div>
                <label for="licence">免許証情報</label>
                <div class="form-licence">
                    <label for="type">免許証番号</label>
                    <div class="input-container"><%= licenseNumber %></div>
                    <label for="deadline">有効期限</label>
                    <div class="input-container"><%=licenseDate %>
                    </div>
                    <label for="email">メールアドレス</label>
                    <div class="input-container"><%= email %></div>
                </div>
            </div>
            <div class="button-container">
                <input type="button" value="入力へ戻る" onclick="location.href='P6.jsp'">
                <form action="P22.jsp" method="post">
                    <input type="hidden" name="customerName"    value="<%= customerName %>">
                    <input type="hidden" name="customerKana"    value="<%= customerKana %>">
                    <input type="hidden" name="gender"          value="<%= gender %>">
                    <input type="hidden" name="email"           value="<%= email %>">
                    <input type="hidden" name="tellNumber"      value="<%= tellNumber %>">
                    <input type="hidden" name="customerAddress" value="<%= customerAddress %>">
                    <input type="hidden" name="birthDate"       value ="<%= birthDate %>">
                    <input type="hidden" name="licenseNumber"   value="<%= licenseNumber %>">
                    <input type="hidden" name="licenseDate"     value="<%= licenseDate %>">
                    <input type="hidden" name="omoteImage"      value="<%= omoteBase64 %>">
                    <input type="hidden" name="uraImage"        value="<%= uraBase64 %>">
                    <input type="submit" value="クレジット情報のご入力へ">
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
