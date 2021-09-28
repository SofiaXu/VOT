package site.aoba.android.vot.models.requests;

public class ChangePasswordRequest {
    private long userId;
    private String password;
    private String phoneNumber;

    public long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
