package com.blog.mywebsite.security;

import com.blog.mywebsite.model.CustomUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

public class EmailAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private Object credentials;

   public EmailAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
   }

   public EmailAuthenticationToken(
           Object principal,
           Object credentials,
           Collection<? extends GrantedAuthority> authorities
   ) {
       super(authorities);
       this.principal = principal;
       this.credentials = credentials;
       super.setAuthenticated(true);
   }

    public static EmailAuthenticationToken authenticated(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities
    ){
       return new EmailAuthenticationToken(principal, credentials, authorities);
    }

    public static EmailAuthenticationToken unauthenticated(Object principal, Object credentials){
        return new EmailAuthenticationToken(principal, credentials);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public Object getEmail(){
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated){
        Assert.isTrue(
                !isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead"
        );
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials(){
        super.eraseCredentials();
        this.credentials = null;
    }
}