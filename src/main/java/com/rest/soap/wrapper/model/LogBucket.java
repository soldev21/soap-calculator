package com.rest.soap.wrapper.model;

import com.rest.soap.wrapper.util.SequenceGenerator;

public class LogBucket {

    private int id;
    private StringBuilder builder;

    public static LogBucket newInstance(){
        return new LogBucket(SequenceGenerator.getNextCount());
    }

    private LogBucket(int id) {
        this.id=id;
        builder = new StringBuilder();
    }

    public LogBucket append(String log) {
        builder.append("CALL "+id+". ").append(log).append("\n");
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
