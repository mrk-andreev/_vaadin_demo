package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public record TemplateData(
        long id,
        String label,
        TargetSystem targetSystem,
        LocalDateTime updatedAt,
        Map<String, String> data
) {
    public TemplateData(long id, TargetSystem targetSystem) {
        this(id, "", targetSystem, LocalDateTime.now(), Map.of());
    }

    public TemplateData update(String label, Map<String, String> data) {
        return new TemplateData(
                id,
                label,
                targetSystem,
                LocalDateTime.now(),
                new HashMap<>(data)
        );
    }
}
