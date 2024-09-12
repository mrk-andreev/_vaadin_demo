package com.example.demo.ui.react;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;


@NpmPackage(value = "xterm", version = "5.3.0")
@NpmPackage(value = "xterm-addon-fit", version = "0.8.0")
@CssImport(value = "xterm/css/xterm.css")
@JsModule("./terminal-field.tsx")
@Tag("terminal-field")
public class TerminalField extends AbstractSinglePropertyField<JsonEditor, String> {
    public TerminalField() {
        super("value", "", false);
        getStyle().set("width", "100%");
    }
}
