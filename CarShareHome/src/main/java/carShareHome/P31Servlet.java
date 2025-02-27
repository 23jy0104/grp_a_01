package carShareHome;

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

/**
 * Servlet implementation class P31Servlet
 */
@WebServlet("/P31Servlet")
public class P31Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        String birthday = request.getParameter("birthday");
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.searchCustomer(email, tel, birthday);
        if (customer != null) {
            String subject = "パスワード再発行のお知らせ";
            String body = String.format(
            	    "パスワード再発行のお知らせ。\r\n\r\n" +
            	    "この度は、TMCカーシェアをご利用いただきありがとうございます。\r\n" +
            	    "こちらのメールアドレス宛に、パスワードリセットのリクエストを受け付けました。\r\n" +
            	    "パスワードの再設定は以下のリンクからアクセスできます:\r\n" +
            	    "http://localhost:8080/CarShareHome/PasswordChange?customerId=%s\r\n\r\n" +
            	    "今後ともTMCカーシェアをよろしくお願いいたします。\r\n" +
            	    "---\r\nTMCカーシェアチーム",
            	    customer.getCustomerId()
            	);


            send(from, email, subject, body);
        }
        request.getRequestDispatcher("P34.jsp").forward(request, response);
    }
}