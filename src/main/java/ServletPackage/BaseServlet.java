package ServletPackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import pl.polsl.model.DbModel;

/**
 * Zaktualizowany BaseServlet obsługujący DbModel.
 */
public abstract class BaseServlet extends HttpServlet {

    protected DbModel model;

    @Override
    public void init() throws ServletException {
        synchronized (getServletContext()) {
            model = (DbModel) getServletContext().getAttribute("lottoModel");
            if (model == null) {
                
                model = new DbModel("my_persistence_unit");
                getServletContext().setAttribute("lottoModel", model);
            }
        }
    }

    @Override
    public void destroy() {
        // Bardzo ważne przy JPA: zamykamy EntityManagerFactory przy wyłączaniu aplikacji
        if (model != null) {
            model.close();
        }
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected abstract void processRequest(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException;
}