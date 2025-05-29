package com.cinenote.model;

import javax.persistence.*;
import java.util.List;

public class FilmeDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CineNotePU");

    public List<Filme> listarFilmes() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Filme> query = em.createQuery("SELECT f FROM Filme f", Filme.class);
            return query.getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return List.of();
        } finally {
            em.close();
        }
    }

    public void salvarFilme(Filme filme) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(filme);
            tx.commit();
        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    public Filme buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Filme.class, id);
        } finally {
            em.close();
        }
    }

    // Método para exclusão de filme pelo ID
    public boolean excluirFilme(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Filme filme = em.find(Filme.class, id);
            if (filme != null) {
                em.remove(filme);
                tx.commit();
                return true;
            } else {
                tx.rollback();
                return false;
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public static void fechar() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
