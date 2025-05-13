package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "filmeBean")
@SessionScoped
public class FilmeBean implements Serializable {

    private String nome;
    private int nota;
    private String comentario;
    private List<Filme> listaFilmes;

    public FilmeBean() {
        listaFilmes = new ArrayList<>();
    }

    public void adicionarFilme() {
        Filme novoFilme = new Filme(nome, nota, comentario);
        listaFilmes.add(novoFilme);
        nome = "";
        nota = 0;
        comentario = "";
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public List<Filme> getListaFilmes() { return listaFilmes; }
}
