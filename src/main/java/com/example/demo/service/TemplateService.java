package com.example.demo.service;

import com.example.demo.domain.TargetSystem;
import com.example.demo.domain.TemplateData;
import com.example.demo.dto.TemplateDto;
import com.example.demo.dto.TemplatePreviewDto;
import com.example.demo.helper.TemplateDataGeneratorHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TemplateService {
    private final AtomicLong idSerial = new AtomicLong(1L);
    private final List<TemplateData> memo = new CopyOnWriteArrayList<>();

    public TemplateService() {
        int count = 1000;
        for (TargetSystem targetSystem : TargetSystem.values()) {
            memo.addAll(TemplateDataGeneratorHelper.newSamples(
                    count,
                    targetSystem,
                    idSerial::incrementAndGet
            ));
        }
    }

    public TemplatePreviewDto create(TargetSystem targetSystem) {
        TemplateData templateData = new TemplateData(idSerial.incrementAndGet(), targetSystem);
        memo.add(templateData);
        return TemplatePreviewDto.from(templateData);
    }

    public TemplateDto getTemplate(long id) {
        return memo.stream()
                .filter(v -> v.id() == id)
                .findFirst()
                .map(TemplateDto::from)
                .orElseThrow();
    }

    public List<TemplatePreviewDto> getTemplates(TargetSystem targetSystem) {
        return memo.stream()
                .filter(t -> t.targetSystem() == targetSystem)
                .map(TemplatePreviewDto::from)
                .toList();
    }

    public int getTemplatesCount(TargetSystem targetSystem) {
        return (int) memo.stream()
                .filter(t -> t.targetSystem() == targetSystem)
                .count();
    }

    public void delete(Set<Long> selectedIds) {
        memo.removeIf(t -> selectedIds.contains(t.id()));
    }

    public void update(long id, String label, Map<String, String> data) {
        memo.replaceAll(templateData -> {
            if (templateData.id() == id) {
                return templateData.update(label, data);
            }

            return templateData;
        });
    }
}
