package edu.ucc.patterns.model;

import java.math.BigDecimal;
import java.util.Objects;

public record ShipmentRequest(String recipient, String destination, BigDecimal weightKg, ServiceLevel serviceLevel) {
    public ShipmentRequest {
        recipient = requireText(recipient, "Recipient");
        destination = requireText(destination, "Destination");
        Objects.requireNonNull(weightKg, "Weight is required");
        if (weightKg.signum() <= 0) throw new IllegalArgumentException("Weight must be greater than zero");
        Objects.requireNonNull(serviceLevel, "Service level is required");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " is required");
        return value.trim();
    }
}
