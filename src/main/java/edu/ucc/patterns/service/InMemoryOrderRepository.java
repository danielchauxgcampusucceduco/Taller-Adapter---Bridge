package edu.ucc.patterns.service;

import edu.ucc.patterns.model.ShippingOrder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, ShippingOrder> orders = new LinkedHashMap<>();
    @Override public void save(ShippingOrder order) { orders.put(order.trackingCode(), order); }
    @Override public Optional<ShippingOrder> findByTrackingCode(String trackingCode) { return Optional.ofNullable(orders.get(trackingCode)); }
    @Override public List<ShippingOrder> findAll() { return new ArrayList<>(orders.values()); }
}
