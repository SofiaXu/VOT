package site.aoba.android.vot.models;

import java.util.Date;

public class Follow {
    private User followed;
    private Date followTime;

    public Date getFollowTime() {
        return followTime;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }

    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }
}
