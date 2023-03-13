package com.example.hurynovich.template_filler.service;

import com.example.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.example.hurynovich.template_filler.dto.TemplateDto;

import java.util.List;

public interface PlaceholderKeyService {

    void extractPlaceholderKeysAndSave(TemplateDto templateDto);

    List<PlaceholderKeyDto> findAllByTemplateId(Long templateId);

    void deleteAllByTemplateId(Long templateId);
}
