package com.example.demo.ui.view;

import com.example.demo.ui.layout.MainAppLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends MainAppLayout implements HasDynamicTitle {

    public MainView() {

    }

    @Override
    public String getPageTitle() {
        return "OrderGen by AHL OMS";
    }
}
