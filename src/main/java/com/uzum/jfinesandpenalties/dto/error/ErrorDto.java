package com.uzum.jfinesandpenalties.dto.error;

import com.uzum.jfinesandpenalties.constant.enums.error.ErrorType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorDto {
    int code;
    String message;
    ErrorType type;
    List<String> validationErrors;
}
