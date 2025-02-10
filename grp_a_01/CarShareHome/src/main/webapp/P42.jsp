<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--補償料金-->

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/top.css">
    <link rel="stylesheet" href="css/price.css">
    <link rel="stylesheet" href="css/comprice.css">
</head>
<body>

<header>
    <img src="img/rog.png" alt="TMCロゴ">
    <h1>TMC カーシェア</h1>
    <div class="login-buttons">
        <button class="member" onclick="location.href='P4.jsp'" >会員登録申請はこちら</button>
        <button class="login" onclick="location.href='P29.jsp'">予約・ログイン ▶</button>
    </div>
</header>

<nav class="nav">
    <ul>
        <li class="nav-item"><a href="P49.jsp">ステーション検索</a></li>
        <li class="nav-item"><a href="P40.jsp">料金</a></li>
        <li class="nav-item active"><a href="P43.jsp">利用方法</a></li>
        <li class="nav-item"><a href="P46.jsp">車種</a></li>
    </ul>
</nav>

<div class="container">
    <div class="sidebar">
        <table>
            <tr>
                <th class="highlight">料金</th>
            </tr>
            <tr>
                <td><a href="P40.jsp" class="link">利用料金</a></td>
            </tr>
            <tr>
                <td><a href="P41.jsp" class="link ">補償制度</a></td>
            </tr>
            <tr>
                <td><a href="P42.jsp" class="link active">延長、超過料金について</a></td>
            </tr>
        </table>
    </div>
    <div class="comprice">
        <table>
            <tr>
                <th class="price" colspan="2">延長について</th>
            </tr>
        </table>
        <p>ご利用を延長される場合は、返却予定時刻までに延長手続きをおこなってください。
            <table>
                <tr>
                    <th class="price" colspan="2">超過料金について</th>
                </tr>
            </table>
            <p>あらかじめ設定した返却予定日時までに手続きをおこなわず、返却が遅れた場合、返却完了までの超過料金は通常料金の2倍です。</p>
            <table>
                <tr>
                    <td>880円/15分</td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>