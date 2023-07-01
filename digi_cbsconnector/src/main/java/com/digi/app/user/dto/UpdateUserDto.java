package com.digi.app.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateUserDto {
    private List<String> roles;
    private double txnlimit;
}
