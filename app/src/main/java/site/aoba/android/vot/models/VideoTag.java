package site.aoba.android.vot.models;

import java.util.List;

public class VideoTag extends Video {
    private List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
