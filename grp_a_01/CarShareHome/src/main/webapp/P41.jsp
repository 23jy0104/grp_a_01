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
    <link rel="stylesheet" href="css/useprice.css">
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
        <li class="nav-item"><a href="../../html/home/UC02_01_S1_S2.html">ステーション検索</a></li>
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
                <td><a href="P41.jsp" class="link active">補償制度</a></td>
            </tr>
            <tr>
                <td><a href="P42.jsp" class="link">延長、超過料金について</a></td>
            </tr>
        </table>
    </div>
    <div class="comprice">
        <table>
            <tr>
                <th class="price" colspan="2">補償制度</th>
            </tr>
            <tr>
                <td>安心補償サービス加入料金</td>
                <td>550円/利用</td>
            </tr>
        </table>
        <p>事故時のNOC、タイヤの実費に加え、バッテリー上がり、キーのインロック、ガス欠、それに関わる搬送（レッカー等）の費用が免除となります。
        <br>なお、この制度は必須で料金に含まれています。</p>
        <table class="img">
            <tr>
                <h1>これらが免除！</h1>
            </tr>
            <tr>
                <td><img src="img/jioko.png"></td>
                <td><img src="img/gas.png"></td>
            </tr>
            <tr>
                <td><img src="img/keyrock.png"></td>
                <td><img src="img/battery.png"></td>
                <td><img src="img/pank.png"></td>
            </tr>
        </table>
    </div>
</div>

</body>
</html>