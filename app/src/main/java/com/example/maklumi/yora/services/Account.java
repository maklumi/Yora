package com.example.maklumi.yora.services;

import android.net.Uri;

import com.example.maklumi.yora.infrastructure.ServiceResponse;
import com.example.maklumi.yora.infrastructure.User;

/**
 * Created by HomePC on 19/2/2016.
 */
public final class Account {
    private Account(){
    }

    public static class UserResponse extends ServiceResponse {
        public int Id;
        public String AvatarUrl;
        public String DisplayName;
        public String UserName;
        public String Email;
        public String AuthToken;
        public boolean HasPassword;
    }

    public static class LoginWithUsernameRequest {
        public String Username;

        public String Password;

        public LoginWithUsernameRequest(String username, String password) {
            Username = username;
            Password = password;
        }
    }

    public static class LoginWithUsernameResponse extends ServiceResponse {

    }

    public static class LoginWithLocalTokenRequest {
        public String AuthToken;

        public LoginWithLocalTokenRequest(String authToken) {
            AuthToken = authToken;
        }
    }

    public static class LoginWIthLocalTokenResponse extends UserResponse {

    }

    public static class LoginWithExternalTokenRequest {
        public String Provider;
        public String Token;
        public String ClientId;

        public LoginWithExternalTokenRequest(String provider, String token) {
            Provider = provider;
            Token = token;
            ClientId = "android";
        }
    }

    public static class LoginWithExternalTokenResponse extends UserResponse {

    }

    public static class RegisterRequest {
        public String Username;
        public String Password;
        public String Email;
        public String ClientId;

        public RegisterRequest(String username, String password, String email) {
            Username = username;
            Password = password;
            Email = email;
            ClientId = "android";
        }
    }

    public static class RegisterResponse extends UserResponse {

    }

    public static class RegisterWithExternalTokenRequest {
        public String Username;
        public String Email;
        public String Provider;
        public String Token;
        public String ClientId;

        public RegisterWithExternalTokenRequest(String username, String email, String provider, String token) {
            Username = username;
            Email = email;
            Provider = provider;
            Token = token;
            ClientId = "android";
        }
    }

    public static class RegisterWithExternalTokenResponse extends UserResponse {

    }



    public static class ChangeAvatarRequest {
        public Uri newAvatarUri;

        public ChangeAvatarRequest(Uri newAvatarUri){
            this.newAvatarUri = newAvatarUri;
        }
    }

    public static class ChangeAvatarResponse extends ServiceResponse {
        public String AvatarUrl;
    }

    public static class UpdateProfileRequest {
        public String displayName;
        public String email;

        public UpdateProfileRequest(String displayName, String email) {
            this.displayName = displayName;
            this.email = email;
        }
    }

    public static class UpdateProfileResponse extends ServiceResponse {
        public String DisplayName;
        public String email;
    }

    public static class ChangePasswordRequest {
        public String currentPassword;
        public String newPassword;
        public String confirmNewPassword;

        public ChangePasswordRequest(String currentPassword, String newPassword, String confirmNewPassword) {
            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
            this.confirmNewPassword = confirmNewPassword;
        }
    }

    public static class ChangePasswordResponse extends ServiceResponse {
    }

    public static class UserDetailsUpdatedEvent {
        public User User;

        public UserDetailsUpdatedEvent(User user) {
            User = user;
        }
    }

    public static class UpdateGcmRegistrationRequest {
        public String RegistrationId;

        public UpdateGcmRegistrationRequest(String registrationId){
            RegistrationId = registrationId;
        }

    }

    public static class UpdateGcmRegistrationResponse extends ServiceResponse {

    }

}
