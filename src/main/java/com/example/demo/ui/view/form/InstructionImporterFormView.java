package com.example.demo.ui.view.form;

import com.example.demo.domain.TargetSystem;
import com.example.demo.ui.react.JsonEditor;
import com.example.demo.ui.view.TargetSystemView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class InstructionImporterFormView extends TargetSystemView {
    public InstructionImporterFormView() {
    }

    @Override
    public TargetSystem getTargetSystem() {
        return TargetSystem.INSTRUCTION_IMPORTER;
    }

    @Override
    public VerticalLayout createView(Map<String, String> dataRef) {
        return new VerticalLayout() {
            {
                var binder = new Binder<>(InstructionImporterProxy.class);
                binder.setBean(new InstructionImporterProxy(dataRef));

                TextField assetClassField = new TextField("Asset class");
                binder.forField(assetClassField).bind(
                        InstructionImporterProxy::getAssetClass,
                        InstructionImporterProxy::setAssetClass);
                TextField instructionTypeField = new TextField("Instruction type");
                binder.forField(instructionTypeField).bind(
                        InstructionImporterProxy::getInstructionType,
                        InstructionImporterProxy::setInstructionType);

                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.add(assetClassField);
                horizontalLayout.add(instructionTypeField);
                add(horizontalLayout);

                JsonEditor jsonEditor = new JsonEditor();
                binder.forField(jsonEditor).bind(
                        InstructionImporterProxy::getPayload,
                        InstructionImporterProxy::setPayload);
                add(jsonEditor);
            }
        };
    }

    private record InstructionImporterProxy(Map<String, String> dataRef) {
        public String getAssetClass() {
            return dataRef.getOrDefault("assetClass", "");
        }

        public void setAssetClass(String value) {
            dataRef.put("assetClass", value);
        }

        public String getInstructionType() {
            return dataRef.getOrDefault("instructionType", "");
        }

        public void setInstructionType(String value) {
            dataRef.put("instructionType", value);
        }

        public String getPayload() {
            return dataRef.getOrDefault("payload", "");
        }

        public void setPayload(String value) {
            dataRef.put("payload", value);
        }
    }
}
