package com.hurynovich.template_filler.integration.test_repository;

import com.hurynovich.template_filler.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestTemplateRepository extends JpaRepository<TemplateEntity, Long> {

}
