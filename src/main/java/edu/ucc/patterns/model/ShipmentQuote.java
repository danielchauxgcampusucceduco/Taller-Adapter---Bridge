package edu.ucc.patterns.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public record ShipmentQuote(String provider, BigDecimal price, LocalDate estimatedDelivery) {
    public ShipmentQuote {
        provider = Objects.requireNonNull(provider, "Provider is required");
        price = Objects.requireNonNull(price, "Price is required");
        estimatedDelivery = Objects.requireNonNull(estimatedDelivery, "Delivery date is required");
    }
}
