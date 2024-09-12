package com.example.demo.ui.react;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

@NpmPackage(value = "@codemirror/view", version = "6.33.0")
@NpmPackage(value = "@codemirror/commands", version = "6.6.1")
@NpmPackage(value = "@codemirror/state", version = "6.4.1")
@NpmPackage(value = "codemirror", version = "6.0.1")
@NpmPackage(value = "@codemirror/lang-json", version = "6.0.1")
@JsModule("./json-editor.tsx")
@Tag("json-editor")
public class JsonEditor extends AbstractSinglePropertyField<JsonEditor, String> {
    public JsonEditor() {
        super("value", "", false);
    }
}
