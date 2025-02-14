package carShareHome;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Customer;

/**
 * Servlet implementation class RegisterCustomer
 */
@WebServlet("/RegisterCustomer")
public class RegisterCustomer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 顧客情報を取得
    	String sei = request.getParameter("sei");
        String mei = request.getParameter("mei");
        

        // Customerオブジェクトを作成
        Customer customer = new Customer();
        
        // 他の情報も設定...

        // セッションから顧客リストを取得
        HttpSession session = request.getSession();
        ArrayList<Customer> clist = (ArrayList<Customer>) session.getAttribute("clist");

        // clistがnullの場合は新しく作成
        if (clist == null) {
            clist = new ArrayList<>();
        }

        // 顧客をリストに追加
        clist.add(customer);

        // セッションに顧客リストを保存
        session.setAttribute("clist", clist);

        // 次のページに遷移
        RequestDispatcher rd = request.getRequestDispatcher("confirmation.jsp");
        rd.forward(request, response);
    }
}
