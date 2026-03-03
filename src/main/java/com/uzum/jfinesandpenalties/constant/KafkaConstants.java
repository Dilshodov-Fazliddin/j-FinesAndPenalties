package com.uzum.jfinesandpenalties.constant;

public class KafkaConstants {
    public static final String FINE_CREATED = "fines.created";
    public static final String DECISION_CREATED = "decision.created";
    public static final String FINE_PAID = "fines.paid";

    public static final String TRUSTED_PACKAGES =  "*";
    public static final String DEFAULT_VALUE =  "com.uzum.jfinesandpenalties.dto.event.DecisionCreatedEvent";

    public static final String NOTIFICATION_GROUP_ID = "notification.service";
    public static final String COURT_GROUP_ID = "court.service";
    public static final String COURT_VIOLATION_GROUP_ID = "court.service.violation";

}
