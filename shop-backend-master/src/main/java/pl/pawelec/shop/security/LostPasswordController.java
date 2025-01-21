package pl.pawelec.shop.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pawelec.shop.security.model.User;
import pl.pawelec.shop.security.model.dto.ChangePassword;
import pl.pawelec.shop.security.model.dto.EmailObject;
import pl.pawelec.shop.security.repository.UserRepository;
import pl.pawelec.shop.security.service.LostPasswordService;

import java.time.LocalDateTime;

@RestController
public class LostPasswordController {
    private final LostPasswordService lostPasswordService;
    public LostPasswordController(LostPasswordService lostPasswordService) {
        this.lostPasswordService = lostPasswordService;
    }


    @PostMapping("/lostPassword")
    public void lostPassword(@RequestBody EmailObject emailObject) {
        lostPasswordService.sendLostPasswordLink(emailObject);
    }

    @PostMapping("/changePassword")
    public void changePassword(@RequestBody ChangePassword changePassword) {
        lostPasswordService.changePassword(changePassword);
    }
}
