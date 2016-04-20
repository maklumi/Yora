package com.example.maklumi.yora.services;

import android.net.Uri;

import com.example.maklumi.yora.infrastructure.ServiceResponse;
import com.example.maklumi.yora.infrastructure.User;

public final class Account {
    private Account() {
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

    public static class LoginWithLocalTokenResponse extends UserResponse {
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
        public String UserName;
        public String Email;
        public String Password;
        public String ClientId;

        public RegisterRequest(String userName, String email, String password) {
            UserName = userName;
            Email = email;
            Password = password;
            ClientId = "android";
        }
    }

    public static class RegisterResponse extends UserResponse {
    }

    public static class RegisterWithExternalTokenRequest {
        public String UserName;
        public String Email;
        public String Provider;
        public String Token;
        public String ClientId;

        public RegisterWithExternalTokenRequest(String userName, String email, String provider, String token) {
            UserName = userName;
            Email = email;
            Provider = provider;
            Token = token;
            ClientId = "android";
        }
    }

    public static class RegisterWithExternalTokenResponse extends UserResponse {
    }

    public static class ChangeAvatarRequest {
        public Uri NewAvatarUri;

        public ChangeAvatarRequest(Uri newAvatarUri) {
            NewAvatarUri = newAvatarUri;
        }
    }

    public static class ChangeAvatarResponse extends ServiceResponse {
        public String AvatarUrl;
    }

    public static class UpdateProfileRequest {
        public String DisplayName;
        public String Email;

        public UpdateProfileRequest(String displayName, String email) {
            DisplayName = displayName;
            Email = email;
        }
    }

    public static class UpdateProfileResponse extends ServiceResponse {
        public String DisplayName;
        public String Email;
    }

    public static class ChangePasswordRequest {
        public String CurrentPassword;
        public String NewPassword;
        public String ConfirmNewPassword;

        public ChangePasswordRequest(String currentPassword, String newPassword, String confirmNewPassword) {
            CurrentPassword = currentPassword;
            NewPassword = newPassword;
            this.ConfirmNewPassword = confirmNewPassword;
        }
    }

    public static class ChangePasswordResponse extends ServiceResponse {
    }

    public static class UserDetailsUpdatedEvent {
        public User User;

        public UserDetailsUpdatedEvent(com.example.maklumi.yora.infrastructure.User user) {
            User = user;
        }
    }

    public static class UpdateGcmRegistrationRequest {
        public String RegistrationId;

        public UpdateGcmRegistrationRequest(String registrationId) {
            RegistrationId = registrationId;
        }
    }

    public static class UpdateGcmRegistrationResponse extends ServiceResponse {
    }
}
