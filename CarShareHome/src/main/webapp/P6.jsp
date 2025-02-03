<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--登録申請入力画面-->

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/step.css"> 
    <script>
	    function validateForm(event) {
	        event.preventDefault(); // フォーム送信を防ぐ
	        let errorMessages = [];
	        
	        const sei = document.getElementById('sei');
	        const mei = document.getElementById('mei');
	        const seikana = document.getElementById('meikana');
	        const meikana = document.getElementById('seikana');
	        const gender = document.querySelector('input[name="gender"]:checked');
	        const birthyear = document.getElementById('birthyear');
	        const birthmonth = document.getElementById('birthmonth');
	        const birthday = document.getElementById('birthday');
	        const postcode = document.getElementById('postcode');
	        const prefecture = document.getElementById('prefecture');
	        const address = document.getElementById('address');
	        const phonenumber = document.getElementById('TEL');
	        const mailaddress = document.getElementById('e_mail');
	        const password = document.getElementById("password");
	        const ageinpassword = document.getElementById("agein_password");
	        const dateyear = document.getElementById('year');
	        const datemonth = document.getElementById("month");
	        const dateday = document.getElementById('day');
	        const licensenumber = document.getElementById('driver_licence_number');
	        const licensetype = document.getElementById('type');
	        const licenseyear = document.getElementById('license_year');
	        const licensemonth = document.getElementById('license_month');
	        const licenseday = document.getElementById('license_day');
	        const fileomote = document.getElementById('file_omote').files[0];
	        const fileura = document.getElementById('file_ura').files[0];
	
	        // 入力フィールドをリセット
	        const inputs = [sei, mei, seikana, meikana, birthyear, birthmonth, birthday, postcode, prefecture, address, phonenumber, mailaddress, password, ageinpassword, dateyear, datemonth, dateday, licensenumber, licensetype, licenseyear, licensemonth, licenseday];
	        inputs.forEach(input => {
	            input.style.backgroundColor = ''; // 背景色をリセット
	        });
	        if (!sei.value) {
	            errorMessages.push("※姓が未入力です。");
	            sei.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (!mei.value) {
	            errorMessages.push("※名が未入力です。");
	            mei.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if(!seikana.value){
	        	 errorMessages.push("※姓カナが未入力です。");
		         seikana.style.backgroundColor = '#ffcccc'; // 薄い赤色
		    }
		    if(!meikana.value){
		    	errorMessages.push("※名カナが未入力です。");
		        seikana.style.backgroundColor = '#ffcccc'; // 薄い赤色
			}
	        if (!gender) {
	            errorMessages.push("※性別が選択されていません。");
	        }
	        if (!birthyear.value || !birthmonth.value || !birthday.value) {
	            errorMessages.push("※誕生日が未入力です。");
	            birthyear.style.backgroundColor = '#ffcccc'; // 薄い赤色
	            birthmonth.style.backgroundColor = '#ffcccc'; // 薄い赤色
	            birthday.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (!postcode.value) {
	            errorMessages.push('※郵便番号が未入力です。');
	            postcode.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (prefecture.value === "") {
	            errorMessages.push("※都道府県を選択してください。");
	            prefecture.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (!address.value) {
	            errorMessages.push("※市区町村が未入力です。");
	            address.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (!phonenumber.value) {
	            errorMessages.push("※携帯電話番号が未入力です。");
	            phonenumber.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (!mailaddress.value) {
	            errorMessages.push("※メールアドレスが未入力です。");
	            mailaddress.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (!password.value) {
	            errorMessages.push("※パスワードが未入力です。");
	            password.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (!ageinpassword.value) {
	            errorMessages.push("※パスワード確認用が未入力です。");
	            ageinpassword.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (password.value !== ageinpassword.value) {
	            errorMessages.push("※パスワードと確認用パスワードが一致しません。");
	        }
	        if (!dateyear.value || !datemonth.value || !dateday.value) {
	            errorMessages.push("※免許証の有効期限が選択されていません。");
	            dateyear.style.backgroundColor = '#ffcccc'; // 薄い赤色
	            datemonth.style.backgroundColor = '#ffcccc'; // 薄い赤色
	            dateday.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (!licensenumber.value) {
	            errorMessages.push("※免許証番号が未入力です。");
	            licensenumber.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (licensetype.value === "") {
	            errorMessages.push("※免許証種別が未選択です。");
	            licensetype.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (!licenseyear.value || !licensemonth.value || !licenseday.value) {
	            errorMessages.push("※免許証取得日が未選択です。");
	            licenseyear.style.backgroundColor = '#ffcccc'; // 薄い赤色
	            licensemonth.style.backgroundColor = '#ffcccc'; // 薄い赤色
	            licenseday.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (!fileomote) {
	            errorMessages.push("※免許証(表面)写真が選択されていません。");
	        }
	        if (!fileura) {
	            errorMessages.push("※免許証(裏面)写真が選択されていません。");
	        }
	
	        // エラーメッセージを表示
	        const errorContainer = document.getElementById('errorMessages');
	        errorContainer.innerHTML = ''; // 以前のエラーメッセージをクリア
	        if (errorMessages.length > 0) {
	            errorContainer.innerHTML = errorMessages.join('<br>');
	            errorContainer.scrollIntoView({ behavior: 'smooth', block: 'start' });
	        } else {
	        	window.location.href = "CarShareNew";
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
    <a href="P29.ｊ" class="btn">予約・ログイン ▶</a>
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
<div id="errorMessages" class="error-message" style="color: red; margin: 10px 0;"></div>
<form id="infoForm" action="CarShareNew" method="post" onsubmit="validateForm(event);">
	<div class="form-container">
	    <div class="form-group">
	        <label for="fullname">
	            <span class="required">必須</span> 氏名<span class="highlight"> ※全角</span>
	        </label>
	        <div class="input-container">
	            姓<input type="text" id="sei" placeholder="例：たいむ">
	            名<input type="text" id="mei" placeholder="例：太郎">
	        </div>
	    </div>
	    <div class="form-group">
	        <label for="fullname-kana">
	            <span class="required">必須</span> 氏名<span class="highlight"> ※全角カナ</span>
	        </label>
	        <div class="input-container">
	            姓<input type="text" id="seikana" placeholder="例：タイム">
	            名<input type="text" id="meikana" placeholder="例：タロウ">
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
	        〒<input type="text" id="postcode" placeholder="例：169-0073" required>
	    </div>
	    <div class="form-group">
	        <label for="prefecture">
	            <span class="required">必須</span> 都道府県
	        </label>
	        <select id="prefecture" required>
	            <option value="">選択してください</option>
	            <option value="hokkaido">北海道</option>
	            <option value="aomori">青森県</option>
	            <option value="akita">秋田県</option>
	            <option value="iwate">岩手県</option>
	            <option value="yamagata">山形県</option>
	            <option value="miyagi">宮城県</option>
	            <option value="niigata">新潟県</option>
	            <option value="hukusima">福島県</option>
	            <option value="nagano">長野県</option>
	            <option value="totigi">栃木県</option>
	            <option value="gunma">群馬県</option>
	            <option value="ibaragi">茨城県</option>
	            <option value="tiba">千葉県</option>
	            <option value="saitama">埼玉県</option>
	            <option value="tokyo">東京都</option>
	            <option value="kanagawa">神奈川県</option>
	            <option value="isikawa">石川県</option>
	            <option value="toyama">富山県</option>
	            <option value="hukui">福井県</option>
	            <option value="gihu">岐阜県</option>
	            <option value="yamanasi">山梨県</option>
	            <option value="sizuoka">静岡県</option>
	            <option value="aiti">愛知県</option>
	            <option value="siga">滋賀県</option>
	            <option value="mie">三重県</option>
	            <option value="osaka">大阪府</option>
	            <option value="nara">奈良県</option>
	            <option value="wakayama">和歌山県</option>
	            <option value="kyouto">京都府</option>
	            <option value="hyougo">兵庫県</option>
	            <option value="simane">島根県</option>
	            <option value="okayama">岡山県</option>
	            <option value="tottori">鳥取県</option>
	            <option value="hirosima">広島県</option>
	            <option value="yamaguti">山口県</option>
	            <option value="kagawa">香川県</option>
	            <option value="ehime">愛媛県</option>
	            <option value="kouti">高知県</option>
	            <option value="tokusima">徳島県</option>
	            <option value="nagasaki">長崎県</option>
	            <option value="saga">佐賀県</option>
	            <option value="fukuoka">福岡県</option>
	            <option value="ooita">大分県</option>
	            <option value="miyazaki">宮崎県</option>
	            <option value="kumamoto">熊本県</option>
	            <option value="kagosima">鹿児島県</option>
	            <option value="okinawa">沖縄県</option>
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
	        <input type="password"id="password"placeholder="例:mypassword1234"required>
	    </div>
	    <div class="form-group">
	        <label for="agein_password">
	            <span class="required">必須</span>パスワード確認用
	        </label>
	        <input type="password"id="agein_password"placeholder="例:mypassword1234"required>
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
	            <select id="type" required>
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
	            <select id="license_year" required>
	                <option value="">--選択--</option>
	                <option value="昭和">昭和</option>
	                <option value="平成">平成</option>
	                <option value="令和">令和</option>
	            </select>
	            <input type="text" id="acquisition" placeholder="例:27" required>
	            <select id="license_month" required>
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
	            <select id="license_day" required>
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
	</div>
	<div class="button-container">
    	<button type="button" onclick="location.href='P4.jsp'">詳細・規約のご確認に戻る</button>
	    <button type="button" onclick="validateForm(event)" class="btn">基本情報のご確認</button>
    </div>
	</form>
</div>
</body>
</html>
