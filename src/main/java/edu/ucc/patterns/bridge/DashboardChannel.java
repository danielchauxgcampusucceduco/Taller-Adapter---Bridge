package edu.ucc.patterns.bridge;

import java.time.LocalDateTime;

public final class DashboardChannel implements NotificationChannel {
    @Override public String channelName() { return "Panel interno"; }
    @Override public DeliveryResult deliver(String recipient, String subject, String message) {
        return new DeliveryResult(channelName(), "Aviso publicado en el panel", LocalDateTime.now());
    }
}
