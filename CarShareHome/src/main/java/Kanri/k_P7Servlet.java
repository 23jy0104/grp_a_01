package Kanri;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CarDao;
import dao.MakerDao;
import dao.ModelDao;

@WebServlet("/k_P7Servlet")
public class k_P7Servlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MakerDao makerDao = new MakerDao();
        ModelDao modelDao = new ModelDao();
        CarDao carDao = new CarDao();

        List<String> makers = null;
        List<String> models = null;
        List<String> carNumbers = null;
        String errorMessage = null;

        try {
            makers = makerDao.getAllMakers();
            models = modelDao.getAllModels();
            carNumbers = carDao.getAllCarNumbers();
            
            // デバッグ用の出力
            System.out.println("Makers: " + makers);
            System.out.println("Models: " + models);
            System.out.println("Car Numbers: " + carNumbers);
        } catch (Exception e) {
            errorMessage = "データの取得中にエラーが発生しました。";
            e.printStackTrace(); // エラー詳細をスタックトレースで表示
        } finally {
            makerDao.connectionClose();
            modelDao.connectionClose();
            carDao.connectionClose();
        }

        // リクエスト属性に設定
        request.setAttribute("makers", makers);
        request.setAttribute("models", models);
        request.setAttribute("carNumbers", carNumbers);
        request.setAttribute("errorMessage", errorMessage);

        // JSPにフォワード
        request.getRequestDispatcher("k_P7.jsp").forward(request, response);
    }
}
