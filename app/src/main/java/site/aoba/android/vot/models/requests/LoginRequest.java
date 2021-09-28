package site.aoba.android.vot.models.requests;

public class LoginRequest {
    private long userId;
    private String password;

    public long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
