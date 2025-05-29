package com.cinenote.controller;

import com.cinenote.model.Filme;
import com.cinenote.model.FilmeDAO;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/adicionarFilme")
public class FilmeAddServlet extends HttpServlet {

    private FilmeDAO filmeDAO = new FilmeDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String atores = request.getParameter("atores");
        String genero = request.getParameter("genero");
        String sinopse = request.getParameter("sinopse");
        String notaStr = request.getParameter("nota");
        String comentario = request.getParameter("comentario");

        int nota;
        try {
            nota = Integer.parseInt(notaStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nota inválida");
            return;
        }

        Filme novoFilme = new Filme(nome, atores, genero, sinopse, nota, comentario);

        try {
            filmeDAO.salvarFilme(novoFilme);

            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String json = new Gson().toJson(novoFilme);
            out.print(json);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao salvar filme");
        }
    }
}
