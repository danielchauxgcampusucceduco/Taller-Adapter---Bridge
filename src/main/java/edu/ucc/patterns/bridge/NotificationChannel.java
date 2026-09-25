package edu.ucc.patterns.bridge;

/** Implementor side of Bridge: each channel knows how to deliver a message. */
public interface NotificationChannel {
    String channelName();
    DeliveryResult deliver(String recipient, String subject, String message);
}
