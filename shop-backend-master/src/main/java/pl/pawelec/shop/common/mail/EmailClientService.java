package pl.pawelec.shop.common.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailClientService {
//    private final List<EmailSender> list; // spring wstrzyknie do listy wszystkie implementacje EmailSender
    private final Map<String,EmailSender>  senderMap;
    @Value("${app.email.sender}") // pobiera wartosc z .properties
    private String isFakeProp;

    public EmailClientService(Map<String, EmailSender> senderMap) {
        this.senderMap = senderMap;
    }

    public EmailSender getInstance(){
        if(isFakeProp.equals("fakeEmailService")){
            System.out.println("@#@#@# fakeEmailService");
            return senderMap.get("fakeEmailService");
        }
        System.out.println("@#@#@# emailSimpleService");

        return senderMap.get("emailSimpleService");
    }

    public EmailSender getInstance(String beanName){
        return senderMap.get("emailSimpleService");
    }
}
