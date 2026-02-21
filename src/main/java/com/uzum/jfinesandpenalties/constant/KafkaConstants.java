package com.uzum.jfinesandpenalties.constant;

public class KafkaConstants {
    public static final String FINE_CREATED = "fines.created";
    public static final String FINE_PAID = "fines.paid";
    public static final String GROUP_ID = "notification.service";
    public static final String TRUSTED_PACKAGES = "com.uzum.jfinesandpenalties.*";
    public static final String VALUE_TYPE = "com.uzum.jfinesandpenalties.dto.event.FineCreatedEvent";

}
