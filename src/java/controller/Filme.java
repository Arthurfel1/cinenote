package controller;

public class Filme {
    private String nome;
    private int nota;
    private String comentario;

    public Filme(String nome, int nota, String comentario) {
        this.nome = nome;
        this.nota = nota;
        this.comentario = comentario;
    }

    public String getNome() { return nome; }
    public int getNota() { return nota; }
    public String getComentario() { return comentario; }
}
