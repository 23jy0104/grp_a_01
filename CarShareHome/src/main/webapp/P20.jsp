<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Customer"%>
<!--基本情報の確認ページ-->
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="../../CSS/P20.css"> 
</head>
<body>

<header>
    <div class="logo">
        <img src="../../img/rog.png" alt="TMC Logo">
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
    <div class="step">
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
                    <div class="input-container">${customer.customerName }</div>
                    
                    <label for="fullname-kana">氏名(フリガナ)</label>
                    <div class="input-container">${customer.customerNameKana }</div>
                    
                    <label for="address">住所</label>
                    <div class="input-container">${customer.customerAddress }</div>
                    
                    <label for="TEL">携帯電話番号</label>
                    <div class="input-container">${customer.phoneNumber }</div>
                    
                    <label for="licence">免許証情報</label>
                    <div class="form-licence">
                        <label for="type">免許証番号</label>
                        <div class="input-container">${customer.licenseNumber }</div>
                        
                        <label for="deadline">有効期限</label>
                        <div class="input-container">${customer.licenseDate }</div>
                        
                        <label for="email">メールアドレス</label>
                        <div class="input-container">${customer.eMail }</div>
                    </div>
                </div>
            <%
                }
            %>
            <div class="button-container">
                <input type="button" value="入力へ戻る" onclick="location.href='P6.html'">
                <input type="button" value="クレジット情報のご入力へ" onclick="location.href='P22.html'">
            </div>
        </div>
    </div>
</div>

</body>
</html>
