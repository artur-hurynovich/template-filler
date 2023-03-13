package com.example.hurynovich.template_filler.request;

public class UpdateTemplateRequest extends AbstractTemplateRequest {

    private final Long id;

    public UpdateTemplateRequest(final Long id, final String name, final String payload) {
        super(name, payload);

        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
