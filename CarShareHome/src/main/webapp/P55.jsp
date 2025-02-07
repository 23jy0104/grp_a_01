<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<% 
	String customerName = (String) session.getAttribute("customerName");
    List<String[]> stations = (List<String[]>) request.getAttribute("stations"); // ステーション情報を取得
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TMC カーシェア</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="vss/P55.css">
</head>
<body>
    <header>
        <img src="img/rog.png" alt="TMCロゴ">
        <h1>TMC カーシェア</h1>
        <h4 id="username"><%=customerName%>さん</h4>
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
    
    <main class="main-content">
        <table border="1" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
            <thead>
                <tr>
                    <th style="text-align: center;">該当ステーション一覧</th>
                </tr>
            </thead>
        </table>

        <table border="1" style="width: 100%; border-collapse: collapse; margin-top: 10px;">
            <thead>
                <tr>
                    <th>ステーション名</th>
                    <th>住所</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <% if (stations != null && !stations.isEmpty()) {
                    for (String[] station : stations) { %>
                        <tr>
                            <td><%= station[0] %></td> <!-- ステーション名 -->
                            <td><%= station[1] %></td> <!-- ステーション住所 -->
                            <td>
                                <a href="./P56.html">
                                    <input type="image" id="select_station" name="select" src="img/syousai.gif" alt="詳細" />
                                </a>
                            </td>
                        </tr>
                    <% } 
                } else { %>
                    <tr>
                        <td colspan="3">該当するステーションが見つかりませんでした。</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </main>
</body>
</html>
