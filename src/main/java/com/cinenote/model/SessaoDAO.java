package com.cinenote.model;

import javax.persistence.*;
import java.util.List;

public class SessaoDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CineNotePU");

    public void salvarSessao(Sessao sessao) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(sessao);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Erro ao salvar a sessão: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Sessao> listarSessoes() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Sessao> query = em.createQuery(
                "SELECT s FROM Sessao s JOIN FETCH s.filme", Sessao.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar sessões: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        } finally {
            em.close();
        }
    }

    public Sessao buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Sessao.class, id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar sessão por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public void removerSessao(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Sessao sessao = em.find(Sessao.class, id);
            if (sessao != null) {
                em.remove(sessao);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Erro ao remover sessão: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Método para exclusão de sessão pelo ID
    public boolean excluirSessao(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Sessao sessao = em.find(Sessao.class, id);
            if (sessao != null) {
                em.remove(sessao);
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

    public void deletarSessoesPorFilme(Long filmeId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int deletedCount = em.createQuery("DELETE FROM Sessao s WHERE s.filme.id = :filmeId")
                                 .setParameter("filmeId", filmeId)
                                 .executeUpdate();
            tx.commit();
            System.out.println("Sessões deletadas do filme " + filmeId + ": " + deletedCount);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Erro ao deletar sessões por filme: " + e.getMessage());
            e.printStackTrace();
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
