<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="model.Customer" %>

<% 
String customerName = (String) session.getAttribute("customerName");
String customerKana =(String) session.getAttribute("customerKana");
String email =(String)session.getAtteribute("email");
%>

<!--変更後の登録情報の確認-->

<!--登録情報変更入力画面-->

<!-- 変更後の登録情報の確認 -->

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
        <h4 id="username"><%=customerName %>さん</h4>
        <button class="logout-button" onclick="location.href='P29.jsp'">ログアウト</button>
    </header>
    <nav class="nav">
        <ul>
            <li class="nav-item gnav02"><a href="./P53.jsp">予約・ステーション検索</a></li>
            <li class="nav-item gnav03"><a href="/use/">予約確認・変更・取り消し</a></li>
            <li class="nav-item gnav04"><a href="/car/">ご利用履歴</a></li>
            <li class="nav-item gnav05"><a href="./P76.jsp">ご登録情報の確認</a></li>
        </ul>
    </nav>
    <h1>登録情報の変更</h1>
    <div class="usage">
        <form action="CustomerEmailUpdate" method="post">
            <table>
                <th class="howtouse">氏名</th>
            </table>
            <p><%=customerName %></p>

            <table>
                <tr>
                    <th class="howtouse">氏名フリガナ</th>
                </tr>
            </table>
            <p><%=customerKana %></p>
            <table>
                <tr>
                    <th class="howtouse">現在のメールアドレス</th>
                </tr>
            </table>
            <p><%=email %></p>
            <table>
                <tr>
                    <th class="howtouse">メールアドレス<span style="color: red;">※必須</span></th>
                </tr>
            </table>
            <div class="form-group">
                <input type="text" name="postCode" placeholder="taro.time@example.com" required>
            </div>

            <button class="informationchange" type="submit">変更する</button>    
        </form>
        <button type="button" onclick ="location.href='P76.jsp'">前の画面に戻る</button>
    </div>
</body>
</html>
