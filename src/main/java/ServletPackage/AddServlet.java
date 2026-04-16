package ServletPackage;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pl.polsl.exception.LotteryDataException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/add")
public class AddServlet extends BaseServlet {

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String drawLine = req.getParameter("draw");

        out.println("<html><body>");
        if (drawLine == null || drawLine.isBlank()) {
            out.println("<h2 style='color:red;'>Error: Data cannot be empty!</h2>");
        } else {
            try {
                // DbModel przetworzy String, zwaliduje go i zapisze przez JPA
                model.addDraw(drawLine);
                out.println("<h2 style='color:green;'>Success!</h2>");
                out.println("<p>Added to database: " + drawLine + "</p>");
            } catch (LotteryDataException e) {
                out.println("<h2 style='color:red;'>Validation/Database Error</h2>");
                out.println("<p>" + e.getMessage() + "</p>");
            }
        }

        out.println("<br><a href='add.html'>Add another</a> | <a href='index.html'>Home</a>");
        out.println("</body></html>");
    }
}