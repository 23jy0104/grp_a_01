<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>

<%
    List<String> makers = (List<String>) request.getAttribute("makers");
    List<String> models = (List<String>) request.getAttribute("models");
    List<String> carNumbers = (List<String>) request.getAttribute("carNumbers");
    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>車両管理システム</title>
    <link rel="stylesheet" href="css/search.css">  
</head>
<body>

    <h1>車両選択</h1>

    <% if (errorMessage != null) { %>
        <p style="color:red;"><%= errorMessage %></p>
    <% } %>

    <div>
        <label for="maker">メーカー:</label>
        <select id="maker">
            <option value="">--選択してください--</option>
            <%
                if (makers != null) {
                    for (String maker : makers) {
            %>
            <option value="<%= maker %>"><%= maker %></option>
            <%
                    }
                } else {
            %>
            <option value="">メーカー情報がありません</option>
            <%
                }
            %>
        </select>
    </div>

    <div>
        <label for="model">モデル:</label>
        <select id="model">
            <option value="">--選択してください--</option>
            <%
                if (models != null) {
                    for (String model : models) {
            %>
            <option value="<%= model %>"><%= model %></option>
            <%
                    }
                } else {
            %>
            <option value="">モデル情報がありません</option>
            <%
                }
            %>
        </select>
    </div>

    <div>
        <label for="carNumber">ナンバープレート:</label>
        <select id="carNumber">
            <option value="">--選択してください--</option>
            <%
                if (carNumbers != null) {
                    for (String number : carNumbers) {
            %>
            <option value="<%= number %>"><%= number %></option>
            <%
                    }
                } else {
            %>
            <option value="">ナンバープレート情報がありません</option>
            <%
                }
            %>
        </select>
    </div>

    <button onclick="location.href='nextPage.jsp'">次へ</button>

</body>
</html>
