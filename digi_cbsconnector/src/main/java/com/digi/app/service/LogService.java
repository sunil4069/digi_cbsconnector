package com.digi.app.service;

public interface LogService {
    void saveUserActionLog(String username, String request, String response, String actionType);
}
