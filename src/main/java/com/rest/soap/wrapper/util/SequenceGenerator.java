package com.rest.soap.wrapper.util;

public class SequenceGenerator {
    private static final  Object lock = new Object();
    private static volatile int counter = 1;

    public static int getNextCount(){
        synchronized (lock){
            return counter++;
        }
    }
}
