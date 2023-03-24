package com.hurynovich.template_filler.dao;

import com.hurynovich.template_filler.entity.PlaceholderKeyEntity;

import java.util.List;

public interface PlaceholderKeyDao {

    List<PlaceholderKeyEntity> findAllByTemplateId(Long templateId);
}
