package com.digi.app.user.enums;

import lombok.Getter;

@Getter
public enum YesNoEnum {
    YES("YES"),
    NO("NO");
    String value;

    YesNoEnum(String value) {
        this.value = value;
    }
}
