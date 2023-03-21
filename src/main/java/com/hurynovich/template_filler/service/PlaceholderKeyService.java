package com.hurynovich.template_filler.service;

import com.hurynovich.template_filler.dto.PlaceholderKeyDto;

import java.util.List;

public interface PlaceholderKeyService {

    List<PlaceholderKeyDto> findAllByTemplateId(Long templateId);
}
