package pl.pawelec.shop.security.model.dto;

import lombok.Getter;

@Getter
public class ChangePassword {
    private String password;
    private String repeatPassword;
    private String hash;
}
