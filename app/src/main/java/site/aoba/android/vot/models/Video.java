package site.aoba.android.vot.models;

import java.util.Date;

public class Video {
    private long id;
    private String title;
    private String Info;
    private Date uploadTime;
    private long uploaderId;
    private long commentsCount;
    private long goodCount;
    private long FavoriteCount;
    private int videoStatus;
    private User uploader;

    public long getId() {
        return id;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public int getVideoStatus() {
        return videoStatus;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public long getFavoriteCount() {
        return FavoriteCount;
    }

    public long getGoodCount() {
        return goodCount;
    }

    public long getUploaderId() {
        return uploaderId;
    }

    public String getInfo() {
        return Info;
    }

    public String getTitle() {
        return title;
    }

    public User getUploader() {
        return uploader;
    }

    public void setGoodCount(long goodCount) {
        this.goodCount = goodCount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCommentsCount(long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public void setFavoriteCount(long favoriteCount) {
        FavoriteCount = favoriteCount;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUploaderId(long uploaderId) {
        this.uploaderId = uploaderId;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public void setVideoStatus(int videoStatus) {
        this.videoStatus = videoStatus;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }
}
