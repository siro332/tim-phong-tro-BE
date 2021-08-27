package com.vxl.tim_phong_tro.utils;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;

@UtilityClass
public class AuthUtils {
    private final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

    public static Algorithm getAlgorithm(){
        return algorithm;
    }
}
