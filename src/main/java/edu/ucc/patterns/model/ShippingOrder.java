package edu.ucc.patterns.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ShippingOrder {
    private final String trackingCode;
    private final ShipmentRequest request;
    private final ShipmentQuote quote;
    private final LocalDateTime createdAt;
    private ShipmentStatus status;

    public ShippingOrder(String trackingCode, ShipmentRequest request, ShipmentQuote quote) {
        this.trackingCode = Objects.requireNonNull(trackingCode);
        this.request = Objects.requireNonNull(request);
        this.quote = Objects.requireNonNull(quote);
        this.createdAt = LocalDateTime.now();
        this.status = ShipmentStatus.REGISTERED;
    }

    public String trackingCode() { return trackingCode; }
    public ShipmentRequest request() { return request; }
    public ShipmentQuote quote() { return quote; }
    public LocalDateTime createdAt() { return createdAt; }
    public ShipmentStatus status() { return status; }
    public void updateStatus(ShipmentStatus newStatus) { status = Objects.requireNonNull(newStatus); }
}
