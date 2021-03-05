package com.rest.soap.wrapper.calcimpl;

import com.rest.soap.wrapper.calc.*;
import com.rest.soap.wrapper.model.LogBucket;
import lombok.Data;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.soap.SoapEnvelope;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static com.rest.soap.wrapper.util.Logger.info;

@Data
public class CalculatorService2 implements CalculatorServiceInterface {

    Jaxb2Marshaller marshaller;
    Jaxb2Marshaller unMarshaller;
    String defaultUri;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LogBucket logBucket;


    @SneakyThrows
    @Override
    public int add(int a, int b) {
        Add add = new Add();
        add.setIntA(a);
        add.setIntB(b);
        String xml = buildRequest(add);
        logBucket.append(xml);
//        info(xml);
        String res = sendRequestAndReceive(xml);
//        info(res);
        logBucket.append(res);
        AddResponse response = new AddResponse();
        response.setAddResult(extractResult(res, "Add"));
        System.out.println(logBucket.toString());
        return response.getAddResult();
    }

    @SneakyThrows
    @Override
    public int subtract(int a, int b) {
        Subtract subtract = new Subtract();
        subtract.setIntA(a);
        subtract.setIntB(b);
        String xml = buildRequest(subtract);
        info(xml);
        String res = sendRequestAndReceive(xml);
        info(res);
        SubtractResponse response = new SubtractResponse();
        response.setSubtractResult(extractResult(res, "Subtract"));
        return response.getSubtractResult();
    }

    @SneakyThrows
    @Override
    public int multiply(int a, int b) {
        Multiply multiply = new Multiply();
        multiply.setIntA(a);
        multiply.setIntB(b);
        String xml = buildRequest(multiply);
        info(xml);
        String res = sendRequestAndReceive(xml);
        info(res);
        MultiplyResponse response = new MultiplyResponse();
        response.setMultiplyResult(extractResult(res, "Multiply"));
        return response.getMultiplyResult();
    }

    @SneakyThrows
    @Override
    public int divide(int a, int b) {
        Divide divide = new Divide();
        divide.setIntA(a);
        divide.setIntB(b);
        String xml = buildRequest(divide);
        info(xml);
        String res = sendRequestAndReceive(xml);
        info(res);
        DivideResponse response = new DivideResponse();
        response.setDivideResult(extractResult(res, "Divide"));
        return response.getDivideResult();
    }

    public String buildRequest(Object input) throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        JAXBContext jaxbContext = JAXBContext.newInstance(input.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(input, document);

        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        changeNamespacePrefix(soapMessage);
        soapMessage.getSOAPBody().addDocument(document);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        soapMessage.writeTo(outputStream);
        String output = new String(outputStream.toByteArray(), "UTF-8");
        output = output.replaceAll("http://schemas.xmlsoap.org/soap/envelope/", "http://www.w3.org/2003/05/soap-envelope");
        return output;
    }

    private void changeNamespacePrefix(SOAPMessage soapMessage) throws SOAPException {
        soapMessage.getSOAPPart().getEnvelope().setPrefix("soap");
        soapMessage.getSOAPHeader().setPrefix("soap");
        soapMessage.getSOAPBody().setPrefix("soap");
        soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("SOAP-ENV");
        soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("soap");
    }

    private String sendRequestAndReceive(String xml) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/soap+xml");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        HttpEntity<String> entity = new HttpEntity<>(xml, headers);
        ResponseEntity<String> xmlSource = restTemplate.postForEntity("http://www.dneonline.com/calculator.asmx?WSDL", entity, String.class);
        return xmlSource.getBody();
    }

    private int extractResult(String responseXml, String methodName) {
        responseXml = responseXml.replaceAll("soap:", "");
        JSONObject jsonObj = XML.toJSONObject(responseXml);
        return jsonObj.getJSONObject("Envelope").getJSONObject("Body").getJSONObject(methodName + "Response").getInt(methodName + "Result");
    }
}
