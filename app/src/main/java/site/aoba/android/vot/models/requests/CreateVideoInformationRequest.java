package site.aoba.android.vot.models.requests;

import java.util.List;

public class CreateVideoInformationRequest {
    private String title;
    private String info;
    private List<Long> tags;

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public List<Long> getTags() {
        return tags;
    }
}
