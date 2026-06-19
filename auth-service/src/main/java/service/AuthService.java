package service;

import DTO.ChangePassRequest;
import DTO.SignInRequest;
import DTO.SignUpRequest;
import entities.User;

public interface AuthService {
    String signIn(SignInRequest signInRequest);

    void changePassword(String username, ChangePassRequest changePassRequest);

    User signUp(SignUpRequest signUpRequest);
}
