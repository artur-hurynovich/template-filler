package com.hurynovich.template_filler.dao.impl;

import com.hurynovich.template_filler.dao.TemplateEventDao;
import com.hurynovich.template_filler.entity.TemplateEventEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BaseTemplateEventDao implements TemplateEventDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TemplateEventEntity save(final TemplateEventEntity templateEvent) {
        return entityManager.merge(templateEvent);
    }
}
