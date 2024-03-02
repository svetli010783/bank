package com.test.task.bank.service;

import com.test.task.bank.domain.User;
import java.util.HashMap;
import java.util.Map;

public final class FillingClaims {
    public static Map<String,Object> fillingClaimsForUser(User user){
        Map<String,Object> claims = new HashMap<>();
        claims.put("subject",user.getLogin());
        claims.put("id",user.getId());
        return claims;
    }

}
