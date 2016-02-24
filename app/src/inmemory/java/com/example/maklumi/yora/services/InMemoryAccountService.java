package com.example.maklumi.yora.services;

import com.example.maklumi.yora.infrastructure.Auth;
import com.example.maklumi.yora.infrastructure.User;
import com.example.maklumi.yora.infrastructure.YoraApplication;
import com.squareup.otto.Subscribe;

/**
 * Created by HomePC on 20/2/2016.
 */
public class InMemoryAccountService extends BaseInMemoryService {

    public InMemoryAccountService (YoraApplication application)
    {
        super(application);
    }

    @Subscribe
    public void updateProfile (final Account.UpdateProfileRequest request){
        final Account.UpdateProfileResponse response = new Account.UpdateProfileResponse();

        if (request.displayName.equals("Hadi")) {
            response.setPropertyErrors("displayName", "Name Hadi already taken");
        }

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                User user = application.getAuth().getUser();
                user.setDisplayName(request.displayName);
                user.setEmail(request.email);

                bus.post(response);
                bus.post(new Account.UserDetailsUpdatedEvent(user));
            }
        }, 2000, 3000);
    }

    @Subscribe
    public void updateAvatar(final Account.ChangeAvatarRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                User user = application.getAuth().getUser();
                user.setAvatarUrl(request.newAvatarUri.toString());

                bus.post(new Account.ChangeAvatarResponse());
                bus.post(new Account.UserDetailsUpdatedEvent(user));
            }
        }, 4000, 5000);
    }

    @Subscribe
    public void changePassword(Account.ChangePasswordRequest request){
        Account.ChangePasswordResponse response = new Account.ChangePasswordResponse();

        if (!request.newPassword.equals(request.confirmNewPassword)) {
            response.setPropertyErrors("confirmNewPassword", "Password does not match");
        }
        if (request.newPassword.length() < 3){
            response.setPropertyErrors("newPassword", "Password too short. Must be longer than 3 characters");
        }
        postDelayed(response);

    }

    @Subscribe
    public void loginWIthUsername(final Account.LoginWithUsernameRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.LoginWithUsernameResponse response = new Account.LoginWithUsernameResponse();

                if (request.Username.equals("dudu"))
                    response.setPropertyErrors("userName", "Invalid username or password");
                loginUser(new Account.UserResponse());
                bus.post(response);
            }
        }, 1000,2000);
    }

    @Subscribe
    public void loginWithExternalToken(Account.LoginWithExternalTokenRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.LoginWithExternalTokenResponse response = new Account.LoginWithExternalTokenResponse();
                loginUser(response);
                bus.post(response);
            }
        }, 1000,2000);
    }

    @Subscribe
    public  void register(Account.RegisterRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.RegisterResponse response = new Account.RegisterResponse();
                loginUser(response);
                bus.post(response);
            }
        }, 1000,2000);
    }

    @Subscribe
    public void externalRegister(Account.RegisterWithExternalTokenRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.RegisterResponse response = new Account.RegisterResponse();
                loginUser(response);
                bus.post(response);
            }
        }, 1000,2000);
    }

    @Subscribe
    public void loginWithLocalToken(Account.LoginWithLocalTokenRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.LoginWIthLocalTokenResponse response = new Account.LoginWIthLocalTokenResponse();
                loginUser(response);
                bus.post(response);
            }
        }, 1000,2000);
    }

    @Subscribe
    public void updateGcmRegistration (Account.UpdateGcmRegistrationRequest request){
        postDelayed(new Account.UpdateGcmRegistrationResponse());
    }

    private void loginUser(Account.UserResponse response) {
        Auth auth = application.getAuth();
        User user = auth.getUser();

        user.setDisplayName("Comel Lote");
        user.setUserName("Kucing Gelap");
        user.setEmail("celc@ho.ho");
        user.setAvatarUrl("http://goo.gl/DUXcYG");
        user.setLoggedIn(true);
        user.setId(123);
        bus.post(new Account.UserDetailsUpdatedEvent(user));

        auth.setAuthToken("falseAuthToken");

        response.DisplayName = user.getDisplayName();
        response.UserName = user.getUserName();
        response.Email = user.getEmail();
        response.AvatarUrl = user.getAvatarUrl();
        response.Id = user.getId();
        response.AuthToken = auth.getAuthToken();
    }
}
