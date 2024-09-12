package com.example.demo.helper;

import com.example.demo.domain.TargetSystem;
import com.example.demo.domain.TemplateData;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.LongSupplier;

public final class TemplateDataGeneratorHelper {
    private TemplateDataGeneratorHelper() {
    }

    public static List<TemplateData> newSamples(int count, TargetSystem targetSystem, LongSupplier nextId) {
        List<TemplateData> memo = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            memo.add(new TemplateData(
                    nextId.getAsLong(),
                    targetSystem + " " + UUID.randomUUID().toString(),
                    targetSystem,
                    LocalDateTime.now(),
                    Map.of("payload", "{\"key\": 123}")
            ));
        }
        return memo;
    }
}
