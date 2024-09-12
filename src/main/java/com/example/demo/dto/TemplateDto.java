package com.example.demo.dto;

import com.example.demo.domain.TargetSystem;
import com.example.demo.domain.TemplateData;

import java.util.HashMap;
import java.util.Map;

public class TemplateDto {
    private String label;
    private Map<String, String> data;
    private final TargetSystem targetSystem;

    public TemplateDto(String label, Map<String, String> data, TargetSystem targetSystem) {
        this.label = label;
        this.data = new HashMap<>(data);
        this.targetSystem = targetSystem;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public static TemplateDto from(TemplateData templateData) {
        return new TemplateDto(templateData.label(), templateData.data(), templateData.targetSystem());
    }

    public TargetSystem getTargetSystem() {
        return targetSystem;
    }
}
