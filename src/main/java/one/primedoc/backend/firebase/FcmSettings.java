package one.primedoc.backend.firebase;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fcm")
public class FcmSettings {
    private String serviceAccountFile;

    public String getServiceAccountFile() {
        return this.serviceAccountFile;
    }

    public void setServiceAccountFile(String serviceAccountFile) {
        this.serviceAccountFile = serviceAccountFile;
    }
}
