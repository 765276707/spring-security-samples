package com.example.properties;

import com.example.dto.JwtUser;
import com.example.utils.JwtUtils;
import com.example.utils.RsaUtils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtProperties {

    public static PublicKey publicKey;

    public static PrivateKey privateKey;

    static {
        KeyPair keyPair = RsaUtils.getKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
//        JwtUser user = new JwtUser("xzb", "123456", null);
//        String token = JwtUtils.createToken(user, privateKey);
//        System.out.println("默认的token: =>  " + token);
    }

}
