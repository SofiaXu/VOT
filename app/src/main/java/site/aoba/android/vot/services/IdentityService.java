package site.aoba.android.vot.services;

import android.content.Context;
import android.content.SharedPreferences;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orhanobut.logger.Logger;
import dagger.hilt.android.qualifiers.ApplicationContext;
import site.aoba.android.vot.common.architecture.Event;
import site.aoba.android.vot.common.architecture.EventArg;
import site.aoba.android.vot.models.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IdentityService {
    private User user;
    private String token;
    private final SharedPreferences sharedPreferences;
    private final ObjectMapper objectMapper;
    private final JWTVerifier verifier;
    private final Event<OnLoginEventArg> onLoginEvent = new Event<>();
    @Inject
    public IdentityService(@ApplicationContext Context context, ObjectMapper objectMapper) {
        this.sharedPreferences = context.getSharedPreferences("VOT.Identity", Context.MODE_PRIVATE);
        this.objectMapper = objectMapper;
        this.token = sharedPreferences.getString("Token", null);
        verifier = JWT.require(Algorithm.HMAC256("XBxNkZ9RJvzZv5zzn3cAKl74DXbRzP0X"))
                .withIssuer("VOT")
                .build();
        if (isValid(false)) {
            try {
                user = objectMapper.readValue(sharedPreferences.getString("UserInfo", null), User.class);
            } catch (JsonProcessingException e) {
                Logger.e(e, "Failed to read user info. Will delete the token. ");
                token = null;
            }
        } else {
            token = null;
        }
    }

    public boolean isValid() {
        return isValid(true);
    }

    private boolean isValid(boolean canRaiseEvent) {
        if (token == null) {
            onLoginEvent.invokeAsync(new OnLoginEventArg(false));
            return false;
        }
        try {
            verifier.verify(token);
            if (canRaiseEvent) onLoginEvent.invokeAsync(new OnLoginEventArg(true));
            return true;
        } catch (JWTVerificationException exception) {
            Logger.e(exception, "Invalid JWT Token. ");
            if (canRaiseEvent) onLoginEvent.invokeAsync(new OnLoginEventArg(false));
            return false;
        }
    }

    public void logout() {
        this.token = null;
        this.user = null;
        onLoginEvent.invokeAsync(new OnLoginEventArg(false));
    }

    public boolean save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Token", token);
        try {
            editor.putString("UserInfo", objectMapper.writer().writeValueAsString(user));
        } catch (JsonProcessingException e) {
            Logger.e(e, "Failed to write user info. ");
        }
        return editor.commit();
    }

    public void saveAsync() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Token", token);
        try {
            editor.putString("UserInfo", objectMapper.writer().writeValueAsString(user));
        } catch (JsonProcessingException e) {
            Logger.e(e, "Failed to write user info. ");
        }
        editor.apply();
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public Event<OnLoginEventArg> getOnLoginEvent() {
        return onLoginEvent;
    }

    public void login(String token, User user) {
        this.token = token;
        this.user = user;
        onLoginEvent.invokeAsync(new OnLoginEventArg(true));
    }
}
