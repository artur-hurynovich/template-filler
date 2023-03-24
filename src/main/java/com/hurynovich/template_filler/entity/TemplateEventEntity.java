package com.hurynovich.template_filler.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "template_events")
public class TemplateEventEntity {

    @Id
    @SequenceGenerator(name = "template_events_id_seq", sequenceName = "template_events_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "template_events_id_seq")
    private Long id;

    @Enumerated(STRING)
    private TemplateEventType type;

    private Long templateId;

    private String templateName;

    private String templatePayload;

    private LocalDateTime dateTime;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public TemplateEventType getType() {
        return type;
    }

    public void setType(final TemplateEventType type) {
        this.type = type;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(final Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(final String templateName) {
        this.templateName = templateName;
    }

    public String getTemplatePayload() {
        return templatePayload;
    }

    public void setTemplatePayload(final String templatePayload) {
        this.templatePayload = templatePayload;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
