package com.example.demo.ui.view;

import com.example.demo.domain.TargetSystem;
import com.example.demo.dto.TemplateDto;
import com.example.demo.service.TemplateService;
import com.example.demo.ui.layout.MainAppLayout;
import com.example.demo.ui.react.TerminalField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Route("template-edit/:id")
public class TemplateEditView extends MainAppLayout implements HasUrlParameter<String>, HasDynamicTitle {
    private final TemplateService templateService;
    private final Map<TargetSystem, TargetSystemView> views;
    private String title;

    public TemplateEditView(
            TemplateService templateService,
            List<TargetSystemView> views
    ) {
        this.templateService = templateService;
        this.views = views.stream()
                .collect(Collectors.toMap(TargetSystemView::getTargetSystem, v -> v));
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {
        RouteParameters parameters = beforeEvent.getRouteParameters();
        long id = Long.parseLong(parameters.get("id").orElseThrow());

        var template = templateService.getTemplate(id);
        title = "Edit " + template.getLabel();
        TargetSystem targetSystem = template.getTargetSystem();

        var dataBinder = new Binder<>(TemplateDto.class);
        dataBinder.setBean(template);

        setContent(
                new VerticalLayout(
                        createNavigation(template, id, targetSystem),
                        createEditor(dataBinder, targetSystem, template)
                )
        );
    }

    private VerticalLayout createEditor(
            Binder<TemplateDto> dataBinder, TargetSystem targetSystem, TemplateDto template) {
        var verticalLayout = new VerticalLayout();
        TextField labelField = new TextField("Label");
        dataBinder.forField(labelField).bind(TemplateDto::getLabel, TemplateDto::setLabel);
        labelField.setWidthFull();
        verticalLayout.add(labelField);

        var view = views.get(targetSystem);
        verticalLayout.add(view.createView(template.getData()));

        return verticalLayout;
    }

    private HorizontalLayout createNavigation(TemplateDto template, long id, TargetSystem targetSystem) {
        var horizontalLayout = new HorizontalLayout();
        {
            Button submitButton = new Button("Submit");
            horizontalLayout.add(submitButton);
            submitButton.addClickListener(e -> {
                templateService.update(id, template.getLabel(), template.getData());

                var dialog = new Dialog();
                VerticalLayout dialogLayout = new VerticalLayout();
                dialogLayout.add(new TerminalField());
                dialogLayout.setPadding(false);
                dialog.add(dialogLayout);
                dialog.setWidthFull();
                dialog.open();
            });
        }
        {
            Button saveButton = new Button("Save");
            saveButton.addClickListener(e ->
                    templateService.update(id, template.getLabel(), template.getData()));
            horizontalLayout.add(saveButton);
        }
        {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                ConfirmDialog dialog = new ConfirmDialog();
                dialog.setHeader("Confirm template delete operation");

                dialog.setRejectable(true);
                dialog.setRejectText("Discard");

                dialog.setConfirmText("Delete");
                dialog.addConfirmListener(event -> {
                    templateService.delete(Set.of(id));
                    deleteButton.getUI().ifPresent(ui -> ui.navigate(TemplateListView.class,
                            new RouteParameters(new RouteParam("target", targetSystem.name()))));
                });
                dialog.open();
            });
            horizontalLayout.add(deleteButton);
        }
        return horizontalLayout;
    }

    @Override
    public String getPageTitle() {
        return title;
    }
}
