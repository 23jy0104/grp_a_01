<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--トップページ-->

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/top.css">
</head>
<body>

<header>
    <img src="img/rog.png" alt="TMCロゴ">
    <h1>TMC カーシェア</h1>
    <div class="login-buttons">
        <button class="member" onclick="location.href='P4.jsp'">会員登録申請はこちら</button>
        <button class="login" onclick="location.href='P29.jsp'">予約・ログイン ▶</button>
    </div>
</header>

<nav class="nav">
    <ul>
        <li class="nav-item gnav02"><a href="UC02_01_S1_S2.jsp">予約・ステーション検索</a></li>
        <li class="nav-item gnav03"><a href="P39.jsp">料金</a></li>
        <li class="nav-item gnav04"><a href="P43.">利用方法</a></li>
        <li class="nav-item gnav05"><a href="P46.jsp">車種</a></li>
    </ul>
</nav>

<section>
    <h2>TMCレンタカー、カーシェア事業始めました！</h2>
    <p>TMCレンタカーは、お客様からの</p>
    <ul>
        <li>そんなに減っていないのにガソリンを満タンにして返さないといけないからその時間がもったいない！</li>
        <li>24時間利用できるようになってほしい！！</li>
        <li>ちょっとした買い物をするのに気軽に借りられるようになってほしい！</li>
    </ul>
    <p>などというご意見を参考にこの度カーシェア事業を展開することに致しました！</p>
    <p>15分単位から借りられ、24時間365日好きな時間に借りられます！</p>
</section>
<h1 class="basic">運転の基本</h1>
※STEPごとにクリックいただくと表示が切り替わります
<div class="stepnav">
    <button onclick="showStep(1)">ステップ 1</button>
    <button onclick="showStep(2)">ステップ 2</button>
    <button onclick="showStep(3)">ステップ 3</button>
    <button onclick="showStep(4)">ステップ 4</button>
    <button onclick="showStep(5)">ステップ 5</button>
</div>
<div id="step1" class="step active">
    <h2>ステップ 1 座席の前後の調整をしよう</h2>
    <img src="img/drive-basic_il001.png">
    <p>座席に深く腰掛け背筋を伸ばして座り、まずは座席の前後を調整しよう。

        座席の前後はブレーキペダルをいっぱいに踏んでもひざに軽く余裕があるくように調整しよう。
    ※ここを正しく調整しないと急ブレーキのときに踏込が不十分になってしまいます。</p>
</div>

<div id="step2" class="step">
    <h2>ステップ 2 座席の角度を調整しよう</h2>
    <img src="img/drive-basic_il002.png">
    <p>座席の前後の調整が終わったら、角度の調整をしよう。

        角度は背筋をのばし、ハンドルの一番上を両手で握った際に、背中がシートから離れない位置に調整しよう
    また、同時にヘッドレストの中心と耳の高さが同じくらいになるようにヘッドレストを調整しよう。
    
    ここを正しく調整することで万が一の際により衝撃を吸収してくれます。</p>
</div>

<div id="step3" class="step">
    <h2>ステップ 3 ハンドル位置を調整しよう</h2>
    <img src="img/drive-basic_il003.png">
    <p>ハンドルの高さや前後の位置を調整しよう。

        ハンドルは9時15分～10時10分の間の位置をにぎろう。
    ハンドルの前後の位置は、にぎったときにひじに余裕ができるように調整しよう。
    ハンドルの高さは、メーター類が全て見える高さに調整しよう。</p>
</div>

<div id="step4" class="step">
    <h2>ステップ 4 ルームミラーをあわせよう</h2>
    <img src="img/drive-basic_il004.png">
    <p>真後ろがミラーの中央に写るように整えよう。

        整えるときに姿勢を変えてしまうと、運転するときに見える景色と変わってしまうため、必ず姿勢は変えずに目だけで確認しながら調節しよう。
</div>

<div id="step5" class="step">
    <h2>ステップ 5 サイドミラーをあわせよう</h2>
    <img src="img/drive-basic_il005.png">
    <p>車体がミラーの約1/4～1/3写るようにし、路面が1/2～2/3写るように整えよう。

        ルームミラーとサイドミラーを正しく調整することで周りの状況が把握しやすくなり、事故を防ぐことにつながります。</p>
</div>
</div>

<script>
    function showStep(step) {
        // 全てのステップを非表示にする
        const steps = document.querySelectorAll('.step');
        steps.forEach(s => s.classList.remove('active'));

        // 指定されたステップを表示する
        document.getElementById('step' + step).classList.add('active');
    }
</script>

</body>
</html>
