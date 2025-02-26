package Kanri;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MailSendAttached
 */
@WebServlet("/MailSendAttached")
public class MailSendAttached extends HttpServlet {
	private static final long serialVersionUID = 1L;

//	private final String from = "21jygrxx@jynet.jec.ac.jp";	// システムの送信元メールアドレス
//	private final String host = "10.64.144.xx";				// メールサーバのIPアドレス（@161）
//	private final String from = "21jygrxx@jynet2.jec.ac.jp"; // システムの送信元メールアドレス
//	private final String host = "10.42.129.3";					// メールサーバのIPアドレス（@1252）
//	private final String host = "localhost";

	private final String from = "【システムの送信元メールアドレス】";
	private final String host = "【メールサーバのホスト名 or IPアドレス】";
	private final String fileName = "【添付するファイル名（フルパス）】";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String to = request.getParameter("to");
		String subject = request.getParameter("subject");
		String body = "こんにちわ、" + to + "様\n";
		body += request.getParameter("body");
		body += "\r\n21jyxxxxより";
		
		// メール送信
		this.send(from, to, subject, body);
	}

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

			// 添付ファイルの処理
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(body);

			MimeBodyPart mbp2 = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(fileName);
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(MimeUtility.encodeWord(fds.getName()));

			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);

			msg.setContent(mp);

			Transport.send(msg);

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch(java.io.UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
}
