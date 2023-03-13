package com.example.hurynovich.template_filler.request;

import java.util.Map;

public record TemplateFillerRequest(Long templateId, Map<String, String> placeholders) {

}
