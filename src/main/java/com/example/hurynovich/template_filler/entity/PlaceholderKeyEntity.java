package com.example.hurynovich.template_filler.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "placeholder_keys")
public class PlaceholderKeyEntity {

    @Id
    @SequenceGenerator(name = "placeholder_keys_id_seq", sequenceName = "placeholder_keys_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "placeholder_keys_id_seq")
    private Long id;

    private String placeholderKey;

    private Long templateId;

    public PlaceholderKeyEntity() {

    }

    public PlaceholderKeyEntity(final Long id, final String placeholderKey, final Long templateId) {
        this.id = id;
        this.placeholderKey = placeholderKey;
        this.templateId = templateId;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getPlaceholderKey() {
        return placeholderKey;
    }

    public void setPlaceholderKey(final String placeholderKey) {
        this.placeholderKey = placeholderKey;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(final Long templateId) {
        this.templateId = templateId;
    }
}
