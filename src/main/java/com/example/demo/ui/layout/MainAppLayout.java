package com.example.demo.ui.layout;


import com.example.demo.domain.TargetSystem;
import com.example.demo.ui.view.TemplateListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.menubar.MenuBar;

import java.util.Arrays;
import java.util.stream.Collectors;


public class MainAppLayout extends AppLayout {
    public MainAppLayout() {
        var title = new H1("OrderGen by AHL OMS");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)").set("margin", "0")
                .set("position", "absolute");

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
        menuBar.addItem("Logout");

        addToNavbar(title, getNavigation(), menuBar);
    }

    private HorizontalLayout getNavigation() {
        HorizontalLayout navigation = new HorizontalLayout();
        navigation.addClassNames(LumoUtility.JustifyContent.CENTER,
                LumoUtility.Gap.SMALL, LumoUtility.Height.MEDIUM,
                LumoUtility.Width.FULL);

        navigation.add(
                Arrays.stream(TargetSystem.values())
                        .map(this::createLink)
                        .collect(Collectors.toUnmodifiableList())
        );
        return navigation;
    }

    private RouterLink createLink(TargetSystem targetSystem) {
        RouterLink link = new RouterLink(TemplateListView.class, new RouteParameters("target", targetSystem.name()));
        link.add(targetSystem.getLabel());
        link.addClassNames(LumoUtility.Display.FLEX,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.TextColor.SECONDARY, LumoUtility.FontWeight.MEDIUM);
        link.getStyle().set("text-decoration", "none");

        return link;
    }
}
