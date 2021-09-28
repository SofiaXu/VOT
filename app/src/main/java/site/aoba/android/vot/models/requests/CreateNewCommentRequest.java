package site.aoba.android.vot.models.requests;

public class CreateNewCommentRequest {
    private long videoId;
    private String content;

    public long getVideoId() {
        return videoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }
}
