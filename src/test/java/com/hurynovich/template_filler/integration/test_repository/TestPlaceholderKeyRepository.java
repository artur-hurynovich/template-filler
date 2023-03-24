package com.hurynovich.template_filler.integration.test_repository;

import com.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestPlaceholderKeyRepository extends JpaRepository<PlaceholderKeyEntity, Long> {

    List<PlaceholderKeyEntity> findAllByTemplateId(Long templateId);
}
