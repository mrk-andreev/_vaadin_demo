package com.example.demo.domain;

public enum TargetSystem {
    TICKET_GENERATOR("TicketGenerator"),
    INSTRUCTION_IMPORTER("InstructionImporter");

    private final String label;

    TargetSystem(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
