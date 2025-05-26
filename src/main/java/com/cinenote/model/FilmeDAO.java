package com.cinenote.model;

import javax.persistence.*;
import java.util.List;

public class FilmeDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CineNotePU");

    /**
     * Lista todos os filmes cadastrados no banco.
     * @return Lista de filmes, ou lista vazia se erro ocorrer.
     */
    public List<Filme> listarFilmes() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Filme> query = em.createQuery("SELECT f FROM Filme f", Filme.class);
            return query.getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return List.of(); // Retorna lista vazia em caso de erro
        } finally {
            em.close();
        }
    }

    /**
     * Salva um novo filme no banco de dados.
     * @param filme Objeto Filme a ser persistido.
     */
    public void salvarFilme(Filme filme) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            System.out.println("Tentando salvar filme: " + filme);
            tx.begin();
            em.persist(filme);
            tx.commit();
            System.out.println("Filme salvo com sucesso: " + filme);
        } catch (PersistenceException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Erro ao salvar filme: " + filme);
            e.printStackTrace();
            throw e; // repassa exceção para tratamento externo
        } finally {
            em.close();
        }
    }

    /**
     * Busca um filme pelo seu ID.
     * @param id ID do filme.
     * @return Filme encontrado ou null se não achar.
     */
    public Filme buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Filme.class, id);
        } finally {
            em.close();
        }
    }
}
