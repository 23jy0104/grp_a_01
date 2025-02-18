package Kanri;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ReservationDAO;
import model.Station;

@WebServlet("/getStations")
public class GetStationsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReservationDAO dao = new ReservationDAO();
        List<Station> stations = dao.getStationsWithLessThanThreeCars();

        // 取得したステーションの数をデバッグ出力
        System.out.println("取得したステーションの数: " + stations.size());

        // ステーション情報のデバッグ出力
        for (Station station : stations) {
            System.out.println("ステーションID: " + station.getStationId() + ", 名前: " + station.getStationName() + ", 住所: " + station.getStationAddress());
        }

        // ステーションデータをリクエスト属性に設定
        request.setAttribute("stations", stations);

        // JSPにフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("yourJspPage.jsp");
        dispatcher.forward(request, response);
    }
}
