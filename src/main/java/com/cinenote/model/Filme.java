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

    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 10, message = "A nota deve ser no máximo 10")
    @Column(nullable = false)
    private int nota; // de 1 a 10

    @Size(max = 1000, message = "O comentário deve ter no máximo 1000 caracteres")
    @Column(length = 1000)
    private String comentario;

    // Construtor padrão (necessário para JPA)
    public Filme() {
    }

    // Construtor com parâmetros para facilitar criação de objetos
    public Filme(String nome, int nota, String comentario) {
        this.nome = nome;
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
                ", nota=" + nota +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
