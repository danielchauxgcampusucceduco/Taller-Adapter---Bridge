package edu.ucc.patterns.bridge;

import edu.ucc.patterns.model.ShippingOrder;

public final class StatusNotification extends ShipmentNotification {
    public StatusNotification(NotificationChannel channel) { super(channel); }
    @Override protected String subject(ShippingOrder order) { return "Actualización de envío " + order.trackingCode(); }
    @Override protected String message(ShippingOrder order) {
        return "Tu envío con " + order.quote().provider() + " está " + order.status().toString().toLowerCase() + ".";
    }
}
