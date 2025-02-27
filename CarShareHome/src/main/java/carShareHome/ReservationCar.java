package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReservationCar
 */
@WebServlet("/ReservationCar")
public class ReservationCar extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationCar() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // リクエストの文字エンコーディングを設定
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // リクエストパラメータの取得
        String carType = request.getParameter("carType");
        String path = "P59.jsp"; // デフォルトの転送先

        // データベース接続に必要な変数
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // JDBCドライバのロード
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
            String user = "23jya01";
            String pass = "23jya01";

            // データベース接続
            conn = DriverManager.getConnection(url, user, pass);

            // SQLクエリの準備
            String sql = "SELECT model_name, s.station_id, k.car_code, car_img,station_name,station_data " +
                         "FROM keybox k " +
                         "INNER JOIN station s ON k.station_id = s.station_id " +
                         "INNER JOIN car_db car ON k.car_code = car.car_code " +
                         "INNER JOIN model m ON m.model_id = car.model_id " +
                         "WHERE model_name = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, carType); // carTypeを使う

            // クエリの実行
            rs = pstmt.executeQuery();
            if (rs.next()) {
                // セッションに必要なデータを設定
                request.getSession().setAttribute("modelName", rs.getString("model_name"));
                request.getSession().setAttribute("stationId", rs.getString("station_id")); // 修正: stationid → stationId
                request.getSession().setAttribute("car_img", rs.getString("car_img"));
                request.getSession().setAttribute("stationName", rs.getString("station_name"));
                request.getSession().setAttribute("stationData", rs.getString("station_data"));
                path ="P59.jsp";
            } else {
                // データが見つからなかった場合の処理
                path = "error.jsp"; // エラーページに転送するなどの処理
            }

            // リクエストディスパッチャーによる転送
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.forward(request, response);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // エラーハンドリング
        } catch (SQLException e) {
            e.printStackTrace();
            // エラーハンドリング
        } finally {
            // リソースをクローズ
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
