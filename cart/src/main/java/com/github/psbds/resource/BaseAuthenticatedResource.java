package com.github.psbds.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.inject.Inject;

public class BaseAuthenticatedResource {

    @Inject
    JsonWebToken jwt;

    public String getUserId() {
        String userId = jwt.getSubject();
        return userId;
    }
}
