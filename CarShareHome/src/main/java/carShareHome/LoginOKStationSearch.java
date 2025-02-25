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

/**
 * Servlet implementation class Station
 */
@WebServlet("/LoginOKStationSearch")
public class LoginOKStationSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginOKStationSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
                sql = "SELECT * FROM Station WHERE station_address LIKE ?";
            } else {
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
                    path = "P55.jsp";
                } else {
                    request.setAttribute("errorMessage", "該当する住所またはステーション名が見つかりませんでした。");
                    path = "P53.jsp";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "データベースエラーが発生しました。");
                path = "P53.jsp";
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "ドライバーが見つかりません。");
            path = "P53.jsp";
        }

        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
}