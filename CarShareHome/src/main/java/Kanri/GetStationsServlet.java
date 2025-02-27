package Kanri;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StationDao;
import model.Station;

@WebServlet("/getStations")
public class GetStationsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = request.getParameter("address");

        StationDao stationDao = new StationDao();
        List<Station> stations = stationDao.getStationsByAddress(address);
        stationDao.connectionClose();

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.print("[");
        for (int i = 0; i < stations.size(); i++) {
            Station station = stations.get(i);
            out.print("{");
            out.print("\"station_name\":\"" + station.getStationName() + "\",");
            out.print("\"station_address\":\"" + station.getStationAddress() + "\"");
            out.print("}");
            if (i < stations.size() - 1) {
                out.print(",");
            }
        }
        out.print("]");
        out.flush();
    }
}

