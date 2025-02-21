package Kanri;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CarInfoDao;

@WebServlet("/RegisterCarServlet")
public class RegisterCarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String selectedPlate = request.getParameter("selectedPlate");
        String stationName = request.getParameter("stationName");
        String stationAddress = request.getParameter("stationAddress");
        String makerName = request.getParameter("makerName");
        String modelName = request.getParameter("modelName");
        String modelYear = request.getParameter("modelYear");

        CarInfoDao carInfoDao = new CarInfoDao();
        boolean isCarAddedSuccessfully = carInfoDao.addCarToKeybox(selectedPlate, stationName, stationAddress);

        if (isCarAddedSuccessfully) {
        	request.setAttribute("selectedPlate", selectedPlate);
            request.setAttribute("stationName", stationName);
            request.setAttribute("stationAddress", stationAddress);
            request.setAttribute("makerName", makerName);
            request.setAttribute("modelName", modelName);
            request.setAttribute("modelYear", modelYear);

            // k_P10.jspにフォワード
            request.getRequestDispatcher("k_P10.jsp").forward(request, response);
        } else {
            // 失敗した場合の処理
            request.setAttribute("errorMessage", "車両の登録に失敗しました。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}