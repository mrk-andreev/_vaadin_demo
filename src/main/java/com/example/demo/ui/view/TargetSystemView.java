package com.example.demo.ui.view;

import com.example.demo.domain.TargetSystem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Map;

public abstract class TargetSystemView {
    public abstract TargetSystem getTargetSystem();

    public abstract VerticalLayout createView(Map<String, String> dataRef);
}
