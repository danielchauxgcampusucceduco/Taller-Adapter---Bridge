package edu.ucc.patterns.bridge;

import java.time.LocalDateTime;

public final class SmsChannel implements NotificationChannel {
    @Override public String channelName() { return "SMS"; }
    @Override public DeliveryResult deliver(String recipient, String subject, String message) {
        return new DeliveryResult(channelName(), "SMS preparado para " + recipient, LocalDateTime.now());
    }
}
