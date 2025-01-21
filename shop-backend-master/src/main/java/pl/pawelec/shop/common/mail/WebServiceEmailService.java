package pl.pawelec.shop.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class WebServiceEmailService implements EmailSender{

    @Override
    public void send(String to, String subject, String msg){
        throw  new RuntimeException("Not implemented yet");
    }
}
