<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Customer"%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>顧客申請一覧</title>
    <link rel="stylesheet" href="css/vehicleCon.css">  
</head>
<body>
    <h1>申請一覧画面</h1>
    <table>
        <tr>
            <th>顧客申請者名</th>
            <th>メールアドレス</th>
        </tr>
        <% 
            List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
            if (customerList != null && !customerList.isEmpty()) {
                for (Customer customer : customerList) {
                    // コンソールに顧客情報を表示
                    System.out.println("顧客名: " + customer.getCustomerName() + ", メールアドレス: " + customer.getEmail());
        %>
        <tr>
		    <td>
		        <a href="k_P15Servlet?customerId=<%= customer.getCustomerId() %>&customerName=<%= customer.getCustomerName() %>">
		            <%= customer.getCustomerName() %>
		        </a>
		    </td>
		    <td><%= customer.getEmail() %></td>
		</tr>
        <% 
                }
            } else {
        %>
        <tr>
            <td colspan="2">顧客情報がありません。</td>
        </tr>
        <% 
            }
        %>
    </table>
    <div class="button-container">
        <button onclick="location.href='k_top.jsp'">サインアウト</button>
    </div>
</body>
</html>
