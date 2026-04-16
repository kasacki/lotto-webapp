package ServletPackage;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pl.polsl.exception.LotteryDataException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/number")
public class NumberServlet extends BaseServlet {

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
        String numStr = req.getParameter("number");
        String cookieValue = "None";

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("lastSearched".equals(c.getName())) {
                    cookieValue = c.getValue();
                }
            }
        }

        out.println("<html><body><h1>Number Statistics</h1>");
        out.println("<p>Last number searched in this session: <b>" + cookieValue + "</b></p>");

        if (numStr == null || numStr.isBlank()) {
            out.println("<p style='color:red;'>Error: No number provided!</p>");
        } else {
            try {
                int number = Integer.parseInt(numStr);
                Cookie searchCookie = new Cookie("lastSearched", String.valueOf(number));
                searchCookie.setMaxAge(3600);
                resp.addCookie(searchCookie);

                var result = model.daysSinceLast(number);
                if (result.isPresent()) {
                    out.println("<p>Number " + number + " last appeared " + result.get().get() + " days ago.</p>");
                } else {
                    out.println("<p>Number " + number + " has never appeared in the records.</p>");
                }
            } catch (NumberFormatException e) {
                out.println("<p style='color:red;'>Error: '" + numStr + "' is not a valid integer.</p>");
            } catch (LotteryDataException e) {
                out.println("<p style='color:red;'>Database Error: " + e.getMessage() + "</p>");
            }
        }
        out.println("<br><a href='number.html'>Back</a> | <a href='index.html'>Home</a>");
        out.println("</body></html>");
    }
}