<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--登録情報変更入力画面-->

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
        <h4 id="username">23jy0000様</h4>
        <button class="logout-button" onclick="location.href='P29.jap'">ログアウト</button>
    </header>
    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="P53.jsp">予約・ステーション検索</a></li>
            <li class="nav-item gnav03"><a href="P65.jsp">予約確認・変更・取り消し</a></li>
            <li class="nav-item gnav04"><a href="P74.jsp">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>
    <h1>登録情報の変更</h1>
    <div class="usage">
        <table>
            <tr>
                <th class="howtouse">決済方法</th>
            </tr>
        </table>
        <p>クレジット
            <button class="change" onclick="location.href=''" style="float: right;">変更する</button>
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
                <th class="howtouse">郵便番号<span style="color: red;">※必須</span></th>
            </tr>
        </table>
        <div class="form-group">
            〒<input type="text" id="postcode" placeholder="例：169-0073" required>
        </div>
        <table>
            <tr>
                <th class="howtouse">都道府県/市区町村<span style="color: red;">※必須</span></th>
            </tr>
        </table>
        <div class="form-group">
            <input type="text" id="address" placeholder="例：東京都新宿区" required>
        </div>
        <table>
            <tr>
                <th class="howtouse">町名・番地など<span style="color: red;">※必須</span></th>
            </tr>
        </table>
        <div class="form-group">
            <input type="text" id="address" placeholder="例：百人町1-25-4" required>
        </div>
        <table>
            <tr>
                <th class="howtouse">ビル名・マンション・号室<span style="color: red;">※必須</span></th>
            </tr>
        </table>
        <div class="form-group">
            <input type="text" id="address" placeholder="例：日電マンション305" required>
        </div>
        <table>
            <tr>
                <th class="howtouse">携帯電話番号<span style="color: red;">※必須</span></th>
            </tr>
        </table>
        <div class="form-group">
            <input type="text" id="address" placeholder="例：09012345678" required>
        </div>
            <table>
                <tr>
                    <th class="howtouse">現在のメールアドレス</th>
                </tr>
            </table>
            <p>〇〇〇〇〇〇＠〇〇〇〇〇〇</p>
            <table>
                <tr>
                    <th class="howtouse">変更後のメールアドレス<span style="color: red;">※必須</span></th>
                </tr>
            </table>
            <div class="form-group">
                <input type="text" id="address" placeholder="例：〇〇〇〇〇〇〇〇＠〇〇〇〇〇〇.ne.jp" required>
            </div>
            <button class="informationchange" onclick="location.href='P82.jsp'">変更する</button>
    </div>
</body>
</html>