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
	        const birthday = document.getElementById('birthday');
	        const postcode = document.getElementById('postcode');
	        const prefecture = document.getElementById('prefecture');
	        const address = document.getElementById('address');
	        const phonenumber = document.getElementById('TEL');
	        const mailaddress = document.getElementById('e_mail');
	        const password = document.getElementById("password");
	        const ageinpassword = document.getElementById("agein_password");
	        const licensenumber = document.getElementById('driver_licence_number');
	        const licensetype = document.getElementById('type');
	        const fileomote = document.getElementById('file_omote').files[0];
	        const fileura = document.getElementById('file_ura').files[0];

	        // 入力フィールドをリセット
	        const inputs = [sei, mei, seikana, meikana, birthday, postcode, prefecture, address, phonenumber, mailaddress, password, ageinpassword, licensenumber, licensetype];
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
	        if (!seikana.value) {
	            errorMessages.push("※姓カナが未入力です。");
	            seikana.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (!meikana.value) {
	            errorMessages.push("※名カナが未入力です。");
	            meikana.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (!gender) {
	            errorMessages.push("※性別が選択されていません。");
	        }
	        if (!birthday.value) {
	            errorMessages.push("※誕生日が未入力です。");
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
	        if (!licensenumber.value) {
	            errorMessages.push("※免許証番号が未入力です。");
	            licensenumber.style.backgroundColor = '#ffcccc'; // 薄い赤色
	        }
	        if (licensetype.value === "") {
	            errorMessages.push("※免許証種別が未選択です。");
	            licensetype.style.backgroundColor = '#ffcccc'; // 薄い赤色
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
	        	document.getElementById('infoForm').submit(); // フォームを送信
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
    <a href="P29.jsp" class="btn">予約・ログイン ▶</a>
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
<form id="infoForm" action="CarShareNew" method="post" enctype="multipart/form-data" onsubmit="validateForm(event);">
	<div class="form-container">
	    <div class="form-group">
	        <label for="fullname">
	            <span class="required">必須</span> 氏名<span class="highlight"> ※全角</span>
	        </label>
	        <div class="input-container">
	            姓<input type="text" id="sei" name="customerSei" placeholder="例：たいむ" required>
	            名<input type="text" id="mei" name="customerMei" placeholder="例：太郎" required>
	        </div>
	    </div>
	    <div class="form-group">
	        <label for="fullname-kana">
	            <span class="required">必須</span> 氏名<span class="highlight"> ※全角カナ</span>
	        </label>
	        <div class="input-container">
	            姓<input type="text" id="seikana" name="customerSeiKana" placeholder="例：タイム" required>
	            名<input type="text" id="meikana" name="customerMeiKana" placeholder="例：タロウ" required>
	        </div>
	    </div>
	    <div class="form-group">
	        <label for="gender">
	        	        <span class="required">必須</span>性別<span class="highlight"> ※必須</span></label>
	        <div class="radio-group">
	            <input type="radio" name="gender" value="男" required> 男性
	            <input type="radio" name="gender" value="女"> 女性
	            <input type="radio" name="gender" value="無"> 回答しない
	        </div>
	    </div>
	    <div class="form-group">
	        <label for="birthdate">
	            <span class="required">必須</span> 生年月日<span class="highlight"> ※半角数字</span>
	        </label>
	        <div class="input-container">
	            <input type="date" id="birthday" name="birthday" required>
	        </div>
	    </div>
	    <div class="form-group">
	        <label for="postcode">
	            <span class="required">必須</span> 郵便番号　ハイフンなし半角数字で入力してください。
	        </label>
	        〒<input type="text" id="postcode" name="postcode" placeholder="例：1690073" required>
	    </div>
	    <div class="form-group">
	        <label for="prefecture">
	            <span class="required">必須</span> 都道府県
	        </label>
	        <select id="prefecture" name="city" required>
	            <option value="">選択してください</option>
	            <option value="北海道">北海道</option>
	            <option value="青森県">青森県</option>
	            <option value="秋田県">秋田県</option>
	            <option value="岩手県">岩手県</option>
	            <option value="山形県">山形県</option>
	            <option value="宮城県">宮城県</option>
	            <option value="新潟県">新潟県</option>
	            <option value="福島県">福島県</option>
	            <option value="長野県">長野県</option>
	            <option value="栃木県">栃木県</option>
	            <option value="群馬県">群馬県</option>
	            <option value="茨城県">茨城県</option>
	            <option value="千葉県">千葉県</option>
	            <option value="埼玉県">埼玉県</option>
	            <option value="東京都">東京都</option>
	            <option value="神奈川県">神奈川県</option>
	            <option value="石川県">石川県</option>
	            <option value="富山県">富山県</option>
	            <option value="福井県">福井県</option>
	            <option value="岐阜県">岐阜県</option>
	            <option value="山梨県">山梨県</option>
	            <option value="静岡県">静岡県</option>
	            <option value="愛知県">愛知県</option>
	            <option value="滋賀県">滋賀県</option>
	            <option value="三重県">三重県</option>
	            <option value="大阪府">大阪府</option>
	            <option value="奈良県">奈良県</option>
	            <option value="和歌山県">和歌山県</option>
	            <option value="京都府">京都府</option>
	            <option value="兵庫県">兵庫県</option>
	            <option value="島根県">島根県</option>
	            <option value="岡山県">岡山県</option>
	            <option value="鳥取県">鳥取県</option>
	            <option value="広島県">広島県</option>
	            <option value="山口県">山口県</option>
	            <option value="香川県">香川県</option>
	            <option value="愛媛県">愛媛県</option>
	            <option value="高知県">高知県</option>
	            <option value="徳島県">徳島県</option>
	            <option value="長崎県">長崎県</option>
	            <option value="佐賀県">佐賀県</option>
	            <option value="福岡県">福岡県</option>
	            <option value="大分県">大分県</option>
	            <option value="宮崎県">宮崎県</option>
	            <option value="熊本県">熊本県</option>
	            <option value="長崎県">鹿児島県</option>
	            <option value="沖縄県">沖縄県</option>
	        </select>
	    </div>
	    <div class="form-group">
	        <label for="address">
	            <span class="required">必須</span> 市区町村/町名/番地
	        </label>
	        <input type="text" id="address" name="address" placeholder="例：中央区銀座1-2-3" required>
	    </div>
	    <div class="form-group">
	        <label for="building">
	            マンション名・ビル名など
	        </label>
	        <input type="text" id="building" name="building" placeholder="例：銀座マンション101号室" required>
	    </div>
	    <div class="form-group">
	        <label for="TEL">
	            <span class="required">必須</span>携帯電話番号
	        </label>
	        <input type="text" id="TEL" name="TEL" placeholder="例:09012345678" required>
	    </div>
	    <div class="form-group">
	        <label for="koteiTEL">
	            自宅電話番号
	        </label>
	        <input type="text" id="koteiTEL" name="koteiTEL" placeholder="例:0312345678">
	    </div>
	    <div class="form-group">
	        <label for="e_mail">
	            <span class="required">必須</span>メールアドレス<span class="highlight"> ※半角英数字</span>
	        </label>
	        <input type="email" id="e_mail" name="email" placeholder="tmccarchare@share.ne.jp" required>
	    </div>
	    <div class="form-group">
	        <label for="password">
	            <span class="required">必須</span>パスワード<span class="highlight"> ※半角英数字8字以上20字以内</span>
	        </label>
	        <input type="password" id="password" name="password" placeholder="例:mypassword1234" required>
	    </div>
	    <div class="form-group">
	        <label for="agein_password">
	            <span class="required">必須</span>パスワード確認用
	        </label>
	        <input type="password" id="agein_password" name="agein_password" placeholder="例:mypassword1234" required>
	    </div>
	    <div class="form-group">
        <label for="information">
            <span class="required">必須</span>免許証情報
        </label>
    </div>
	    <div class="form-group">
	        <label for="driver_licence_number">
	            <span class="required">必須</span>免許証番号<span class="highlight"> ※半角数字</span>
	        </label>
	        <input type="text" id="driver_licence_number" name="licenseNumber" placeholder="例:001234567890" required>
	    </div>
	    <div class="form-group">
        <label for="date_expiry">
            <span class="required">必須</span>有効期限
        </label>
        <input type ="date" name="licenseDate">
        </div>
	    <div class="form-group">
	        <label for="licence_type">
	            <span class="required">必須</span>免許証種別<span class="highlight"> ※半角数字</span>
	        </label>
	        <div class="select-container">
	            <select id="type" name="type" required>
	                <option value="">--選択--</option>
	                <option value="普通自動車免許">普通自動車免許</option>
	                <option value="普通自動車免許(AT限定)">普通自動車免許(AT限定)</option>
	                <option value="中型自動車(8t)限定免許">中型自動車(8t)限定免許</option>
	                <option value="大型自動車免許">大型自動車免許</option>
	                <option value="準中型自動車免許">準中型自動車免許</option>
	            </select>
	        </div>
	        <div class="form-group">
        <label for="licence_acquisition_date">
            <span class="required">必須</span>免許取得年月日<span class="highlight"> ※半角数字</span>
        </label>
            <input type="date" id="acquisition" name="licenseDate"  placeholder="例:27" required>
            
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
	        <input type="file" id="file_omote" name="file_omote" accept="image/*" required>
	    </div>
	    <div class="form-group">
	        <label for="licence_img_ura">
	            <span class="required">必須</span>免許証(裏面)
	        </label>
	        <input type="file" id="file_ura" name="file_ura" accept="image/*" required>
	    </div>
	</div>
	<div class="button-container">
	    	    <button type="button" onclick="location.href='P4.jsp'" class="btn">詳細・規約のご確認に戻る</button>
	    <button type="button" onclick="validateForm(event)" class="btn">基本情報のご確認</button>
	</div>
</form>
</div>
</body>
</html>
