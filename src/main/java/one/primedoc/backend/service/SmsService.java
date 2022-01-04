package one.primedoc.backend.service;

import one.primedoc.backend.enums.MessageType;
import one.primedoc.backend.model.response.SmsMessageResponse;

import javax.xml.bind.JAXBException;

public interface SmsService {
    public SmsMessageResponse sendSMS(String username, String code, MessageType type);
    public String createMessage(String username, String code, MessageType type) throws JAXBException;
    public String getMessage(MessageType type);
    public SmsMessageResponse getResponse(String xml) throws JAXBException;
}
