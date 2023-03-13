package com.example.hurynovich.template_filler.request;

public abstract class AbstractTemplateRequest {

    private final String name;

    private final String payload;

    public AbstractTemplateRequest(final String name, final String payload) {
        this.name = name;
        this.payload = payload;
    }

    public String getName() {
        return name;
    }

    public String getPayload() {
        return payload;
    }
}
