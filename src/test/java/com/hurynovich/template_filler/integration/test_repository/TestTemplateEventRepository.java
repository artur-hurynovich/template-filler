package com.hurynovich.template_filler.integration.test_repository;

import com.hurynovich.template_filler.entity.TemplateEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestTemplateEventRepository extends JpaRepository<TemplateEventEntity, Long> {

}
