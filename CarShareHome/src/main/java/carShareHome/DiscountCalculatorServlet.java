package carShareHome;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DiscountDao;
import model.Discount;

@WebServlet("/DiscountCalculatorServlet")
public class DiscountCalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int BASE_RATE_PER_15_MINUTES = 440; // 15分あたりの基本料金
    private static final int INSURANCE_FEE = 550; // 保険料金

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startTimestamp = (String)request.getSession().getAttribute("startDate");
        String endTimestamp = (String)request.getSession().getAttribute("endDate");
        String stationId =(String)request.getSession().getAttribute("stationId");
        String carCode =(String)request.getSession().getAttribute("carCode");
        String img =(String)request.getSession().getAttribute("img");
        String stationName =(String)request.getSession().getAttribute("stationName");
        String modelName =(String)request.getSession().getAttribute("modelName");
        String number =(String)request.getSession().getAttribute("number");
        // 時間の計算
        long durationInMillis = calculateDuration(startTimestamp, endTimestamp);
        int durationHours = (int) (durationInMillis / (1000 * 60 * 60)); // ミリ秒を時間に変換

        // 割引情報を取得
        DiscountDao discountDao = new DiscountDao();
        List<Discount> discounts = discountDao.getDiscounts();
        discountDao.connectionClose(); // 接続を閉じる

        // 料金計算
        int totalCost = calculateTotalCost(durationHours, discounts);

        // 結果をリクエストに設定
        request.setAttribute("totalCost", totalCost);
        
        
        request.setAttribute("stationId", stationId);
        request.setAttribute("carCode", carCode);
        request.setAttribute("img",img);
        request.setAttribute("stationName", stationName);
        request.setAttribute("modelName", modelName);
        request.setAttribute("number", number);
        request.setAttribute("startTimestamp", startTimestamp); 
        request.setAttribute("endTimestamp", endTimestamp);
        request.getRequestDispatcher("P63.jsp").forward(request, response); // 結果をresult.jspにフォワード
    }

    private long calculateDuration(String startTimestamp, String endTimestamp) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = format.parse(startTimestamp);
            Date endDate = format.parse(endTimestamp);
            return endDate.getTime() - startDate.getTime(); // ミリ秒単位の差
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int calculateTotalCost(int durationHours, List<Discount> discounts) {
        // 基本料金の計算
        int basicCost = (durationHours * 60 / 15) * BASE_RATE_PER_15_MINUTES; // 15分単位で計算
        int insuranceCost = INSURANCE_FEE; // 保険料金は固定

        // 割引の計算
        int discount = calculateDiscount(durationHours, discounts);

        // 合計料金の計算
        int totalCost = basicCost + insuranceCost - discount;
        return totalCost > 0 ? totalCost : 0; // 料金が負にならないように
    }

    private int calculateDiscount(int durationHours, List<Discount> discounts) {
        int totalDiscount = 0;

        for (Discount discount : discounts) {
            if (durationHours >= discount.getTime()) {
                totalDiscount  = discount.getDiscountPrice();
            }
        }

        return totalDiscount;
    }
}
