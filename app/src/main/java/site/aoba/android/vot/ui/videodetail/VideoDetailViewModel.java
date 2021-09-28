package site.aoba.android.vot.ui.videodetail;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;
import com.orhanobut.logger.Logger;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.architecture.Event;
import site.aoba.android.vot.common.architecture.EventArg;
import site.aoba.android.vot.common.architecture.NavigableViewModel;
import site.aoba.android.vot.common.eventargs.ShowSnackbarEventArg;
import site.aoba.android.vot.models.Tag;
import site.aoba.android.vot.models.VideoTag;
import site.aoba.android.vot.models.responses.JsonResponseGeneric;
import site.aoba.android.vot.modules.votclient.VOTClient;

import javax.inject.Inject;

@HiltViewModel
public class VideoDetailViewModel extends NavigableViewModel<String> {
    private long videoId;
    private final VOTClient client;
    private final Event<VideoUpdatedEventArg> videoUpdatedEventArgEvent = new Event<>();
    private MutableLiveData<String> videoTitle = new MutableLiveData<>();
    private MutableLiveData<String> videoInfo = new MutableLiveData<>();
    private MutableLiveData<String> videoUploader = new MutableLiveData<>();
    private MutableLiveData<ObservableList<Tag>> tags = new MutableLiveData<>(new ObservableArrayList<>());
    private final Event<ShowSnackbarEventArg> showSnackbarEvent = new Event<>();
    @Inject
    public VideoDetailViewModel(VOTClient client) {
        this.client = client;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public void loadData() {
        client.getVideo(videoId).enqueue(new Callback<JsonResponseGeneric<VideoTag>>() {
            @Override
            public void onResponse(Call<JsonResponseGeneric<VideoTag>> call, Response<JsonResponseGeneric<VideoTag>> response) {
                if (response.isSuccessful()) {
                    VideoTag videoTag = response.body().getContent();
                    videoUpdatedEventArgEvent.invoke(new VideoUpdatedEventArg(videoTag));
                    if (videoTag.getTags() != null) {
                        tags.getValue().addAll(videoTag.getTags());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponseGeneric<VideoTag>> call, Throwable t) {
                Logger.e(t, "Network error.");
                getShowSnackbarEvent().invokeAsync(new ShowSnackbarEventArg(R.string.network_error_popup, true));
            }
        });
    }

    public Event<VideoUpdatedEventArg> getVideoUpdatedEventArgEvent() {
        return videoUpdatedEventArgEvent;
    }

    public MutableLiveData<ObservableList<Tag>> getTags() {
        return tags;
    }

    public Event<ShowSnackbarEventArg> getShowSnackbarEvent() {
        return showSnackbarEvent;
    }

    public MutableLiveData<String> getVideoInfo() {
        return videoInfo;
    }

    public MutableLiveData<String> getVideoTitle() {
        return videoTitle;
    }

    public MutableLiveData<String> getVideoUploader() {
        return videoUploader;
    }

    public void setTags(MutableLiveData<ObservableList<Tag>> tags) {
        this.tags = tags;
    }

    public void setVideoInfo(MutableLiveData<String> videoInfo) {
        this.videoInfo = videoInfo;
    }

    public void setVideoTitle(MutableLiveData<String> videoTitle) {
        this.videoTitle = videoTitle;
    }

    public void setVideoUploader(MutableLiveData<String> videoUploader) {
        this.videoUploader = videoUploader;
    }

    public long getVideoId() {
        return videoId;
    }
}

class VideoUpdatedEventArg extends EventArg {
    private final VideoTag videoTag;
    public VideoUpdatedEventArg(VideoTag videoTag) {
        this.videoTag = videoTag;
    }
    public VideoTag getVideoTag() {
        return videoTag;
    }
}