package com.uzum.jfinesandpenalties.utils;

import com.uzum.jfinesandpenalties.dto.request.FineRequest;
import com.uzum.jfinesandpenalties.entity.FineEntity;


public class MessageBuilder {

    public static String SEND_FINE_MESSAGE(FineEntity fine) {
        return "Здравствуйте, " + fine.getOffenderName() + "!\n\n" +
                "Вам назначен штраф: " + fine.getPenaltyAmount() + " руб.\n" +
                "Тип штрафа: " + fine.getFineType() + "\n" +
                "Описание: " + fine.getDescription() + "\n" +
                "Статья: " + fine.getArticleId() + "\n\n" +
                "Штраф выписан сотрудником: " + fine.getOfficer().getFirstName() + ".\n\n" +
                "Пожалуйста, погасите штраф в ближайшее время.";
    }
}
