package com.example.hurynovich.template_filler.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "templates")
public class TemplateEntity {

    @Id
    @SequenceGenerator(name = "templates_id_seq", sequenceName = "templates_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "templates_id_seq")
    private Long id;

    private String name;

    private String payload;

    public TemplateEntity() {

    }

    public TemplateEntity(final Long id, final String name, final String payload) {
        this.id = id;
        this.name = name;
        this.payload = payload;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(final String payload) {
        this.payload = payload;
    }
}
