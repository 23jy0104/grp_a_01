<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--登録申請入力画面-->

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/step.css"> 
    <script>
        function validateForm() {
            const requiredFields = document.querySelectorAll('.required input, .required select');
            let isValid = true;

            requiredFields.forEach(field => {
                if (!field.value) {
                    isValid = false;
                    field.style.borderColor = 'red'; // 未入力の場合、赤枠を表示
                } else {
                    field.style.borderColor = ''; // 入力があれば枠を元に戻す
                }
            });

            if (!isValid) {
                alert('必須項目をすべて入力してください。');
            }

            return isValid;
        }
    </script>
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
        <div class="step-label">基本情報のご入力</div> <!-- 現在のステップ -->
    </div>
    <div class="step">
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
    <div class="form-group">
        <label for="fullname">
            <span class="required">必須</span> 氏名<span class="highlight"> ※全角</span>
        </label>
        <div class="input-container">
            姓<input type="text" id="fullname" placeholder="例：たいむ">
            名<input type="text" id="given-name" placeholder="例：太郎">
        </div>
    </div>
    <div class="form-group">
        <label for="fullname-kana">
            <span class="required">必須</span> 氏名<span class="highlight"> ※全角カナ</span>
        </label>
        <div class="input-container">
            姓<input type="text" id="fullname-kana" placeholder="例：タイム">
            名<input type="text" id="given-name-kana" placeholder="例：タロウ">
        </div>
    </div>
    <div class="form-group">
        <label for="gender">
        <span class="required">必須</span>性別<span class="highlight"> ※必須</span></label>
        <div class="radio-group">
            <input type="radio" name="gender" value="male" required> 男性
            <input type="radio" name="gender" value="female"> 女性
            <input type="radio" name="gender" value="nomal"> 回答しない
        </div>
    </div>
    <div class="form-group">
        <label for="birthdate">
            <span class="required">必須</span> 生年月日<span class="highlight"> ※半角数字</span>
        </label>
        <div class="input-container">
            <input type="number" id="birthyear" placeholder="年" min="1920" max="2100" required>
            <div class="select-container">
                <select id="birthmonth" required>
                    <option value="">月</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                </select>
                <select id="birthday" required>
                    <option value="">日</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    <option value="13">13</option>
                    <option value="14">14</option>
                    <option value="15">15</option>
                    <option value="16">16</option>
                    <option value="17">17</option>
                    <option value="18">18</option>
                    <option value="19">19</option>
                    <option value="20">20</option>
                    <option value="21">21</option>
                    <option value="22">22</option>
                    <option value="23">23</option>
                    <option value="24">24</option>
                    <option value="25">25</option>
                    <option value="26">26</option>
                    <option value="27">27</option>
                    <option value="28">28</option>
                    <option value="29">29</option>
                    <option value="30">30</option>
                    <option value="31">31</option>
                </select>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="postcode">
            <span class="required">必須</span> 郵便番号
        </label>
        〒<input type="text" id="address" placeholder="例：169-0073" required>
    </div>
    <div class="form-group">
        <label for="prefecture">
            <span class="required">必須</span> 都道府県
        </label>
        <select id="prefecture" required>
            <option value="">選択してください</option>
            <option value="tokyo">東京都</option>
            <option value="kanagawa">神奈川県</option>
            <option value="osaka">大阪府</option>
            <option value="aichi">愛知県</option>
            <option value="hokkaido">北海道</option>
            <option value="fukuoka">福岡県</option>
        </select>
    </div>
    <div class="form-group">
        <label for="address">
            <span class="required">必須</span> 市区町村/町名/番地
        </label>
        <input type="text" id="address" placeholder="例：中央区銀座1-2-3" required>
    </div>
    <div class="form-group">
        <label for="building">
            <span class="required">必須</span> マンション名・ビル名など
        </label>
        <input type="text" id="building" placeholder="例：銀座マンション101号室" required>
    </div>
    <div class="form-group">
        <label for="TEL">
            <span class="required">必須</span>携帯電話番号
        </label>
        <input type="text" id="TEL" placeholder="例:09012345678" required>
    </div>
    <div class="form-group">
        <label for="koteiTEL">
            自宅電話番号
        </label>
        <input type="text"id="koteiTEL"placeholder="例:0312345678">
    </div>
    <div class="form-group">
        <label for="e_mail">
            <span class="required">必須</span>メールアドレス<span class="highlight"> ※半角英数字</span>
        </label>
        <input type="text"id="e_mail"placeholder="tmccarchare@share.ne.jp"required>
    </div>
    <div class="form-group">
        <label for="password">
            <span class="required">必須</span>パスワード<span class="highlight"> ※半角英数字8字以上20字以内</span>
        </label>
        <input type="text"id="password"placeholder="例:mypassword1234"required>
    </div>
    <div class="form-group">
        <label for="agein_password">
            <span class="required">必須</span>パスワード確認用
        </label>
        <input type="text"id="password"placeholder="例:mypassword1234"required>
    </div>
    <div class="form-group">
        <label for="information">
            <span class="required">必須</span>免許証情報
        </label>
    </div>
    <div class="form-group">
        <label for="date_expiry">
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
            <select id="day" required>
                <option value="">日</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
                <option value="13">13</option>
                <option value="14">14</option>
                <option value="15">15</option>
                <option value="16">16</option>
                <option value="17">17</option>
                <option value="18">18</option>
                <option value="19">19</option>
                <option value="20">20</option>
                <option value="21">21</option>
                <option value="22">22</option>
                <option value="23">23</option>
                <option value="24">24</option>
                <option value="25">25</option>
                <option value="26">26</option>
                <option value="27">27</option>
                <option value="28">28</option>
                <option value="29">29</option>
                <option value="30">30</option>
                <option value="31">31</option>
            </select>
        </div>
        

    </div>
    <div class="form-group">
        <label for="driver_licence_number">
            <span class="required">必須</span>免許証番号<span class="highlight"> ※半角数字</span>
        </label>
        <input type="text"id="driver_licence_number"placeholder="例:001234567890"required>
    </div>
    <div class="form-group">
        <label for="licence_type">
            <span class="required">必須</span>免許証種別<span class="highlight"> ※半角数字</span>
        </label>
        <div class="select-container">
            <select id="year" required>
                <option value="">--選択--</option>
                <option value="普通自動車免許">普通自動車免許</option>
                <option value="普通自動車免許(AT限定)">普通自動車免許(AT限定)</option>
                <option value="中型自動車(8t)限定免許">中型自動車(8t)限定免許</option>
                <option value="中型自動車(8t)限定免許(AT限定)">中型自動車(8t)限定免許(AT限定)</option>
                <option value="中型自動車免許">中型自動車免許</option>
                <option value="中型自動車免許(AT限定)">中型自動車免許(AT限定)</option>
                <option value="大型自動車免許">大型自動車免許</option>
                <option value="大型自動車免許(AT限定)">大型自動車免許(AT限定)</option>
                <option value="準中型自動車(5t)限定免許">準中型自動車(5t)限定免許</option>
                <option value="準中型自動車(5t)限定免許(AT限定)">準中型自動車(5t)限定免許(AT限定)</option>
                <option value="準中型自動車免許">準中型自動車免許</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <lavel for="licence_acquisition_date">
            <span class="required">必須</span>免許取得年月日<span class="highlight"> ※半角数字</span>
        </lavel>
        <div class="select-container">
            <select id="year" required>
                <option value="">--選択--</option>
                <option value="昭和">昭和</option>
                <option value="平成">平成</option>
                <option value="令和">令和</option>
            </select>
            <input type="text" id="acquisition" placeholder="例:27" required>
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
            <select id="day" required>
                <option value="">日</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
                <option value="13">13</option>
                <option value="14">14</option>
                <option value="15">15</option>
                <option value="16">16</option>
                <option value="17">17</option>
                <option value="18">18</option>
                <option value="19">19</option>
                <option value="20">20</option>
                <option value="21">21</option>
                <option value="22">22</option>
                <option value="23">23</option>
                <option value="24">24</option>
                <option value="25">25</option>
                <option value="26">26</option>
                <option value="27">27</option>
                <option value="28">28</option>
                <option value="29">29</option>
                <option value="30">30</option>
                <option value="31">31</option>
            </select>
        </div>
        <span class="highlight">※免許証に記載されている元号表記をそのまま選択してください。<br>※元号は１年で入力してください。 </span>
    </div>
    <div class="form-group">
        <label for="license_img">
            <span class="required">必須</span>免許画像アップロード
        </label>
    </div>
    <div class="box">
        運転免許証画像は表面と裏面の両方をご登録ください。<br>裏面に何も記載がない場合でもご登録をお願いいたします。
        <br><br>☆注意事項<br>
        <li><span class="highlight">記載事項を読み取ることができない場合、免許情報を確認することができません。送信前に記載事項が鮮明に写っているかご確認お願いいたします。</span></li>
        ↓以下の場合は確認できません。
        <li>光が反射して文字が読み取れない。</li>
        <li>余白が多いため画像が小さくて読み取れない。</li>
        <li>画像がぼやけていたり、荒いため文字が読み取れない。</li>
        <li>氏名、生年月日部分が切れていて見えない。</li>
        <li>スクリーンショットをアップロードしている。</li>
        <li>画像が正しい方向に向いていない。</li>
    </div>
    <div class="form-group">
        <label for="licence_img_omote">
            <span class="required">必須</span>免許証(表面)
        </label>
        <form actiou="upload.php" method="post" enctype="multipart/form-data" onsubmit="return validateForm();">
            <input type="file" id="file_omote" name="file_omote" accept="image/*"required>
            <br><br>
        </form>
    </div>
    <div class="form-group">
        <label for="licence_img_ura">
            <span class="required">必須</span>免許証(裏面)
        </label>
        <form actiou="upload.php" method="post" enctype="multipart/form-data" onsubmit="return validateForm();">
            <input type="file" id="file_ura" name="file_ura" accept="image/*"required>
            <br><br>
        </form>
    </div>
    <div class="button-container">
        <input type="button"value="詳細・規約のご確認に戻る" onclick="location.href='P4.jsp'">
        <input type="button" value="基本情報のご確認" onclick="if(validateForm()) location.href='UC03_03.jsp';">
    </div>
</div>
</body>
</html>
