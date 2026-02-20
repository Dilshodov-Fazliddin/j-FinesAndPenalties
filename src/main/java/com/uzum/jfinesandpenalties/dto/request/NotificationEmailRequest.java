package com.uzum.jfinesandpenalties.dto.request;

public record NotificationEmailRequest(
        Receiver receiver,
        String type,
        String text
) {
}
