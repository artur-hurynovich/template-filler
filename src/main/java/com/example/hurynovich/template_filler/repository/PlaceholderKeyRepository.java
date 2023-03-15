package com.example.hurynovich.template_filler.repository;

import com.example.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceholderKeyRepository extends JpaRepository<PlaceholderKeyEntity, Long> {

    List<PlaceholderKeyEntity> findAllByTemplateId(Long templateId);
}
