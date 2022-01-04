package one.primedoc.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.primedoc.backend.model.PhoneResponse;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "response")
public class SmsMessageResponse {
    private String status;
    private PhoneResponse phone;
}
