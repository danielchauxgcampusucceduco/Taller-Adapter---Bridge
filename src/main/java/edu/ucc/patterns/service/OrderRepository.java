package edu.ucc.patterns.service;

import edu.ucc.patterns.model.ShippingOrder;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(ShippingOrder order);
    Optional<ShippingOrder> findByTrackingCode(String trackingCode);
    List<ShippingOrder> findAll();
}
