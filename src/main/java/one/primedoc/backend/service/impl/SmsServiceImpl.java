package one.primedoc.backend.service.impl;

import lombok.Data;
import one.primedoc.backend.enums.MessageType;
import one.primedoc.backend.model.PhoneRequest;
import one.primedoc.backend.model.request.SmsMessageRequest;
import one.primedoc.backend.model.response.SmsMessageResponse;
import one.primedoc.backend.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class SmsServiceImpl implements SmsService {
    private final HttpHeaders headers;
    private final RestTemplate restTemplate;
    @Value("${sms.service.url}")
    private String url;
    @Value("${sms.service.login}")
    private String login;
    @Value("${sms.service.sender}")
    private String sender;
    @Value("${sms.service.password}")
    private String password;
    @Value("${sms.service.verification.message}")
    private String verificationMessage;
    @Value("${sms.service.recovery.message}")
    private String recoveryMessage;
    @Value("${sms.service.reset.message}")
    private String resetMessage;


    public SmsServiceImpl() {
        headers = new HttpHeaders();
        restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        headers.setContentType(MediaType.APPLICATION_XML);
    }

    @Override
    public SmsMessageResponse sendSMS(String username, String code, MessageType type) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(createMessage(username, code, type), headers), String.class);
            return getResponse(Objects.requireNonNull(response.getBody()));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public String createMessage(String username, String code, MessageType type) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SmsMessageRequest.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(
                SmsMessageRequest.builder()
                        .id(code)
                        .login(login)
                        .pwd(password)
                        .sender(sender)
                        .text(String.format(getMessage(type), code))
                        .phones(Collections.singletonList(new PhoneRequest(username)))
                        .build(), sw);
        return sw.toString();
    }
    @Override
    public String getMessage(MessageType type){
        return type.equals(MessageType.VERIFICATION) ? verificationMessage : type.equals(MessageType.RECOVERY) ? recoveryMessage : type.equals(MessageType.RESET) ? resetMessage : "";
    }

    @Override
    public SmsMessageResponse getResponse(String xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SmsMessageResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//        return (SmsMessageResponse) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
        return new SmsMessageResponse();
    }
}
