package com.back_puyoReboot.Authentication;

import com.back_puyoReboot.User.User;
import com.back_puyoReboot.User.UserLoginResponseDTO;
import com.back_puyoReboot.User.UserService;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@Valid @RequestBody UserRegistrationRequestDTO userRegistrationDTO) {
        userService.checkUserExists(userRegistrationDTO.email());
        boolean isRegisteredSuccess = userService.registerUser(userRegistrationDTO);

        return ResponseEntity.status(isRegisteredSuccess ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).body(isRegisteredSuccess);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticate(@Valid @RequestBody UserLoginRequestDTO userLoginDTO) {
        String token = authService.authenticate(userLoginDTO.email(), userLoginDTO.password());
        User user = userService.findByEmail(userLoginDTO.email());

        Map<String, Object> response = new HashMap<>();

        response.put("token", token);
        response.put("user", UserLoginResponseDTO.fromEntityToDTO(user));


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
