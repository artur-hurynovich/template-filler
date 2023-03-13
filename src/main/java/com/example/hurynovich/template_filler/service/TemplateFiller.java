package com.example.hurynovich.template_filler.service;

import java.util.Map;

public interface TemplateFiller {

    String fill(Long templateId, Map<String, String> placeholders);
}
