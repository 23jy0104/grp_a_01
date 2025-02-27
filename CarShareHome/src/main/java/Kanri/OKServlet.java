package Kanri;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import model.Customer;

@WebServlet("/OKServlet")
public class OKServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public OKServlet() {
        super();
    }

    private final String from = "gr0101a@jynet.jec.ac.jp";
    private final String host = "10.64.144.9";

    private void send(String from, String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);

        Session session = Session.getInstance(props, null);
        session.setDebug(false);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = { new InternetAddress(to) };
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(body);

            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // クエリパラメータからcustomerIdを取得
        String customerId = request.getParameter("customerId");
        String email = request.getParameter("email");

        System.out.println("18サーブレット Customer ID: " + customerId); // デバッグ用メッセージ

        if (customerId == null || customerId.isEmpty()) {
            System.err.println("Customer ID is missing or empty."); // エラーメッセージ
            request.setAttribute("errorMessage", "Customer ID is required.");
            request.getRequestDispatcher("error.jsp").forward(request, response); // エラーページにリダイレクト
            return;
        }

        // CustomerDaoを使用して顧客情報を取得
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.getCustomerById(customerId);

        // 顧客情報をリクエスト属性に設定
        if (customer != null) {
            request.setAttribute("customerName", customer.getCustomerName());
            request.setAttribute("customerId", customer.getCustomerId());
            request.setAttribute("tellNumber", customer.getTellNumber());
            request.setAttribute("email", customer.getEmail());
            request.setAttribute("licenseNumber", customer.getLicenseNumber());
            request.setAttribute("licenceDate", customer.getLicenceDate());
            request.setAttribute("postCode", customer.getPostCode());
            request.setAttribute("customerAddress", customer.getCustomerAddress());
            request.setAttribute("omoteJpg", customer.getOmote());
            request.setAttribute("uraJpg", customer.getUra());
        } else {
            System.err.println("No customer found with ID: " + customerId);
        }

        // メール送信処理
        if (email != null) {
            if (customer != null) {
                String subject = "登録審査完了のお知らせ";
                String body = String.format(
                    "登録審査完了のお知らせ\r\n" +
                    "この度は、TMCカーシェアにご登録いただきありがとうございます。\r\n" +
                    "お客様の会員登録申請が完了しました。以下のリンクをクリックして、ログインページにアクセスしてください。\r\n" +
                    "http://localhost:8080/CarShareHome/P29.jsp\r\n\r\n" +
                    "今後ともTMCカーシェアをよろしくお願いいたします。\r\n" +
                    "---\r\nTMCカーシェアチーム",
                    customer.getCustomerId()
                );

                send(from, email, subject, body);
            }
        }

        // JSPにフォワード
        request.getRequestDispatcher("k_P18.jsp").forward(request, response);
    }
}
