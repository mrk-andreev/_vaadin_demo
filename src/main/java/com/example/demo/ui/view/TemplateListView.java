package com.example.demo.ui.view;

import com.example.demo.domain.TargetSystem;
import com.example.demo.dto.TemplatePreviewDto;
import com.example.demo.service.TemplateService;
import com.example.demo.ui.dataprovider.TemplatePreviewDataProvider;
import com.example.demo.ui.layout.MainAppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.HashSet;
import java.util.Set;

@Route("templates-list/:target")
public class TemplateListView extends MainAppLayout implements HasUrlParameter<String>, HasDynamicTitle {
    private final TemplateService templateService;
    private final TemplatePreviewDataProvider templatePreviewDataProvider;
    private String title;

    public TemplateListView(
            TemplateService templateService, TemplatePreviewDataProvider templatePreviewDataProvider) {
        this.templateService = templateService;
        this.templatePreviewDataProvider = templatePreviewDataProvider;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {
        TargetSystem targetSystem = TargetSystem.valueOf(beforeEvent.getRouteParameters().get("target").orElseThrow());
        title = targetSystem.name();

        var items = templatePreviewDataProvider.create(targetSystem).withConfigurableFilter();
        var grid = new Grid<>(items);
        grid.setWidthFull();

        Set<Long> selectedIds = new HashSet<>();
        grid.addComponentColumn(templatePreview -> {
            Checkbox checkbox = new Checkbox();
            checkbox.addValueChangeListener(e -> {
                if (e.getValue()) {
                    selectedIds.add(templatePreview.id());
                } else {
                    selectedIds.remove(templatePreview.id());
                }
            });

            return checkbox;
        });
        grid.addColumn(TemplatePreviewDto::id).setHeader("ID");
        grid.addColumn(TemplatePreviewDto::label).setHeader("Label");
        grid.addColumn(TemplatePreviewDto::updatedAt).setHeader("Updated at");
        grid.addComponentColumn(templatePreview -> {
            Button button = new Button("Edit");
            button.addClickListener(e ->
                    button.getUI().ifPresent(ui -> ui.navigate(TemplateEditView.class,
                                    new RouteParameters(
                                            new RouteParam("id", templatePreview.id())
                                    )
                            )
                    ));

            return button;
        });

        HorizontalLayout actionsLayout = new HorizontalLayout();
        Button createButton = new Button("Create");
        createButton.addClickListener(e -> {
            var newTemplate = templateService.create(targetSystem);
            createButton.getUI().ifPresent(ui -> ui.navigate(TemplateEditView.class,
                    new RouteParameters(
                            new RouteParam("id", newTemplate.id())
                    )
            ));
        });
        actionsLayout.add(createButton);
        Button deleteButton = new Button("Delete");
        deleteButton.addClickListener(e -> {
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Confirm template delete operation");

            dialog.setRejectable(true);
            dialog.setRejectText("Discard");

            dialog.setConfirmText("Delete");
            dialog.addConfirmListener(event -> {
                templateService.delete(selectedIds);
                items.refreshAll();
                selectedIds.clear();
            });
            dialog.open();
        });
        actionsLayout.add(deleteButton);


        VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        layout.add(actionsLayout);
        layout.add(grid);
    }

    @Override
    public String getPageTitle() {
        return title;
    }
}
