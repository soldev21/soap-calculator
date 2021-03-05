package com.rest.soap.wrapper.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class LogBucket {
    private StringBuilder builder;

    public LogBucket() {
        builder = new StringBuilder();
    }

    public LogBucket append(String log) {
        builder.append(log).append("\n");
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
