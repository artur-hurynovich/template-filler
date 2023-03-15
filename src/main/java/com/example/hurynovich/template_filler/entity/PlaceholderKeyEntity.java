package com.example.hurynovich.template_filler.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "placeholder_keys")
public class PlaceholderKeyEntity {

    @Id
    @SequenceGenerator(name = "placeholder_keys_id_seq", sequenceName = "placeholder_keys_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "placeholder_keys_id_seq")
    private Long id;

    private String placeholderKey;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private TemplateEntity template;

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

    public TemplateEntity getTemplate() {
        return template;
    }

    public void setTemplate(final TemplateEntity template) {
        this.template = template;
    }
}
