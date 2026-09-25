package edu.ucc.patterns.adapter;

import edu.ucc.patterns.model.ShipmentQuote;
import edu.ucc.patterns.model.ShipmentRequest;

/** Target interface used by the application, independent of supplier APIs. */
public interface ShippingProvider {
    String name();
    ShipmentQuote quote(ShipmentRequest request);
}
