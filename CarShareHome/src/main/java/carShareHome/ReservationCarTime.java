package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReservationCarTime")
public class ReservationCarTime extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String startdate = request.getParameter("startCalendar");
        String starttime = request.getParameter("startTime");
        String enddate = request.getParameter("endCalendar");
        String endtime = request.getParameter("endTime");
        String stationid = request.getParameter("stationId");
        String modelName =request.getParameter("modelName");

        // 入力チェック
        if (startdate == null || startdate.trim().isEmpty() || 
            starttime == null || starttime.trim().isEmpty() || 
            enddate == null || enddate.trim().isEmpty() || 
            endtime == null || endtime.trim().isEmpty()) {
            
            // エラーメッセージを設定
            request.setAttribute("errorMessage", "すべてのフィールドを正しく入力してください。");
            request.getRequestDispatcher("P59.jsp").forward(request, response);
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Timestamp startTimestamp = null;
        Timestamp endTimestamp = null;

        try {
            startTimestamp = new Timestamp(format.parse(startdate + " " + starttime).getTime());
            endTimestamp = new Timestamp(format.parse(enddate + " " + endtime).getTime());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "日付のフォーマットが不正です。");
            request.getRequestDispatcher("P59.jsp").forward(request, response);
            return;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
            String user = "23jya01";
            String pass = "23jya01";

            // データベース接続
            con = DriverManager.getConnection(url, user, pass);

            // 空いている時間帯を取得するSQLクエリ
            String sql = "SELECT c.car_code, c.model_year, c.number, m.maker_name, mo.model_name "
                    + "FROM car_db c "
                    + "JOIN maker m ON c.maker_id = m.maker_id "
                    + "JOIN model mo ON c.model_id = mo.model_id "
                    + "JOIN keybox k ON c.car_code = k.car_code "
                    + "WHERE k.station_id = ? "  // ステーションIDのプレースホルダ
                    + "  AND c.model_id = ? "     // 車種IDのプレースホルダ
                    + "  AND c.car_code NOT IN ( "
                    + "      SELECT car_code "
                    + "      FROM reservation "
                    + "      WHERE (start_date < ? AND stop_date > ?) "
                    + "  )";
            String modelIdSql = "SELECT model_id FROM model WHERE model_name = ?";
            PreparedStatement modelPs = con.prepareStatement(modelIdSql);
            modelPs.setString(1, modelName);
            ResultSet modelRs = modelPs.executeQuery();

            String modelId = null;
            if (modelRs.next()) {
                modelId = modelRs.getString("model_id");
            }
            modelRs.close();
            modelPs.close();
         ps = con.prepareStatement(sql);
         ps.setString(1, stationid);  // ステーションID
         ps.setString(2, modelId);     // 車種ID (この変数は適切に設定する必要があります)
         ps.setTimestamp(3, endTimestamp); // 終了日時
         ps.setTimestamp(4, startTimestamp); // 開始日時

            rs = ps.executeQuery();

            // 空いている時間帯のロジック
            List<Timestamp[]> availableSlots = new ArrayList<>();
            Timestamp currentStart = startTimestamp;

            while (rs.next()) {
                Timestamp reservedStart = rs.getTimestamp("start_date");
                Timestamp reservedEnd = rs.getTimestamp("stop_date");

                // 予約の前に空いている時間を追加
                if (currentStart.before(reservedStart)) {
                    availableSlots.add(new Timestamp[]{currentStart, reservedStart});
                }
                // 次の開始時間を更新
                currentStart = reservedEnd.after(currentStart) ? reservedEnd : currentStart;
            }

            // 最後の予約の後に空いている時間を追加
            if (currentStart.before(endTimestamp)) {
                availableSlots.add(new Timestamp[]{currentStart, endTimestamp});
            }
            
            // 空いている時間帯をリクエスト属性に設定
            request.setAttribute("availableSlots", availableSlots);

            // P59.jspにフォワード
            request.getRequestDispatcher("P59.jsp").forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "データベースエラーが発生しました。");
            request.getRequestDispatcher("P59.jsp").forward(request, response);
        } finally {
            // リソースのクリーンアップを行う
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
