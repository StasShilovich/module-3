package com.epam.esm.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public abstract class GenericDao<T> {

    private Class<T> clazz;

    @PersistenceContext(unitName = "entityManagerFactory")
    EntityManager entityManager;

    public GenericDao(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public Optional<T> findById(long id) {
        T t = entityManager.find(clazz, id);
        return Optional.of(t);
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(long id) {
        Optional<T> t = findById(id);
        entityManager.remove(t);
    }

    public List<T> findAll(int offset, int limit) {
        String query = "from " + clazz.getName() + " c";
        Query q = entityManager.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit);
        return q.getResultList();
    }

    public long getCountOfEntities() {
        String query = "SELECT COUNT(*) FROM " + clazz.getName() + " c";
        Query q = entityManager.createQuery(query);
        return ((Number) q.getSingleResult()).intValue();
    }
}
