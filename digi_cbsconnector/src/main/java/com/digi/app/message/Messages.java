package com.digi.app.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Messages {
    private String statusCode;
    private boolean status;
    private String message;
    private Object data;
    private Object errors;
}
