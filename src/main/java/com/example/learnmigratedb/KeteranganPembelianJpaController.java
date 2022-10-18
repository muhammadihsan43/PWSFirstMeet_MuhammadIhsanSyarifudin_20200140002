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
public class KeteranganPembelianJpaController implements Serializable {

    public KeteranganPembelianJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example_learnmigratedb_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(KeteranganPembelian keteranganPembelian) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(keteranganPembelian);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKeteranganPembelian(keteranganPembelian.getIdBarang()) != null) {
                throw new PreexistingEntityException("KeteranganPembelian " + keteranganPembelian + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(KeteranganPembelian keteranganPembelian) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            keteranganPembelian = em.merge(keteranganPembelian);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = keteranganPembelian.getIdBarang();
                if (findKeteranganPembelian(id) == null) {
                    throw new NonexistentEntityException("The keteranganPembelian with id " + id + " no longer exists.");
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
            KeteranganPembelian keteranganPembelian;
            try {
                keteranganPembelian = em.getReference(KeteranganPembelian.class, id);
                keteranganPembelian.getIdBarang();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The keteranganPembelian with id " + id + " no longer exists.", enfe);
            }
            em.remove(keteranganPembelian);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<KeteranganPembelian> findKeteranganPembelianEntities() {
        return findKeteranganPembelianEntities(true, -1, -1);
    }

    public List<KeteranganPembelian> findKeteranganPembelianEntities(int maxResults, int firstResult) {
        return findKeteranganPembelianEntities(false, maxResults, firstResult);
    }

    private List<KeteranganPembelian> findKeteranganPembelianEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KeteranganPembelian.class));
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

    public KeteranganPembelian findKeteranganPembelian(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KeteranganPembelian.class, id);
        } finally {
            em.close();
        }
    }

    public int getKeteranganPembelianCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KeteranganPembelian> rt = cq.from(KeteranganPembelian.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
