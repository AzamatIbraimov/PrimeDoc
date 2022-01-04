package one.primedoc.backend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.primedoc.backend.model.PhoneRequest;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "message")
public class SmsMessageRequest {
    private String login;
    private String pwd;
    private String id;
    private String sender;
    private String text;
    private String time;
    private List<PhoneRequest> phones;
    private Byte test;
}

