package com.rest.soap.wrapper.calcimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.soap.wrapper.calc.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapMessage;

import static com.rest.soap.wrapper.util.Logger.info;

public class CalculatorService extends WebServiceGatewaySupport implements CalculatorServiceInterface {

    public int add(int a, int b) {
        Add add = new Add();
        add.setIntA(a);
        add.setIntB(b);
        info(add);
        AddResponse response = (AddResponse) getWebServiceTemplate()
                .marshalSendAndReceive(add,webServiceMessage -> {
                    ((SoapMessage)webServiceMessage).setSoapAction(
                            "http://tempuri.org/Add" );
                } );
        info(add);
        return response.getAddResult();
    }

    public int subtract(int a, int b) {
        Subtract add = new Subtract();
        add.setIntA(a);
        add.setIntB(b);

        SubtractResponse response = (SubtractResponse) getWebServiceTemplate()
                .marshalSendAndReceive(add,webServiceMessage -> {
                    ((SoapMessage)webServiceMessage).setSoapAction(
                            "http://tempuri.org/Subtract" );
                } );

        return response.getSubtractResult();
    }

    public int multiply(int a, int b) {
        Multiply add = new Multiply();
        add.setIntA(a);
        add.setIntB(b);

        MultiplyResponse response = (MultiplyResponse) getWebServiceTemplate()
                .marshalSendAndReceive(add,webServiceMessage -> {
                    ((SoapMessage)webServiceMessage).setSoapAction(
                            "http://tempuri.org/Multiply" );
                } );

        return response.getMultiplyResult();
    }

    public int divide(int a, int b) {
        Divide add = new Divide();
        add.setIntA(a);
        add.setIntB(b);

        DivideResponse response = (DivideResponse) getWebServiceTemplate()
                .marshalSendAndReceive(add,webServiceMessage -> {
                    ((SoapMessage)webServiceMessage).setSoapAction(
                            "http://tempuri.org/Divide" );
                } );

        return response.getDivideResult();
    }
}
