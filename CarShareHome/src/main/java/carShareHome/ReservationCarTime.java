package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CarData;
import model.Maker;
import model.Model;
import model.Reservation;

/**
 * Servlet implementation class ReservationCarTime
 */
@WebServlet("/ReservationCarTime")
public class ReservationCarTime extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();
        String stationId = (String) session.getAttribute("stationId");
        String modelName = (String) session.getAttribute("modelName");
        String selectedDate = request.getParameter("selectedDate"); // 選択した日付を取得

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CarData> carDataList = new ArrayList<>(); // CarDataのリストを作成
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
            String user = "23jya01";
            String pass = "23jya01";

            // データベース接続
            con = DriverManager.getConnection(url, user, pass);
            
            String sql = "SELECT c.car_code, c.model_year, c.number, m.maker_id, m.maker_name, mo.model_id, mo.model_name, r.start_date, r.stop_date " +
                         "FROM car_db c " +
                         "JOIN maker m ON c.maker_id = m.maker_id " +
                         "JOIN model mo ON c.model_id = mo.model_id " +
                         "JOIN keybox k ON c.car_code = k.car_code " +
                         "JOIN reservation r ON r.car_code = c.car_code " +
                         "WHERE k.station_id = ? " +
                         "AND c.model_id = ? " +
                         "AND r.start_date >= ? " + // 選択した日付以降
                         "AND r.stop_date < DATE_ADD(?, INTERVAL 1 DAY);"; // 選択した日付の次の日まで
            
            ps = con.prepareStatement(sql);
            ps.setString(1, stationId);
            ps.setString(2, modelName);
            ps.setString(3, selectedDate); // 選択した日付をパラメータに設定
            ps.setString(4, selectedDate); // 同じく選択した日付を設定
            rs = ps.executeQuery();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // フォーマットを指定

            while (rs.next()) {
                CarData carData = new CarData(); // CarDataのインスタンスを作成
                Maker maker = new Maker();
                Model model = new Model();
                Reservation re = new Reservation();
                
                carData.setCarCode(rs.getString("car_code"));
                carData.setModelYear(rs.getString("model_year"));
                carData.setCarNumber(rs.getString("number"));
                maker.setMakerName(rs.getString("maker_name"));
                model.setModelName(rs.getString("model_name"));

                // TIMESTAMPをStringに変換
                re.setStartDate(dateFormat.format(rs.getTimestamp("start_date"))); // Stringに変換
                re.setStopDate(dateFormat.format(rs.getTimestamp("stop_date"))); // Stringに変換
                
                carDataList.add(carData); // リストに追加
            }
            request.setAttribute("carDataList", carDataList); // リストをリクエストにセット
            request.setAttribute("hasResults", !carDataList.isEmpty()); // ヒットしたかどうかをリクエストにセット
            
            // JSPにフォワード
            request.getRequestDispatcher("P59.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // リソースのクリーンアップ
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
