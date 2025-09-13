package com.github.psbds.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.inject.Inject;

public abstract class BaseAuthenticatedResource {

    JsonWebToken jwt;

    @Inject
    protected BaseAuthenticatedResource(JsonWebToken jwt) {
        this.jwt = jwt;
    }

    public String getUserId() {
        String userId = jwt.getSubject();
        return userId;
    }
}
