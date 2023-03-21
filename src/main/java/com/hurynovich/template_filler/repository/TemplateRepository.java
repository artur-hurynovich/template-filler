package com.hurynovich.template_filler.repository;

import com.hurynovich.template_filler.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<TemplateEntity, Long> {

}
