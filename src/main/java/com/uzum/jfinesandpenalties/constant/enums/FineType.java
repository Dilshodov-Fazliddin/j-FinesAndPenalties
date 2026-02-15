package com.uzum.jfinesandpenalties.constant.enums;

import lombok.Getter;

@Getter
public enum FineType {
    SPEEDING( "Превышение скорости"),
    RED_LIGHT( "Проезд на красный свет"),
    PARKING_VIOLATION( "Нарушение правил парковки"),
    DRUNK_DRIVING( "Управление в состоянии алкогольного опьянения"),
    NO_SEATBELT( "Отсутствие ремня безопасности"),
    ILLEGAL_OVERTAKE( "Незаконный обгон"),
    MOBILE_WHILE_DRIVING( "Использование мобильного телефона за рулем"),
    UNLICENSED_DRIVING( "Управление без водительского удостоверения");

    private final String description;

    FineType(String description) {
        this.description = description;
    }

}