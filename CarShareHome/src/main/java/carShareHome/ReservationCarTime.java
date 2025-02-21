package carShareHome;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DatabaseManager;
import model.ReservationManager;
import model.ReservationTime;

@WebServlet("/ReservationCarTime")
public class ReservationCarTime extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReservationManager reservationManager = new ReservationManager();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        String date = request.getParameter("date");
        List<ReservationTime> slots = reservationManager.getReservedSlots(date);
        
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // JSON形式でレスポンスを返す
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        for (int i = 0; i < slots.size(); i++) {
            ReservationTime slot = slots.get(i);
            jsonBuilder.append("{");
            jsonBuilder.append("\"time\":\"").append(slot.getTime()).append("\",");
            jsonBuilder.append("\"status\":\"").append(slot.getStatus()).append("\"");
            jsonBuilder.append("}");
            if (i < slots.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        out.print(jsonBuilder.toString());
        out.flush();
    }


    public List<ReservationTime> getReservedSlots(String date) {
        List<ReservationTime> slots = new ArrayList<>();
        String query = "SELECT time, status FROM reservations WHERE date = ?"; // 予約テーブルのクエリ

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, date);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String time = rs.getString("time");
                String status = rs.getString("status");
                slots.add(new ReservationTime(time, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return slots;
    }
}
