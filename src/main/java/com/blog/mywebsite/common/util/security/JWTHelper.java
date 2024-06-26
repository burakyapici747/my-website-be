package com.blog.mywebsite.common.util.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.blog.mywebsite.constant.SecurityConstant;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public final class JWTHelper {
    private JWTHelper(){
        throw new AssertionError();
    }
    @Value("${security.jwt.secret}")
    private static String secretKey;

    public static String generateJwtToken(final String email, List<String> roles){
        final Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withSubject(email)
                .withClaim("roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() +
                        SecurityConstant.CURRENT_EXPIRED_DAY * 24 * 60 * 60 * 1000))
                .sign(algorithm);
    }

    public static DecodedJWT decodeJwtToken(final String jwtToken) throws JWTVerificationException {
        final Algorithm algorithm = Algorithm.HMAC256(secretKey);
        final JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(jwtToken);
    }
}