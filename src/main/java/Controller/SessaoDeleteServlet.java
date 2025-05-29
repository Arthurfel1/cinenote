
package Controller;


import com.cinenote.model.SessaoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/excluirSessao/*")
public class SessaoDeleteServlet extends HttpServlet {

    private SessaoDAO sessaoDAO = new SessaoDAO();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo(); // ex: /123
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da sessão é obrigatório");
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1));
            boolean excluido = sessaoDAO.excluirSessao(id); // implementar no DAO

            if (excluido) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sessão não encontrada");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao excluir sessão");
        }
    }
}

