package Kanri;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CarInfoDao;
import model.CarInfo;

@WebServlet("/CarInfoServlet")
public class CarInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        System.out.println("きてえう");

        // リクエストから選択されたナンバープレートとステーション名、住所を取得
        String selectedPlate = request.getParameter("selectedPlate");
        String stationName = request.getParameter("stationName");
        String stationAddress = request.getParameter("stationAddress");

        // CarInfoDaoのインスタンスを作成
        CarInfoDao carInfoDao = new CarInfoDao();

        // ナンバープレートに基づいて車両情報を取得
        CarInfo carInfo = carInfoDao.getCarInfoByNumber(selectedPlate);

        // 車両情報をリクエスト属性に設定
        request.setAttribute("carInfo", carInfo);
        request.setAttribute("stationName", stationName);
        request.setAttribute("stationAddress", stationAddress);

        if (carInfo != null) {
            try {
                // URLエンコードを使用
                String encodedPlate = URLEncoder.encode(selectedPlate, "UTF-8");
                String encodedStationName = URLEncoder.encode(stationName, "UTF-8");
                String encodedStationAddress = URLEncoder.encode(stationAddress, "UTF-8");

                // k_P7Servletにリダイレクト
                response.sendRedirect("k_P7Servlet?selectedPlate=" + encodedPlate + 
                                       "&stationName=" + encodedStationName + 
                                       "&stationAddress=" + encodedStationAddress);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                // エラーハンドリング（必要に応じて）
            }
        } else {
            // 車両情報が見つからなかった場合の処理（エラーメッセージなど）
            request.setAttribute("errorMessage", "選択されたナンバープレートに対する情報が見つかりませんでした。");
            request.getRequestDispatcher("/車両管理システム.jsp").forward(request, response);
        }
    }
}
