package com.cinenote.controller;

import com.cinenote.model.Filme;
import com.cinenote.model.FilmeDAO;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/filmes")
public class FilmeApiServlet extends HttpServlet {

    private FilmeDAO filmeDAO = new FilmeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Filme> filmes = filmeDAO.listarFilmes();

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(filmes));
        out.flush();
    }
}
