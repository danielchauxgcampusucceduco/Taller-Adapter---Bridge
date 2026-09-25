package edu.ucc.patterns.adapter;

import edu.ucc.patterns.model.ServiceLevel;
import edu.ucc.patterns.model.ShipmentQuote;
import edu.ucc.patterns.model.ShipmentRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/** Adapter that translates the domain request to the legacy courier contract. */
public final class LegacyCourierAdapter implements ShippingProvider {
    private final LegacyCourierSystem legacySystem;

    public LegacyCourierAdapter(LegacyCourierSystem legacySystem) {
        this.legacySystem = Objects.requireNonNull(legacySystem);
    }

    @Override public String name() { return "Mensajería Nacional (adaptado)"; }
    @Override public String toString() { return name(); }

    @Override public ShipmentQuote quote(ShipmentRequest request) {
        boolean rush = request.serviceLevel() != ServiceLevel.STANDARD;
        LegacyCourierSystem.LegacyRate rate = legacySystem.calculateRate(
                request.destination(), request.weightKg().multiply(BigDecimal.valueOf(1000)).doubleValue(), rush);
        return new ShipmentQuote(name(), BigDecimal.valueOf(rate.totalPesos()).setScale(0, RoundingMode.HALF_UP),
                LocalDate.now().plusDays(rate.businessDays()));
    }
}
