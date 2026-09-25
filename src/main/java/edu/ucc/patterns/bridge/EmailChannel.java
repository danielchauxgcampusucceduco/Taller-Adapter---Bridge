package edu.ucc.patterns.bridge;

import java.time.LocalDateTime;

public final class EmailChannel implements NotificationChannel {
    @Override public String channelName() { return "Correo electrónico"; }
    @Override public DeliveryResult deliver(String recipient, String subject, String message) {
        return new DeliveryResult(channelName(), "Correo preparado para " + recipient, LocalDateTime.now());
    }
}
