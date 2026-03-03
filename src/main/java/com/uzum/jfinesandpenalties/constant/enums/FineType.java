package com.uzum.jfinesandpenalties.constant.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public enum FineType {
    SPEEDING( "Превышение скорости"),
    RED_LIGHT( "Проезд на красный свет"),
    PARKING_VIOLATION( "Нарушение правил парковки"),
    DRUNK_DRIVING( "Управление в состоянии алкогольного опьянения"),
    NO_SEATBELT( "Отсутствие ремня безопасности"),
    ILLEGAL_OVERTAKE( "Незаконный обгон"),
    MOBILE_WHILE_DRIVING( "Использование мобильного телефона за рулем"),
    UNLICENSED_DRIVING( "Управление без водительского удостоверения");

    String description;

    FineType(String description) {
        this.description = description;
    }

}