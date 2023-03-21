package com.hurynovich.template_filler.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
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

    @OneToMany(mappedBy = "template", cascade = ALL, orphanRemoval = true)
    private List<PlaceholderKeyEntity> placeholderKeys = new ArrayList<>();

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

    public List<PlaceholderKeyEntity> getPlaceholderKeys() {
        return placeholderKeys;
    }

    public void setPlaceholderKeys(final List<PlaceholderKeyEntity> placeholderKeys) {
        this.placeholderKeys = placeholderKeys;
    }

    public void addPlaceholderKey(final PlaceholderKeyEntity placeholderKey) {
        placeholderKey.setTemplate(this);

        placeholderKeys.add(placeholderKey);
    }
}
