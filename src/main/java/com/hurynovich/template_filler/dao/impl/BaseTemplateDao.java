package com.hurynovich.template_filler.dao.impl;

import com.hurynovich.template_filler.dao.TemplateDao;
import com.hurynovich.template_filler.entity.TemplateEntity;
import com.hurynovich.template_filler.entity.TemplateEntity_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.hurynovich.template_filler.entity.TemplateEntity_.NAME;
import static java.util.Optional.ofNullable;

@Repository
public class BaseTemplateDao implements TemplateDao {

    private static final String NEXT_TEMPLATE_ID_QUERY = "select nextval('templates_id_seq')";

    private static final char PERCENT_SYMBOL = '%';

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long getNextId() {
        return (Long) entityManager
                .createNativeQuery(NEXT_TEMPLATE_ID_QUERY, Long.class)
                .getSingleResult();
    }

    @Override
    public TemplateEntity save(final TemplateEntity template) {
        return entityManager.merge(template);
    }

    @Override
    public void deleteById(final Long id) {
        final var template = entityManager.find(TemplateEntity.class, id);

        entityManager.remove(template);
    }

    @Override
    public Optional<TemplateEntity> findById(final Long id) {
        final var template = entityManager.find(TemplateEntity.class, id);

        return ofNullable(template);
    }

    @Override
    public List<TemplateEntity> findAll() {
        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(TemplateEntity.class);
        query.from(TemplateEntity.class);

        return entityManager
                .createQuery(query)
                .getResultList();
    }

    @Override
    public boolean existsByName(final String name) {
        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(TemplateEntity.class);
        final var root = query.from(TemplateEntity.class);
        final var equalNamePredicate = criteriaBuilder.equal(root.get(NAME), name);
        query.where(equalNamePredicate);

        return !entityManager
                .createQuery(query)
                .getResultList()
                .isEmpty();
    }

    @Override
    public boolean existsByNameAndNotId(final String name, final Long id) {
        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(TemplateEntity.class);
        final var root = query.from(TemplateEntity.class);
        final var equalNamePredicate = criteriaBuilder.equal(root.get(NAME), name);
        final var notEqualIdPredicate = criteriaBuilder.notEqual(root.get(TemplateEntity_.ID), id);
        query.where(criteriaBuilder.and(equalNamePredicate, notEqualIdPredicate));

        return !entityManager
                .createQuery(query)
                .getResultList()
                .isEmpty();
    }

    @Override
    public List<TemplateEntity> findAllByNameContaining(final String namePattern) {
        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(TemplateEntity.class);
        final var root = query.from(TemplateEntity.class);
        final var likeNamePredicate = criteriaBuilder.like(root.get(NAME), wrap(namePattern));
        query.where(likeNamePredicate);

        return entityManager
                .createQuery(query)
                .getResultList();
    }

    private String wrap(final String namePattern) {
        return PERCENT_SYMBOL + namePattern + PERCENT_SYMBOL;
    }
}
