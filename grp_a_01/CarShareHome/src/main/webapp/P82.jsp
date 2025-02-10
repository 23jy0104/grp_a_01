<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--変更後の登録情報の確認-->

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/check.css">
</head>
<body>
    <header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
    </header>
    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
            <li class="nav-item gnav03"><a href="P65.jsp">予約確認・変更・取り消し</a></li>
            <li class="nav-item gnav04"><a href="P74.jsp">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>
    <h1>登録情報の確認</h1>
    <div class="usage">
        <table>
            <tr>
                <th class="howtouse">決済方法</th>
            </tr>
        </table>
        <p>クレジット
            <button class="change" onclick="location.href='P88.jsp'" style="float: right;">変更する</button>
        </p>

        <table>
            <th class="howtouse">氏名</th>
            </tr>
        </table>
        <p>〇〇　〇〇</p>
        <table>
            <tr>
                <th class="howtouse">氏名フリガナ</th>
            </tr>
        </table>
        <p>〇〇〇〇　〇〇〇〇</p>
        <table>
            <tr>
                <th class="howtouse">住所</th>
            </tr>
        </table>
        <p>〒000-0000
            <br>東京都新宿区百人町1-25-4
        </p>
        <table>
            <tr>
                <th class="howtouse">電話番号(携帯)</th>
            </tr>
        </table>
        <p>〇〇〇-〇〇〇〇-〇〇〇〇</p>
        <table>
            <tr>
                <th class="howtouse">運転免許証種別</th>
            </tr>
        </table>
         <p>普通自動車免許</p>
        <table>
            <tr>
                <th class="howtouse">有効期限</th>
            </tr>
        </table>
        <p>〇〇〇〇(令和〇年)〇〇月〇〇日</p>
        <table>
            <tr>
                <th class="howtouse">安心補償サービス</th>
            </tr>
        </table>
            <p>自動加入</p>
            <table>
                <tr>
                    <th class="howtouse">ご登録メールアドレス</th>
                </tr>
            </table>
            <p>〇〇〇〇〇〇＠〇〇〇〇〇〇</p>
            <button class="informationchange" onclick="location.href='P76.jsp'">変更確定</button>
            <button class="informationchange" onclick="location.href='P76.jsp'">入力画面に戻る</button>
    </div>
</html>