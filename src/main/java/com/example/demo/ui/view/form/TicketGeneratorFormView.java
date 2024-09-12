package com.example.demo.ui.view.form;

import com.example.demo.domain.TargetSystem;
import com.example.demo.ui.react.JsonEditor;
import com.example.demo.ui.view.TargetSystemView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class TicketGeneratorFormView extends TargetSystemView {
    public TicketGeneratorFormView() {
    }

    @Override
    public TargetSystem getTargetSystem() {
        return TargetSystem.TICKET_GENERATOR;
    }

    @Override
    public VerticalLayout createView(Map<String, String> dataRef) {
        return new VerticalLayout() {
            {
                var binder = new Binder<>(TicketGeneratorProxy.class);
                binder.setBean(new TicketGeneratorProxy(dataRef));
                JsonEditor jsonEditor = new JsonEditor();
                binder.forField(jsonEditor).bind(TicketGeneratorProxy::getPayload, TicketGeneratorProxy::setPayload);
                add(jsonEditor);
            }
        };
    }

    private record TicketGeneratorProxy(Map<String, String> dataRef) {
        public String getPayload() {
            return dataRef.getOrDefault("payload", "");
        }

        public void setPayload(String value) {
            dataRef.put("payload", value);
        }
    }
}
