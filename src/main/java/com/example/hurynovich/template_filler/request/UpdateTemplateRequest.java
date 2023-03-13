package com.example.hurynovich.template_filler.request;

public class UpdateTemplateRequest extends AbstractTemplateRequest {

    private final Long id;

    public UpdateTemplateRequest(final String name, final String payload, final Long id) {
        super(name, payload);

        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
