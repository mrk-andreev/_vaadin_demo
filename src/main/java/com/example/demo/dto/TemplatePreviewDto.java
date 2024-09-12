package com.example.demo.dto;

import com.example.demo.domain.TemplateData;

import java.time.LocalDateTime;

public record TemplatePreviewDto(long id, String label, LocalDateTime updatedAt) {
    public static TemplatePreviewDto from(TemplateData t) {
        return new TemplatePreviewDto(t.id(), t.label(), t.updatedAt());
    }
}
