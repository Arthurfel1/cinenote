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

        System.out.println("🚀 FilmeAddServlet acionado!");

        request.setCharacterEncoding("UTF-8"); // Garante suporte a caracteres especiais
        String nome = request.getParameter("nome");
        String notaStr = request.getParameter("nota");
        String comentario = request.getParameter("comentario");

        System.out.println("🎬 Dados recebidos: nome=" + nome + ", nota=" + notaStr + ", comentário=" + comentario);

        int nota;
        try {
            nota = Integer.parseInt(notaStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nota inválida");
            return;
        }

        Filme novoFilme = new Filme(nome, nota, comentario);

        try {
            filmeDAO.salvarFilme(novoFilme);
            System.out.println("✅ Filme salvo com sucesso!");

            // Retorna o filme como JSON
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
