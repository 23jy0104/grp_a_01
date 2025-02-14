<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--基本情報の入力-->

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/credit.css">
    <style>
        .error-message {
            color: red;
            display: none; /* 初期は非表示 */
        }
    </style>
    <script>
        function toggleButton() {
            const checkbox = document.getElementById('myCheckbox');
            const button = document.getElementById('confirmButton');
            button.disabled = !checkbox.checked;

            // チェックが入っている場合、オレンジ色のスタイルを適用
            if (checkbox.checked) {
                button.style.backgroundColor = 'orange';
            } else {
                button.style.backgroundColor = ''; // デフォルトの色に戻す
                button.style.color = ''; // デフォルトの文字色に戻す
            }
        }

        function confirmAction(event) {
            // デフォルトのフォーム送信を防ぐ
            event.preventDefault();

            // 必須項目のチェック
            const creditNumber = document.getElementById('credit_number').value;
            const creditDate = document.querySelector('input[name="credittime"]').value;
            const securityCode = document.getElementById('security').value;

            let errorMessage = '';
            const errorContainer = document.getElementById('errorContainer');
            errorContainer.innerHTML = ''; // エラーメッセージをリセット
            errorContainer.style.display = 'none'; // 非表示にする

            if (!creditNumber) {
                errorMessage += '※クレジットカード番号を入力してください。<br>';
            }
            if (!creditDate) {
                errorMessage += '※有効期限を入力してください。<br>';
            }
            if (!securityCode) {
                errorMessage += '※セキュリティコードを入力してください。<br>';
            }

            if (errorMessage) {
                errorContainer.innerHTML = errorMessage; // エラーメッセージを設定
                errorContainer.style.display = 'block'; // 表示する
            } else {
                // エラーがなければ遷移先のURLを指定
                window.location.href = 'creditNew';
            }
        }
    </script>
</head>
<body>

    <header>
        <div class="logo">
            <img src="img/rog.png" alt="TMC Logo">
            <h1>TMC カーシェア</h1>
        </div>
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

    <div id="errorContainer" class="error-message"></div> <!-- エラーメッセージ表示用 -->
    
    <div class="form-group">
        <form action="creditNew" method="post" onsubmit="confirmAction(event)">
            <div class="form-group">
                <label for="credit_number">
                    <span class="required">必須</span>クレジットカード番号<span class="highlight"> ※半角数字、ハイフンなし</span>
                </label>
                <input type="text" id="credit_number" placeholder="例:1234567891234567"><br><br>
                <span class="highlight2"> 注意:ご登録は、お申込ご本人名義のクレジットカードに限ります。</span><br>
                VISA・JCB・AMEX・MASTER・DINERS・EPOSの6ブランドがご利用いただけます。

                <label for="credit_date">
                    <span class="required">必須</span>有効期限
                </label>
                <input type="month" name="credittime">
                <br>

                <label for="security">
                    <span class="required">必須</span>セキュリティコード<span class="highlight"> ※半角数字</span>
                </label>
                <input type="number" id="security" placeholder="例:012">

                <div class="warning">
                    <input type="checkbox" id="myCheckbox" onclick="toggleButton()">入力内容をご確認いただき、誤りがない場合はチェックをつけてください。<br>
                    ※チェックが入っていない場合、変更を確定できません。
                </div>

                <div class="btn container">
                    <input type="button" id="modoru" value="ご登録情報の確認に戻る" onclick="location.href='P20.jsp'">
                    <button id="confirmButton" class="confirmbutton" disabled type="submit">確定</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
