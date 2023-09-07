package com.junsim.whereami.utility;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtility {

    public static String currentMember() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
