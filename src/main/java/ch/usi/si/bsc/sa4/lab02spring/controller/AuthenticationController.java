package ch.usi.si.bsc.sa4.lab02spring.controller;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.ChangePasswordDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.UserDTO;
import ch.usi.si.bsc.sa4.lab02spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Request router for /auth.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * POST /auth/changePassword
     */
    @PostMapping("/changePassword")
    public ResponseEntity<UserDTO> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        var optionalUser = userService.changePassword(changePasswordDTO.getUserId(), changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get().toUserDTO());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}