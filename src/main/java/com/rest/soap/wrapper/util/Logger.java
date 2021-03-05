package com.rest.soap.wrapper.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.soap.wrapper.calcimpl.CalculatorService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger {
    private static final Log logger = LogFactory.getLog(CalculatorService.class);


    public static void info(Object o) {
        try {
            logger.info(Utils.mapper.writeValueAsString(o));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
