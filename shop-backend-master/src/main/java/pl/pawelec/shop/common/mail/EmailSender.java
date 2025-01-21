package pl.pawelec.shop.common.mail;

public interface EmailSender {
    void send(String to, String subject, String msg);
}
