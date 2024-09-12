package com.example.demo.ui.filter;

import com.example.demo.dto.TemplatePreviewDto;

public class TemplatePreviewFilter {
    private String searchTerm;

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public boolean test(TemplatePreviewDto dto) {
        return matches(dto.label(), searchTerm);
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
