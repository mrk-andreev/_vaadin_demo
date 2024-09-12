import type { ReactElement } from 'react';
import React, { Component, useState, useRef, useEffect } from 'react';
import { ReactAdapterElement, type RenderHooks } from 'Frontend/generated/flow/ReactAdapter';
import 'xterm/css/xterm.css';
import { Terminal } from 'xterm';
import { FitAddon } from 'xterm-addon-fit';


class TerminalField extends ReactAdapterElement {
  protected override render(hooks: RenderHooks): ReactElement | null {
    const [code, setCode] = hooks.useState<string>('value');

    const containerRef = useRef(null);
    const terminalRef = useRef(null);
    const writerRef = useRef(null);

    useEffect(() => {
        if (!terminalRef.current) {
            const terminal = new Terminal();
            terminalRef.current = terminal;
            writerRef.current = setInterval(() => {
                // TODO: add scan remote logic
                terminalRef.current.writeln('Test message ' + new Date());
                }, 1000);
            const fitAddon = new FitAddon();
            terminal.loadAddon(fitAddon);
            terminal.open(containerRef.current);
            fitAddon.fit();
        }

        return () => {
            if (terminalRef.current) {
                terminalRef.current.dispose();
                terminalRef.current = null;
                clearInterval(writerRef.current);
            }
        };
    }, []);

    return (<>
        <div ref={containerRef}></div>
    </>);
  }
}

customElements.define('terminal-field', TerminalField);