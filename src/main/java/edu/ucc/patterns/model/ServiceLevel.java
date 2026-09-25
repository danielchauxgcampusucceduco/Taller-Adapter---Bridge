package edu.ucc.patterns.model;

public enum ServiceLevel {
    STANDARD("Estándar"), EXPRESS("Express"), PRIORITY("Prioritario");

    private final String label;

    ServiceLevel(String label) { this.label = label; }

    @Override public String toString() { return label; }
}
