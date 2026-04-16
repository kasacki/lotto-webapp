package ServletPackage;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pl.polsl.exception.LotteryDataException;
import pl.polsl.model.Draw;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/draws")
public class DrawsServlet extends BaseServlet {

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html><body><h1>Full Draw History (from Database)</h1>");

        try {
            List<Draw> draws = model.readAllDraws();
            if (draws.isEmpty()) {
                out.println("<p>The database is currently empty.</p>");
            } else {
                out.println("<table border='1'><tr><th>Date</th><th>Numbers</th></tr>");
                for (Draw d : draws) {
                    out.println("<tr><td>" + d.date() + "</td><td>" + d.numbers() + "</td></tr>");
                }
                out.println("</table>");
            }
        } catch (LotteryDataException e) {
            out.println("<p style='color:red;'>Database access error: " + e.getMessage() + "</p>");
        }

        out.println("<br><a href='index.html'>Home</a>");
        out.println("</body></html>");
    }
}