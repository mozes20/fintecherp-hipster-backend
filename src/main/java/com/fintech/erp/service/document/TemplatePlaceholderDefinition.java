package com.fintech.erp.service.document;

public class TemplatePlaceholderDefinition {

    private final String key;
    private final String description;

    public TemplatePlaceholderDefinition(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
