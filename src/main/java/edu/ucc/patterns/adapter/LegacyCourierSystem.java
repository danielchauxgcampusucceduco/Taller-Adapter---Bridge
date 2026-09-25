package edu.ucc.patterns.adapter;

/** Simulates an existing supplier library with a different contract and units. */
public final class LegacyCourierSystem {
    public LegacyRate calculateRate(String city, double weightInGrams, boolean rush) {
        double subtotal = 8_000 + (weightInGrams * 3.1);
        return new LegacyRate(subtotal * (rush ? 1.45 : 1.0), rush ? 1 : 3);
    }

    public record LegacyRate(double totalPesos, int businessDays) { }
}
