package pl.pawelec.shop.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSimpleService implements EmailSender{
    private final JavaMailSender mailSender;

    public EmailSimpleService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async // metoda zostanie z tego bean odpalona w osobnym watku, dziaa dzieki aspektom, dziala na publicznej metodzie(domyslnie)
    @Override
    public void send(String to, String subject, String msg){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Shop <sklep@ezpawka.pl>");
        message.setReplyTo("Shop <sklep@ezpawka.pl>");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
        log.info("Email wysłany");

    }
}
