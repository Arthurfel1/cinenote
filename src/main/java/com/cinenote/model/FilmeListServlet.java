package com.cinenote.controller;

import com.cinenote.model.Filme;
import com.cinenote.model.FilmeDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/filmes")
public class FilmeListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private FilmeDAO filmeDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        filmeDAO = new FilmeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Filme> filmes = filmeDAO.listarFilmes();
            request.setAttribute("filmes", filmes);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaFilmes.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao carregar lista de filmes");
        }
    }
}
