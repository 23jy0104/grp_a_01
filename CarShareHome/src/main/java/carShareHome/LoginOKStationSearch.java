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
@WebServlet("/LoginOKStationSearch.java")
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
        String path = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
            final String user = "23jya01";
            final String pass = "23jya01";
            
            String sql = "select * From Station where station_Name LIKE ?";
            List<String[]> stations = new ArrayList<>(); // ステーション情報を格納するリスト
            
            try (Connection con = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pstmt = con.prepareStatement(sql)) {
                 
                pstmt.setString(1, "%" + stationAddress + "%");
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    String[] station = new String[4];
                    station[0] =rs.getString("station_id");
                    station[1] = rs.getString("station_name");
                    station[2] = rs.getString("station_address");
                    station[3] =rs.getString("station_data");
                    stations.add(station); // ステーション情報をリストに追加
                }
                
                if (!stations.isEmpty()) {
                    request.getSession().setAttribute("stations", stations); // リストをリクエスト属性に設定
                    path = "P55.jsp"; // ステーション情報を表示するJSP
                } else {
                    path = "P53.jsp"; // データが見つからない場合の遷移
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