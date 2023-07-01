package com.digi.app.user.enums;

public enum RolesEnum {
    SUPERADMIN(1, "ROLE_SUPERADMIN"),
    ADMIN(2, "ROLE_ADMIN"),
    INPUTTER(3, "ROLE_INPUTTER"),
    AUTHORIZER(4, "ROLE_AUTHORIZER");

    int id;
    String name;

    RolesEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
