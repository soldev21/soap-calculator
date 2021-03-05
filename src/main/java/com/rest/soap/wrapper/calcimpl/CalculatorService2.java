package com.rest.soap.wrapper.calcimpl;

import com.rest.soap.wrapper.calc.*;
import com.rest.soap.wrapper.entitiy.ChildLogEnt;
import com.rest.soap.wrapper.entitiy.ParentLogEnt;
import com.rest.soap.wrapper.model.LogBucket;
import com.rest.soap.wrapper.repository.ChildLogRepository;
import com.rest.soap.wrapper.repository.ParentLogRepository;
import lombok.Data;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.util.Date;

import static com.rest.soap.wrapper.util.Logger.info;
import static com.rest.soap.wrapper.util.Utils.writeAsString;

@Data
@Component("calc2")
public class CalculatorService2 implements CalculatorServiceInterface {

    private final String defaultUri = "http://www.dneonline.com/calculator.asmx?WSDL";

    @Autowired
    RestTemplate restTemplate;


    @Autowired
    ChildLogRepository childLogRepository;

    @Autowired
    ParentLogRepository parentLogRepository;


    @SneakyThrows
    @Override
    public int add(Add add) {

        ParentLogEnt parentLogEnt = createAndSaveParentLogEnt();
        int parentId = parentLogEnt.getId();
        LogBucket logBucket = LogBucket.newInstance();
        String reqLog = writeAsString(add);
        createAndSaveChild(reqLog,parentId);
        logBucket.append(reqLog);

        String xml = buildRequest(add);

        createAndSaveChild(xml,parentId);
        logBucket.append(xml);

        String res = sendRequestAndReceive(xml);

        createAndSaveChild(res,parentId);
        logBucket.append(res);

        AddResponse response = new AddResponse();
        response.setAddResult(extractResult(res, "Add"));

        info(logBucket.toString());

        return response.getAddResult();
    }

    @SneakyThrows
    @Override
    public int subtract(Subtract subtract) {
        ParentLogEnt parentLogEnt = createAndSaveParentLogEnt();
        int parentId = parentLogEnt.getId();
        LogBucket logBucket = LogBucket.newInstance();
        String reqLog = writeAsString(subtract);
        createAndSaveChild(reqLog,parentId);
        logBucket.append(reqLog);

        String xml = buildRequest(subtract);

        createAndSaveChild(xml,parentId);
        logBucket.append(xml);

        String res = sendRequestAndReceive(xml);

        createAndSaveChild(res,parentId);
        logBucket.append(res);

        SubtractResponse response = new SubtractResponse();
        response.setSubtractResult(extractResult(res, "Subtract"));

        info(logBucket.toString());

        return response.getSubtractResult();
    }

    @SneakyThrows
    @Override
    public int multiply(Multiply multiply) {
        ParentLogEnt parentLogEnt = createAndSaveParentLogEnt();
        int parentId = parentLogEnt.getId();
        LogBucket logBucket = LogBucket.newInstance();
        String reqLog = writeAsString(multiply);
        createAndSaveChild(reqLog,parentId);
        logBucket.append(reqLog);

        String xml = buildRequest(multiply);

        createAndSaveChild(xml,parentId);
        logBucket.append(xml);

        String res = sendRequestAndReceive(xml);

        createAndSaveChild(res,parentId);
        logBucket.append(res);

        MultiplyResponse response = new MultiplyResponse();
        response.setMultiplyResult(extractResult(res, "Multiply"));

        info(logBucket.toString());

        return response.getMultiplyResult();
    }

    @SneakyThrows
    @Override
    public int divide(Divide divide) {
        ParentLogEnt parentLogEnt = createAndSaveParentLogEnt();
        int parentId = parentLogEnt.getId();
        LogBucket logBucket = LogBucket.newInstance();
        String reqLog = writeAsString(divide);
        createAndSaveChild(reqLog,parentId);
        logBucket.append(reqLog);

        String xml = buildRequest(divide);

        createAndSaveChild(xml,parentId);
        logBucket.append(xml);

        String res = sendRequestAndReceive(xml);

        createAndSaveChild(res,parentId);
        logBucket.append(res);

        DivideResponse response = new DivideResponse();
        response.setDivideResult(extractResult(res, "Divide"));

        info(logBucket.toString());

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
        ResponseEntity<String> xmlSource = restTemplate.postForEntity(defaultUri, entity, String.class);
        return xmlSource.getBody();
    }

    private int extractResult(String responseXml, String methodName) {
        responseXml = responseXml.replaceAll("soap:", "");
        JSONObject jsonObj = XML.toJSONObject(responseXml);
        return jsonObj.getJSONObject("Envelope").getJSONObject("Body").getJSONObject(methodName + "Response").getInt(methodName + "Result");
    }

    private ParentLogEnt createAndSaveParentLogEnt(){
        ParentLogEnt parentLogEnt = new ParentLogEnt();
        parentLogEnt.setInsertDate(new Date());
        parentLogRepository.save(parentLogEnt);
        return parentLogEnt;
    }

    private void createAndSaveChild(String val, int parentId){
        ChildLogEnt childLogEnt = new ChildLogEnt();
        childLogEnt.setInsertDate(new Date());
        childLogEnt.setParentId(parentId);
        childLogEnt.setValue(val);
        childLogRepository.save(childLogEnt);
    }

}
