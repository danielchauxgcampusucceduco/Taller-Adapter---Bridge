package edu.ucc.patterns.bridge;

import edu.ucc.patterns.model.ShippingOrder;
import java.util.Objects;

/** Abstraction side of Bridge. New notification types and channels vary independently. */
public abstract class ShipmentNotification {
    private final NotificationChannel channel;

    protected ShipmentNotification(NotificationChannel channel) { this.channel = Objects.requireNonNull(channel); }
    public DeliveryResult send(ShippingOrder order) {
        return channel.deliver(order.request().recipient(), subject(order), message(order));
    }
    protected abstract String subject(ShippingOrder order);
    protected abstract String message(ShippingOrder order);
}
