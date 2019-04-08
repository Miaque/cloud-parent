package com.demo.licensingservice.utils;

import org.springframework.util.Assert;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static final UserContext getContext() {
        UserContext context = userContext.get();

        if (null == context) {
            context = createEmptyContext();
            userContext.set(context);
        }

        return userContext.get();
    }

    public static final void setContext(UserContext context) {
        Assert.notNull(context, "only non-null UserContext instances are permitted");
        userContext.set(context);
    }

    private static final UserContext createEmptyContext() {
        return new UserContext();
    }
}
