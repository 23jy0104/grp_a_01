package Kanri;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

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

import service.CreateQR;

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

	private final String from = "gr0101a@jynet.jec.ac.jp";
	private final String host = "10.64.144.9";
	private String fileName = "qr_code.png";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String to = request.getParameter("to");
		String subject = "利用開始のお知らせ";
		String body = String.format(
		    "この度は、TMCカーシェアにご利用いただきありがとうございます。\r\n\r\n" +
		    "お客様のご予約された車両のQRコードをお送りいたします。\r\n" +
		    "つきましては、添付された画像をご確認ください。\r\n\r\n" +
		    "お客様のキーボックスは\r\n" +
		    "登録条件を満たしていなかったため\r\n" +
		    "その他の理由\r\n\r\n" +
		    "今後のご利用をお待ちしております。再度申請される場合は、必要な情報を再確認の上、申請をお願いいたします。\r\n" +
		    "http://localhost:8080/CarShareHome/P4.jsp\r\n\r\n" +
		    "今後ともTMCカーシェアをよろしくお願いいたします。\r\n" +
		    "---\r\nTMCカーシェアチーム"
		);
		try {
			String reservationId = "R0018";
			String sendURL = "http://localhost:8080/CarShareHome/StationQRcodeReader?reservationId=" + reservationId;
			
			
			//sendURL = "http://192.168.1.124:8080/CarShareHome/StationQRcodeReader?reservationId=" + reservationId;
			
			fileName = reservationId+".png";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Timer timer = new Timer(false);
			CreateQR.createQr(sendURL ,reservationId+".png"); //QR読み込み後に表示される文字を取得
			File tmpFile = new File(fileName);
			System.out.println(tmpFile.getAbsolutePath());
			TimerTask task = new TimerTask() {
	 
				@Override
				public void run() {
					// メール送信
					send(from, to, subject, body);
					
					timer.cancel();
				}
			};
			timer.schedule(task, sdf.parse("2025-02-26 16:07:00"));  // <-ここにDaoからとてきたstart_timeをString型で入れる
		}catch(ParseException e) {
			e.printStackTrace();
		}
		
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
			mbp2.setFileName(MimeUtility.encodeWord("QRcode.png"));

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
