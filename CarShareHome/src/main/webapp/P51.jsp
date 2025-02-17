<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Station" %>

<% 
    List<String[]> stations = (List<String[]>) session.getAttribute("stations"); // ステーション情報を取得
%>


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
        <li class="nav-item gnav02"><a href="P49.jsp">予約・ステーション検索</a></li>
        <li class="nav-item gnav03"><a href="P39.jsp">料金</a></li>
        <li class="nav-item gnav04"><a href="P43.">利用方法</a></li>
        <li class="nav-item gnav05"><a href="P46.jsp">車種</a></li>
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
            for (String[] station : stations) {
            	String stationName = station[1];
                String stationdata = station[3];
                String detailUrl = "P52.jsp?stationname=" + stationName +"&stationdata="+ stationdata; 
        %>
            <tr>
                <td><%= station[1] %></td>
                <td><%= station[2] %></td>
                <td>
                    <a href="<%= detailUrl %>">
                        <input type="image" id="select_station" name="select" src="img/syousai.gif" alt="詳細" />
                    </a>
                </td>
            </tr>
        <% 
            } 
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
