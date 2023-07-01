package com.digi.app.util;

import java.security.Principal;
import java.util.List;

public interface UtilitiesService {
    List<String> currentUserRoles(Principal principal);

    String currentUsername(Principal principal);

    String getDigiUUID();

    String getCurrentDateInDigiDateFormat();
}
