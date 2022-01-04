package one.primedoc.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
public class PhoneResponse {
    private String number;
    private Byte report;
    private String sendTime;
    private String rcvTime;
}
