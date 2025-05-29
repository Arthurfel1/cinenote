package com.cinenote.controller;

import com.cinenote.model.Sessao;
import com.cinenote.model.SessaoDAO;
import com.cinenote.model.SessaoDTO;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/sessoes")
public class SessaoListServlet extends HttpServlet {

    private SessaoDAO sessaoDAO = new SessaoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Sessao> sessoes = sessaoDAO.listarSessoes();

            List<SessaoDTO> dtoList = sessoes.stream()
                    .map(s -> {
                        String nomeFilme = (s.getFilme() != null) ? s.getFilme().getNome() : "Desconhecido";
                        return new SessaoDTO(
                                s.getId(),
                                nomeFilme,
                                s.getSala(),
                                (s.getHorario() != null ? s.getHorario().toString() : "Indefinido"),
                                s.getCapacidade()
                        );
                    })
                    .collect(Collectors.toList());

            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(dtoList));
            out.flush();
        } catch (Exception e) {
            System.err.println("Erro ao carregar sessões no servlet:");
            e.printStackTrace(); // Log completo no console
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao carregar sessões");
        }
    }
}
