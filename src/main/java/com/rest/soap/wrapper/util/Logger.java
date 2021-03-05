package com.rest.soap.wrapper.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger {
    private static final Log logger = LogFactory.getLog(Logger.class);


    public static void info(Object o) {
        try {
            logger.info(Utils.mapper.writeValueAsString(o));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void info(String o){
        logger.info(o);
    }
}
