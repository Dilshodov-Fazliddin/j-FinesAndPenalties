package com.uzum.jfinesandpenalties.utils;

import com.uzum.jfinesandpenalties.dto.request.FineRequest;

public class MessageBuilder {

    public static String SEND_FINE_MESSAGE(FineRequest fineRequest, Double amount,String officerName){
        return "Здравствуйте, " + fineRequest.name() + "!\n\n" +
                "Вам назначен штраф.\n" +
                "Тип штрафа: " + fineRequest.fineType().getDescription() + "\n" +
                "Сумма штрафа: " + amount + " сум\n" +
                "Описание: " + fineRequest.description() + "\n" +
                "Статья: " + fineRequest.articleId() + "\n\n" +
                "Штраф выписан сотрудником: " + officerName + ".\n\n" +
                "Пожалуйста, погасите штраф в ближайшее время.";

    }
}
