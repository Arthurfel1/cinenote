package com.cinenote.model;

public class SessaoDTO {
    private Long id;
    private String filmeNome;
    private String sala;
    private String horario; // String para facilitar JSON
    private int capacidade;

    public SessaoDTO(Long id, String filmeNome, String sala, String horario, int capacidade) {
        this.id = id;
        this.filmeNome = filmeNome;
        this.sala = sala;
        this.horario = horario;
        this.capacidade = capacidade;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilmeNome() {
        return filmeNome;
    }

    public void setFilmeNome(String filmeNome) {
        this.filmeNome = filmeNome;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
}
