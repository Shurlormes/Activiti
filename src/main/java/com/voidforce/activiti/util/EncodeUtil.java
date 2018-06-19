package com.voidforce.activiti.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class EncodeUtil {

    public static String encodeByBCrypt(String str) {
        return BCrypt.hashpw(str, BCrypt.gensalt());
    }

}
