package com.hurynovich.template_filler.command;

public record UpdateTemplateCommand(Long templateId, String templateName, String templatePayload) {

}
