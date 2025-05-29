package com.cinenote.controller;

import com.cinenote.model.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@WebServlet("/adicionarSessao")
public class SessaoAddServlet extends HttpServlet {

    private final SessaoDAO sessaoDAO = new SessaoDAO();
    private final FilmeDAO filmeDAO = new FilmeDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long filmeId = Long.parseLong(request.getParameter("filmeId"));
            String sala = request.getParameter("sala");
            LocalDateTime horario = LocalDateTime.parse(request.getParameter("horario"));
            int capacidade = Integer.parseInt(request.getParameter("capacidade"));

            Filme filme = filmeDAO.buscarPorId(filmeId);
            if (filme == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Filme não encontrado.");
                return;
            }

            Sessao sessao = new Sessao(filme, sala, horario, capacidade);
            sessaoDAO.salvarSessao(sessao);

            // Respondendo com JSON para facilitar AJAX/Fetch no frontend
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"status\":\"ok\"}");

        } catch (NumberFormatException | DateTimeParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao salvar sessão.");
        }
    }
}
