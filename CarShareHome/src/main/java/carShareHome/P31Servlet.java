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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public P31Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String birthday = request.getParameter("birthday");
		CustomerDao customerDao = new CustomerDao();
		Customer customer = customerDao.searchCustomer(email, tel, birthday);
		if(customer != null) {
			String subject = "パスワード再発行のお知らせ";
			String body = """
					<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>パスワード再発行のお知らせ</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 600px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333;
        }
        a {
            color: #007BFF;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>パスワード再発行のお知らせ</h1>
        <p>この度は、TMCカーシェアをご利用いただきありがとうございます。</p>
        <p>こちらのメールアドレス宛に、パスワードリセットのリクエストを受け付けました。</p>
        <p>パスワードの再設定は以下のリンクからアクセスできます:</p>
        <p>
            <a href="http://localhost:8080/CarShareHome/PasswordChange?customerId=""";
					
			body += customer.getCustomerId();
			body += """
					" target="_blank">パスワード再設定</a>
        </p>
        <p>今後ともTMCカーシェアをよろしくお願いいたします。</p>
        <p>---<br> TMCカーシェアチーム</p>
    </div>
</body>
</html>
					""";
			send(from,email,subject,body);
		}
		request.getRequestDispatcher("P34.jsp").forward(request, response);
	}

}
