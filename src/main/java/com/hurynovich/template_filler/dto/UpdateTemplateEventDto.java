package com.hurynovich.template_filler.dto;

public record UpdateTemplateEventDto(Long id, Long templateId, String templateName, String templatePayload) {

}
