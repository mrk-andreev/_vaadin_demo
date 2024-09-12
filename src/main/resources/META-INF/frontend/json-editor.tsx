import type { ReactElement } from 'react';
import React, { Component, useState, useRef, useEffect } from 'react';
import { ReactAdapterElement, type RenderHooks } from 'Frontend/generated/flow/ReactAdapter';
import {EditorView, keymap} from "@codemirror/view"
import {defaultKeymap} from "@codemirror/commands"
import { json } from '@codemirror/lang-json';
import {basicSetup} from "codemirror"
import {EditorState} from "@codemirror/state"

class JsonEditorElement extends ReactAdapterElement {
  protected override render(hooks: RenderHooks): ReactElement | null {
    const [code, setCode] = hooks.useState<string>('value');

    const containerRef = useRef(null);
    const shadowRootRef = useRef(null);

    useEffect(() => {
        if (containerRef.current && !shadowRootRef.current) {
          shadowRootRef.current = containerRef.current.attachShadow({ mode: "open" });
          const element = document.createElement('div');
          shadowRootRef.current.append(element);

          const updateListener = EditorView.updateListener.of((update) => {
              if (update.docChanged) {
                setCode(update.state.doc.toString());
              }
          });

          const state = EditorState.create({
              doc: code,
              extensions: [basicSetup, keymap.of(defaultKeymap), json(), updateListener]
          });

          new EditorView({
              state,
              parent: element,
              root: shadowRootRef.current,
          });
        }
    }, []);

    return (<>
        <div ref={containerRef}></div>
    </>);
  }
}

customElements.define('json-editor', JsonEditorElement);