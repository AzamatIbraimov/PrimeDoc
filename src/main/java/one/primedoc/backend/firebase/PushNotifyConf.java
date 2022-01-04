package one.primedoc.backend.firebase;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushNotifyConf {
    private String title;
    private String body;
    private String icon;
    private Boolean scheduled;
    private String date;
    private String click_action;
    private String ttlInSeconds;
}