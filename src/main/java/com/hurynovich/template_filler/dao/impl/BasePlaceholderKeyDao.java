package com.hurynovich.template_filler.dao.impl;

import com.hurynovich.template_filler.dao.PlaceholderKeyDao;
import com.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hurynovich.template_filler.entity.PlaceholderKeyEntity_.TEMPLATE;
import static com.hurynovich.template_filler.entity.TemplateEntity_.ID;

@Repository
public class BasePlaceholderKeyDao implements PlaceholderKeyDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PlaceholderKeyEntity> findAllByTemplateId(final Long templateId) {
        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(PlaceholderKeyEntity.class);
        final var root = query.from(PlaceholderKeyEntity.class);
        query
                .select(root)
                .where(criteriaBuilder.equal(root
                        .join(TEMPLATE)
                        .get(ID), templateId));

        return entityManager
                .createQuery(query)
                .getResultList();
    }
}
