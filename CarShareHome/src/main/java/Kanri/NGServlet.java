package Kanri;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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

@WebServlet("/NGServlet")
public class NGServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public NGServlet() {
        super();
    }

    private final String from = "gr0101a@jynet.jec.ac.jp";
    private final String host = "10.64.144.9"; // SMTPサーバーのホスト

    private void send(String from, String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);

        Session session = Session.getInstance(props, null);
        session.setDebug(false); // デバッグモードを有効にする場合はtrueに変更

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(body);

            Transport.send(msg);
            System.out.println("メール送信成功: " + subject + " to " + to);
        } catch (MessagingException e) {
            System.err.println("メール送信エラー: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 顧客情報の取得
        CustomerDao customerDao = new CustomerDao();
        List<Customer> customerList = customerDao.getAllCustomers();
        request.setAttribute("customerList", customerList);
        
        // メール送信のためのパラメータを取得
        String email = request.getParameter("email");

        // 必須フィールドのチェック
        if (email == null || email.isEmpty()) {
            System.err.println("必要な情報が不足しています: customerId=" + email);
            request.setAttribute("errorMessage", "全てのフィールドを入力してください。");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        String subject = "登録審査結果のお知らせ";
		String body = String.format(
		    "この度は、TMCカーシェアにご登録いただきありがとうございます。\r\n\r\n" +
		    "お客様の会員登録申請について審査を行いましたが、残念ながら審査に通過しませんでした。\r\n" +
		    "審査不合格の理由については、以下の通りです。\r\n\r\n" +
		    "提供された情報に不備があったため\r\n" +
		    "登録条件を満たしていなかったため\r\n" +
		    "その他の理由\r\n\r\n" +
		    "今後のご利用をお待ちしております。再度申請される場合は、必要な情報を再確認の上、申請をお願いいたします。\r\n" +
		    "http://localhost:8080/CarShareHome/P4.jsp\r\n\r\n" +
		    "今後ともTMCカーシェアをよろしくお願いいたします。\r\n" +
		    "---\r\nTMCカーシェアチーム"
		);

		send(from, email, subject, body);
		System.out.println("審査結果のお知らせメールを送信しました: " + email);
        // JSPにフォワード
        request.getRequestDispatcher("k_P13.jsp").forward(request, response);
    }

}
