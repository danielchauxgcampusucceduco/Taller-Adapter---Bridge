package edu.ucc.patterns;

import edu.ucc.patterns.adapter.InternationalCarrierAdapter;
import edu.ucc.patterns.adapter.InternationalCarrierGateway;
import edu.ucc.patterns.adapter.LegacyCourierAdapter;
import edu.ucc.patterns.adapter.LegacyCourierSystem;
import edu.ucc.patterns.bridge.DashboardChannel;
import edu.ucc.patterns.bridge.EmailChannel;
import edu.ucc.patterns.model.ServiceLevel;
import edu.ucc.patterns.model.ShipmentRequest;
import edu.ucc.patterns.model.ShipmentStatus;
import edu.ucc.patterns.service.InMemoryOrderRepository;
import edu.ucc.patterns.service.LogisticsService;
import java.math.BigDecimal;
import java.util.List;

/** Lightweight executable verification without external test dependencies. */
public final class PatternVerification {
    private PatternVerification() { }
    public static void main(String[] args) {
        ShipmentRequest request = new ShipmentRequest("Ana Torres", "Medellín", new BigDecimal("2.5"), ServiceLevel.EXPRESS);
        var national = new LegacyCourierAdapter(new LegacyCourierSystem());
        var international = new InternationalCarrierAdapter(new InternationalCarrierGateway());
        require(national.quote(request).price().signum() > 0, "Legacy adapter must produce a domain quote");
        require(international.quote(request).price().signum() > 0, "Gateway adapter must produce a domain quote");

        LogisticsService service = new LogisticsService(new InMemoryOrderRepository());
        var order = service.createOrder(national, request);
        var results = service.changeStatus(order, ShipmentStatus.IN_TRANSIT, List.of(new EmailChannel(), new DashboardChannel()));
        require(results.size() == 2, "Bridge must notify through each selected channel");
        require(order.status() == ShipmentStatus.IN_TRANSIT, "Order status must be updated");
        System.out.println("Pattern verification completed successfully.");
    }
    private static void require(boolean condition, String message) { if (!condition) throw new IllegalStateException(message); }
}
