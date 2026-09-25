package edu.ucc.patterns.service;

import edu.ucc.patterns.adapter.ShippingProvider;
import edu.ucc.patterns.bridge.DeliveryNotification;
import edu.ucc.patterns.bridge.DeliveryResult;
import edu.ucc.patterns.bridge.NotificationChannel;
import edu.ucc.patterns.bridge.StatusNotification;
import edu.ucc.patterns.model.ShipmentQuote;
import edu.ucc.patterns.model.ShipmentRequest;
import edu.ucc.patterns.model.ShipmentStatus;
import edu.ucc.patterns.model.ShippingOrder;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class LogisticsService {
    private final OrderRepository repository;
    public LogisticsService(OrderRepository repository) { this.repository = Objects.requireNonNull(repository); }
    public ShipmentQuote quote(ShippingProvider provider, ShipmentRequest request) { return provider.quote(request); }
    public ShippingOrder createOrder(ShippingProvider provider, ShipmentRequest request) {
        ShippingOrder order = new ShippingOrder("ENV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(), request, provider.quote(request));
        repository.save(order);
        return order;
    }
    public List<DeliveryResult> changeStatus(ShippingOrder order, ShipmentStatus status, List<NotificationChannel> channels) {
        order.updateStatus(status);
        return channels.stream().map(channel -> status == ShipmentStatus.DELIVERED
                ? new DeliveryNotification(channel).send(order) : new StatusNotification(channel).send(order)).toList();
    }
}
