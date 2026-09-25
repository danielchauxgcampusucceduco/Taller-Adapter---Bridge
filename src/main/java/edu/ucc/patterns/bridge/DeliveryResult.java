package edu.ucc.patterns.bridge;

import java.time.LocalDateTime;

public record DeliveryResult(String channel, String detail, LocalDateTime sentAt) { }
