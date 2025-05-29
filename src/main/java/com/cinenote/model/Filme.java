package com.cinenote.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do filme é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Os atores principais são obrigatórios")
    @Column(nullable = false)
    private String atores;

    @NotBlank(message = "O gênero é obrigatório")
    @Column(nullable = false)
    private String genero;

    @Size(max = 1000, message = "A sinopse deve ter no máximo 1000 caracteres")
    @Column(length = 1000)
    private String sinopse;

    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 10, message = "A nota deve ser no máximo 10")
    @Column(nullable = false)
    private int nota;

    @Size(max = 1000, message = "O comentário deve ter no máximo 1000 caracteres")
    @Column(length = 1000)
    private String comentario;

    public Filme() {}

    public Filme(String nome, String atores, String genero, String sinopse, int nota, String comentario) {
        this.nome = nome;
        this.atores = atores;
        this.genero = genero;
        this.sinopse = sinopse;
        this.nota = nota;
        this.comentario = comentario;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", atores='" + atores + '\'' +
                ", genero='" + genero + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", nota=" + nota +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
