package com.example.demo.ui.dataprovider;

import com.example.demo.domain.TargetSystem;
import com.example.demo.dto.TemplatePreviewDto;
import com.example.demo.service.TemplateService;
import com.example.demo.ui.filter.TemplatePreviewFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class TemplatePreviewDataProvider {
    private final TemplateService templateService;

    public TemplatePreviewDataProvider(TemplateService templateService) {
        this.templateService = templateService;
    }

    public AbstractBackEndDataProvider<TemplatePreviewDto, TemplatePreviewFilter> create(TargetSystem targetSystem) {
        return new AbstractBackEndDataProvider<>() {
            @Override
            protected Stream<TemplatePreviewDto> fetchFromBackEnd(
                    Query<TemplatePreviewDto, TemplatePreviewFilter> query) {
                var stream = templateService.getTemplates(targetSystem).stream();

                if (query.getFilter().isPresent()) {
                    stream = stream
                            .filter(t -> query.getFilter().get().test(t));
                }

                return stream.skip(query.getOffset()).limit(query.getLimit());
            }

            @Override
            protected int sizeInBackEnd(Query<TemplatePreviewDto, TemplatePreviewFilter> query) {
                return templateService.getTemplatesCount(targetSystem);
            }
        };
    }
}
