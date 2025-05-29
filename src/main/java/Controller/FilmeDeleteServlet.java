
package Controller;


import com.cinenote.model.FilmeDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/excluirFilme/*")
public class FilmeDeleteServlet extends HttpServlet {

    private FilmeDAO filmeDAO = new FilmeDAO();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo(); // ex: /123
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do filme é obrigatório");
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1)); // remove o "/"
            boolean excluido = filmeDAO.excluirFilme(id); // implementar no DAO

            if (excluido) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Filme não encontrado");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao excluir filme");
        }
    }
}
