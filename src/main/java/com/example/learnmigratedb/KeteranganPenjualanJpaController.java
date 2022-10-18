/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.learnmigratedb;

import com.example.learnmigratedb.exceptions.NonexistentEntityException;
import com.example.learnmigratedb.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author hp
 */
public class KeteranganPenjualanJpaController implements Serializable {

    public KeteranganPenjualanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example_learnmigratedb_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(KeteranganPenjualan keteranganPenjualan) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(keteranganPenjualan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKeteranganPenjualan(keteranganPenjualan.getIdNota()) != null) {
                throw new PreexistingEntityException("KeteranganPenjualan " + keteranganPenjualan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(KeteranganPenjualan keteranganPenjualan) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            keteranganPenjualan = em.merge(keteranganPenjualan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = keteranganPenjualan.getIdNota();
                if (findKeteranganPenjualan(id) == null) {
                    throw new NonexistentEntityException("The keteranganPenjualan with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KeteranganPenjualan keteranganPenjualan;
            try {
                keteranganPenjualan = em.getReference(KeteranganPenjualan.class, id);
                keteranganPenjualan.getIdNota();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The keteranganPenjualan with id " + id + " no longer exists.", enfe);
            }
            em.remove(keteranganPenjualan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<KeteranganPenjualan> findKeteranganPenjualanEntities() {
        return findKeteranganPenjualanEntities(true, -1, -1);
    }

    public List<KeteranganPenjualan> findKeteranganPenjualanEntities(int maxResults, int firstResult) {
        return findKeteranganPenjualanEntities(false, maxResults, firstResult);
    }

    private List<KeteranganPenjualan> findKeteranganPenjualanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KeteranganPenjualan.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public KeteranganPenjualan findKeteranganPenjualan(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KeteranganPenjualan.class, id);
        } finally {
            em.close();
        }
    }

    public int getKeteranganPenjualanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KeteranganPenjualan> rt = cq.from(KeteranganPenjualan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
