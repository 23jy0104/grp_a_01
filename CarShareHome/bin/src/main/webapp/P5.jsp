<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--手続きに必要なものの確認画面-->

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/tetuduki.css">
</head>
<body>

    <div class="header">
        <img src="img/rog.png"alt="TMC Logo" class="logo">
        <h1>TMC カーシェア</h1>
        <button class="button" onclick="location.href='P29.jsp'">予約・ログイン </button>
    </div>
    <h2>
        登録手続きに必要なもの
    </h2>

    <table>
        <tr>
            <th>ご用意いただくもの</th>
            <th>注意事項</th>
        </tr>
        <tr>
            <td>運転免許証</td>
            <td>ご本人様の運転免許証をご用意ください。</td>
        </tr>
        <tr>
            <td>クレジットカード</td>
            <td>ご本人様名義のクレジットカードをご用意ください。</td>
        </tr>
        <tr>
            <td colspan="2">
                （運転免許証記載の住所と現住所が異なる方のみ）<br>
                現住所確認書類<br>
                ・公的機関の発行書類または請求書（発行から3か月以内）<br>
                ・住民票の写し（発行から3か月以内）、本籍・マイナンバーの記載がないもの<br>
                ・パスポート<br>
                ・住民基本台帳カード<br>
                ・金融機関カード及び特別永住者証明書（両面）
                <div class="btn-container">
                    <a href="P6.jsp" class="btn">確認しました</a>
                </div>
            </td>
        </tr>
    </table>

</body>
</html>
