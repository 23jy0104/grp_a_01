package Kanri;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.KeyboxDao;
import model.ReturnKeybox;

/**
 * Servlet implementation class k_P25Servlet
 */
@WebServlet("/k_P25Servlet")
public class k_P25Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public k_P25Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String customerId = request.getParameter("customerId");
		String stationId = request.getParameter("stationId");
		String reservationId = request.getParameter("reservationId");
		String selectKeyBoxId = request.getParameter("selectKeyBoxId");
		String setKeyBoxId = request.getParameter("setKeyBoxId");
		List<ReturnKeybox> keyboxList  = null;
		KeyboxDao keyboxDao = new KeyboxDao();
	    keyboxList = keyboxDao.getYobiKeybox(stationId);
	    boolean checkErr = false;
		if (setKeyBoxId != null && !setKeyBoxId.equals("")) {
				for(ReturnKeybox keyBox : keyboxList) {
					if(keyBox.getKeyboxStatus().equals("2")) {
						checkErr = true;
						break;
					}
				}
			if(!checkErr) {
				for(ReturnKeybox keyBox : keyboxList) {
					if (setKeyBoxId.equals(keyBox.getKeyboxId())) {
						// 0 > 1
						keyboxDao.updateKeyboxStatus(keyBox.getStationId(), keyBox.getKeyboxId(), "1");
					}else if(keyBox.getKeyboxStatus().equals("1")) {
						// 1 > 0
						keyboxDao.updateKeyboxStatus(keyBox.getStationId(), keyBox.getKeyboxId(), "0");
					}
				}
				keyboxList = keyboxDao.getYobiKeybox(stationId);
			}
		}
		String henkyakubuttonDisabled = "disabled";
		String buttonDisabled = "";
		String henkyakuColor = "";
		for(ReturnKeybox keyBox : keyboxList) {
			if(keyBox.getKeyboxStatus().equals("2")) {
				henkyakubuttonDisabled = "";
				buttonDisabled = "disabled";
				henkyakuColor = "orange";
				selectKeyBoxId = keyBox.getKeyboxId();
				break;
			}
		}
		

		request.setAttribute("customerId", customerId);
		request.setAttribute("stationId", stationId);
		request.setAttribute("reservationId", reservationId);
		request.setAttribute("selectKeyBoxId", selectKeyBoxId);
		request.setAttribute("keyboxList", keyboxList);
		request.setAttribute("henkyakubuttonDisabled", henkyakubuttonDisabled);
		request.setAttribute("buttonDisabled", buttonDisabled);
		request.setAttribute("henkyakuColor", henkyakuColor);
		request.getRequestDispatcher("k_P29.jsp").forward(request, response);
		
	}

}
