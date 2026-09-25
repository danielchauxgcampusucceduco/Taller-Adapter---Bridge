package edu.ucc.patterns.bridge;

import edu.ucc.patterns.model.ShippingOrder;

public final class DeliveryNotification extends ShipmentNotification {
    public DeliveryNotification(NotificationChannel channel) { super(channel); }
    @Override protected String subject(ShippingOrder order) { return "Envío entregado: " + order.trackingCode(); }
    @Override protected String message(ShippingOrder order) {
        return "Confirmamos la entrega del envío dirigido a " + order.request().destination() + ".";
    }
}
