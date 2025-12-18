package com.fintech.erp.web.rest.vm;

import com.fintech.erp.service.document.TemplatePlaceholderDefinition;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TemplatePlaceholderResponse {

    private Long entityId;
    private Map<String, String> values = new LinkedHashMap<>();
    private List<TemplatePlaceholderDefinition> definitions = List.of();

    public TemplatePlaceholderResponse() {}

    public TemplatePlaceholderResponse(Long entityId, Map<String, String> values, List<TemplatePlaceholderDefinition> definitions) {
        this.entityId = entityId;
        setValues(values);
        setDefinitions(definitions);
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values == null ? new LinkedHashMap<>() : new LinkedHashMap<>(values);
    }

    public List<TemplatePlaceholderDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<TemplatePlaceholderDefinition> definitions) {
        this.definitions = definitions == null ? List.of() : List.copyOf(definitions);
    }

    public TemplatePlaceholderResponse withValues(Map<String, String> newValues) {
        setValues(newValues);
        return this;
    }

    public TemplatePlaceholderResponse withDefinitions(List<TemplatePlaceholderDefinition> newDefinitions) {
        setDefinitions(newDefinitions);
        return this;
    }

    public TemplatePlaceholderResponse withEntityId(Long id) {
        setEntityId(id);
        return this;
    }
}
