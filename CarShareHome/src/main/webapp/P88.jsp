<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--基本情報の入力-->

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/credit.css">
    <link rel="stylesheet" href="css/P88.css">
    <script>
       function toggleButton() {
            const checkbox = document.getElementById('myCheckbox');
            const button = document.getElementById('confirmButton');
            button.disabled = !checkbox.checked;

            // チェックが入っている場合、オレンジ色のスタイルを適用
            if (checkbox.checked) {
                button.style.backgroundColor = 'orange';;
            } else {
                button.style.backgroundColor = ''; // デフォルトの色に戻す
                button.style.color = ''; // デフォルトの文字色に戻す
            }
        }

        function confirmAction() {
            // 遷移先のURLを指定します
            window.location.href = 'P76.jsp';
        }
    </script>
</head>
<body>

    <header>
        <div class="logo">
            <img src="img/rog.png" alt="TMC Logo">
            <h1>TMC カーシェア</h1>
        </div>
        <a href="P29.jsp" class="rogin">予約・ログイン ▶</a>
    </header>
    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
            <li class="nav-item gnav03"><a href="P65.html">予約確認・変更・取り消し</a></li>
            <li class="nav-item gnav04"><a href="P74.html">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.html">ご登録情報の確認</a></li>
        </ul>
    </nav>
    <table class="present">
        <tr>
            <th>現在のクレジット情報</th>
        </tr>
    </table>
    <table class="present">
        <tr>
            <th>クレジットカード番号</th>
            <td>****-〇〇〇〇</td>
        </tr>
        <tr>
            <th>有効期限</th>
            <td>〇〇月〇〇年</td>
        </tr>
    </table>
    <div class="form-group">
        <label for="credit_number">
            <span class="required">必須</span>クレジットカード番号<span class="highlight"> ※半角数字、ハイフンなし</span>
        </label>
        <input type="text"id="password"placeholder="例:mypassword1234"required><br><br>
        <span class="highlight2"> 注意:ご登録は、お申込ご本人名義のクレジットカードに限ります。</span><br>
        VISA・JCB・AMEX・MASTER・DINERS・EPOSの6ブランドがご利用いただけます。
        <label for="credit_date">
            <span class="required">必須</span>有効期限
        </label>
        <div class="select-container">
            <select id="year" required>
                <option value="">年</option>
                <option value="2025">2025年</option>
                <option value="2026">2026年</option>
                <option value="2027">2027年</option>
                <option value="2028">2028年</option>
                <option value="2029">2029年</option>
                <option value="2030">2030年</option>
                <option value="2031">2031年</option>
            </select>
            <select id="month" required>
                <option value="">月</option>
                <option value="1">1月</option>
                <option value="2">2月</option>
                <option value="3">3月</option>
                <option value="4">4月</option>
                <option value="5">5月</option>
                <option value="6">6月</option>
                <option value="7">7月</option>
                <option value="8">8月</option>
                <option value="9">9月</option>
                <option value="10">10月</option>
                <option value="11">11月</option>
                <option value="12">12月</option>
            </select>
        </div>
        <br>
        <label for="security">
            <span class="required">必須</span>クレジットカード番号<span class="highlight"> ※半角数字</span>
        </label>
        <input type="text"id="password"placeholder="例:012"required>
        <div class="warning">
            <input type="checkbox" id="myCheckbox" onclick="toggleButton()">入力内容をご確認いただき、誤りがない場合はチェックをつけてください。<br>
            ※チェックが入っていない場合、変更を確定できません。
        </div>
        <div class="btn container">
            <button id="confirmButton" class="confirmbutton" disabled onclick="confirmAction()">確定</button>
        </div>

    </div>
</body>
</html>