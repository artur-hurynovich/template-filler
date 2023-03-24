package com.hurynovich.template_filler.dto;

public record CreateTemplateEventDto(Long id, Long templateId, String templateName, String templatePayload) {

}
