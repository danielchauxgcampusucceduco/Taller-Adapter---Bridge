package edu.ucc.patterns.adapter;

/** Simulates a vendor gateway returning a response incompatible with the domain model. */
public final class InternationalCarrierGateway {
    public GatewayOffer requestOffer(String destination, int grams, String tier) {
        int days = "PRIORITY".equals(tier) ? 1 : "EXPRESS".equals(tier) ? 2 : 5;
        long amount = 15_000L + grams * 4L + ("PRIORITY".equals(tier) ? 12_000 : 0);
        return new GatewayOffer(amount, days, "COP");
    }
    public record GatewayOffer(long amount, int transitDays, String currency) { }
}
