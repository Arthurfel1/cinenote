package com.cinenote.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessoes")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "filme_id")
    private Filme filme;

    @Column(nullable = false)
    private String sala;

    @Column(name = "horario", nullable = false)
    private LocalDateTime horario;

    @Column(nullable = false)
    private int capacidade;

    public Sessao() {}

    public Sessao(Filme filme, String sala, LocalDateTime horario, int capacidade) {
        this.filme = filme;
        this.sala = sala;
        this.horario = horario;
        this.capacidade = capacidade;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return "Sessao{" +
                "id=" + id +
                ", filme=" + (filme != null ? filme.getNome() : "null") +
                ", sala='" + sala + '\'' +
                ", horario=" + horario +
                ", capacidade=" + capacidade +
                '}';
    }
}
