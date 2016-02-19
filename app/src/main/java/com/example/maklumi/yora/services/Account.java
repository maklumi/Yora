package com.example.maklumi.yora.services;

import android.net.Uri;

import com.example.maklumi.yora.infrastructure.ServiceResponse;

/**
 * Created by HomePC on 19/2/2016.
 */
public final class Account {
    private Account(){

    }

    public static class ChangeAvatarRequest {
        public Uri newAvatarUri;

        public ChangeAvatarRequest(Uri newAvatarUri){
            newAvatarUri = newAvatarUri;
        }
    }

    public static class ChangeAvatarResponse extends ServiceResponse {
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

}
