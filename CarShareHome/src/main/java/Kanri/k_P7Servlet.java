package Kanri;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CarInfoDao;
import model.CarInfo;

@WebServlet("/k_P7Servlet")
public class k_P7Servlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // GETリクエストが来た場合の処理
        String stationName = request.getParameter("stationName");
        String stationAddress = request.getParameter("stationAddress");
        
        // 車両情報を最初に取得してリクエスト属性に設定（必要に応じて）
        CarInfoDao carInfoDao = new CarInfoDao();
        List<CarInfo> carInfoList = carInfoDao.getAllCarInfo();
        
        request.setAttribute("stationName", stationName);
        request.setAttribute("stationAddress", stationAddress);
        request.setAttribute("carInfoList", carInfoList);
        
        // JSPにフォワード
        request.getRequestDispatcher("k_P7.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectedPlate = request.getParameter("selectedPlate");

        CarInfoDao carInfoDao = new CarInfoDao();
        CarInfo carInfo = carInfoDao.getCarInfoByNumber(selectedPlate);

        String stationName = request.getParameter("stationName");
        String stationAddress = request.getParameter("stationAddress");

        request.setAttribute("stationName", stationName);
        request.setAttribute("stationAddress", stationAddress);
        request.setAttribute("carInfo", carInfo);

        request.getRequestDispatcher("k_P7.jsp").forward(request, response);
    }
}
