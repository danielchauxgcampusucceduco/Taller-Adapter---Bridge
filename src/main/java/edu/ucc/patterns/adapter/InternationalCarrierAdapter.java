package edu.ucc.patterns.adapter;

import edu.ucc.patterns.model.ShipmentQuote;
import edu.ucc.patterns.model.ShipmentRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public final class InternationalCarrierAdapter implements ShippingProvider {
    private final InternationalCarrierGateway gateway;
    public InternationalCarrierAdapter(InternationalCarrierGateway gateway) { this.gateway = Objects.requireNonNull(gateway); }
    @Override public String name() { return "Global Express (adaptado)"; }
    @Override public String toString() { return name(); }
    @Override public ShipmentQuote quote(ShipmentRequest request) {
        var offer = gateway.requestOffer(request.destination(), request.weightKg().movePointRight(3).intValueExact(), request.serviceLevel().name());
        return new ShipmentQuote(name(), BigDecimal.valueOf(offer.amount()), LocalDate.now().plusDays(offer.transitDays()));
    }
}
