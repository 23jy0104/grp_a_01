package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginNGStationSarch")
public class LoginNGStationSarch extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginNGStationSarch() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String stationAddress = request.getParameter("stationAddress");
        String addressType = request.getParameter("address");
        String path = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
            final String user = "23jya01";
            final String pass = "23jya01";

            String sql;
            if ("1".equals(addressType)) {
                // 住所で検索
                sql = "SELECT * FROM Station WHERE station_address LIKE ?";
            } else {
                // ステーション名で検索
                sql = "SELECT * FROM Station WHERE station_name LIKE ?";
            }

            List<String[]> stations = new ArrayList<>();

            try (Connection con = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pstmt = con.prepareStatement(sql)) {
                 
                pstmt.setString(1, "%" + stationAddress + "%");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String[] station = new String[4];
                    station[0] = rs.getString("station_id");
                    station[1] = rs.getString("station_name");
                    station[2] = rs.getString("station_address");
                    station[3] = rs.getString("station_data");
                    stations.add(station);
                }

                if (!stations.isEmpty()) {
                    request.getSession().setAttribute("stations", stations);
                    path = "P51.jsp";
                } else {
                    // エラーメッセージをリクエストに設定
                    request.setAttribute("errorMessage", "該当するステーションが見つかりませんでした。");
                    path = "P49.jsp";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
}
