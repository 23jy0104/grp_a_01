<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 管理TOPページ-->
<!DOCTYPE html>

<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理システム</title>
    <link rel="stylesheet" href="css/top.css">
    <script>
        function openVehicleManagement() {
            window.location.href = "P2.jsp"; 
        }

        function openReservationManagement() {
            window.location.href = "P19.jsp"; 
        }

        function openCustomerManagement() {
            window.location.href = "P11.jsp"; 
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>管理システム</h1>
        <button onclick="openVehicleManagement()">車両管理課</button>
        <button onclick="openReservationManagement()">オペレーター</button>
        <button onclick="openCustomerManagement()">システム管理課</button>
    </div>
</body>
</html>
