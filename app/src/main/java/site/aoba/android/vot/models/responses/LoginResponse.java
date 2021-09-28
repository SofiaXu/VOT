package site.aoba.android.vot.models.responses;

import site.aoba.android.vot.models.User;

public class LoginResponse extends JsonResponseGeneric<User> {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
