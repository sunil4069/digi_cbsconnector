package com.digi.app.service;

import com.digi.app.constants.MessageConstants;
import com.digi.app.entity.UserActionLog;
import com.digi.app.repo.UserActionLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    private UserActionLogRepository userActionLogRepository;

    @Autowired
    public void setUserActionLogRepository(UserActionLogRepository userActionLogRepository) {
        this.userActionLogRepository = userActionLogRepository;
    }

    @Override
    public void saveUserActionLog(String username, String request, String response, String actionType) {
        UserActionLog userActionLog = new UserActionLog();
        userActionLog.setActionType(actionType);
        userActionLog.setRequest(request);
        System.out.println(request.length());
        if (StringUtils.isNotBlank(response)) {
            userActionLog.setResponse(response);
        } else {
            userActionLog.setResponse(MessageConstants.CONNECTION_FAILED);
        }
        userActionLog.setUsername(username);
        userActionLogRepository.save(userActionLog);
    }

}
