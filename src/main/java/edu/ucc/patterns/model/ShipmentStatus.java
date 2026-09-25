package edu.ucc.patterns.model;

public enum ShipmentStatus {
    REGISTERED("Registrado"), IN_TRANSIT("En tránsito"), DELIVERED("Entregado");

    private final String label;
    ShipmentStatus(String label) { this.label = label; }
    @Override public String toString() { return label; }
}
