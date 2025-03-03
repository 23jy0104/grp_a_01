package carShareHome;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ReservationDAO;
import model.HenkyakuData;

/**
 * Servlet implementation class StationQRcodeReader
 */
@WebServlet("/StationQRcodeReader")
public class StationQRcodeReader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StationQRcodeReader() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reservationId = request.getParameter("reservationId");
        request.setAttribute("reservationId", reservationId);
        ReservationDAO reservationDao = new ReservationDAO();
        HenkyakuData henkyaku = reservationDao.getUsageById(reservationId);
        String startDate = henkyaku.getStartDate();

        // 現在の時間を取得
        Date currentTime = new Date();

        // startDateをDate型に変換
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // フォーマットを設定
        Date startDateTime;
        try {
            startDateTime = sdf.parse(startDate);
        } catch (ParseException e) {
            throw new ServletException("日付の解析に失敗しました", e);
        }

        // currentTimeがstartDateより後または同じでない場合
        if (currentTime.before(startDateTime)) {
            request.getRequestDispatcher("badqrcode.jsp").forward(request, response);
            return;
        }

        // 予約の状態に応じた処理
        if (henkyaku.getTimeDate() == null || henkyaku.getTimeDate().equals("")) {
            reservationDao.setTimeDate(reservationId);
        } else if (henkyaku.getFinishDate() == null || henkyaku.getFinishDate().equals("")) {
            reservationDao.setFinishDate(reservationId);
        } else {
            System.out.println("指定された予約は利用が終了しています。");
            request.getRequestDispatcher("noqrcode.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("qrcode.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}