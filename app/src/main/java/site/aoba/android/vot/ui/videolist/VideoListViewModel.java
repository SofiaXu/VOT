package site.aoba.android.vot.ui.videolist;

import androidx.annotation.StringRes;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.orhanobut.logger.Logger;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.architecture.BindableViewModel;
import site.aoba.android.vot.common.architecture.Event;
import site.aoba.android.vot.common.architecture.EventArg;
import site.aoba.android.vot.common.architecture.NavigableViewModel;
import site.aoba.android.vot.common.eventargs.ShowSnackbarEventArg;
import site.aoba.android.vot.models.User;
import site.aoba.android.vot.models.Video;
import site.aoba.android.vot.models.responses.JsonResponseGeneric;
import site.aoba.android.vot.modules.votclient.VOTClient;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;

@HiltViewModel
public class VideoListViewModel extends NavigableViewModel<String> implements BindableViewModel {
    private final MutableLiveData<ObservableList<Video>> videos;
    private int page = 1;
    private final Event<ShowSnackbarEventArg> showSnackbarEvent = new Event<>();
    private final VOTClient client;
    @Inject
    public VideoListViewModel(VOTClient client) {
        videos = new MutableLiveData<>();
        videos.setValue(new ObservableArrayList<>());
        this.client = client;
        loadData(1, false);
    }

    public MutableLiveData<ObservableList<Video>> getVideos() {
        return videos;
    }

    private void loadData(int page, boolean isRefresh) {
        client.getVideos(page, 10).enqueue(new Callback<JsonResponseGeneric<List<Video>>>() {
            @Override
            public void onResponse(Call<JsonResponseGeneric<List<Video>>> call, Response<JsonResponseGeneric<List<Video>>> response) {
                if (response.isSuccessful()) {
                    List<Video> items = response.body().getContent();
                    if (isRefresh) {
                        videos.getValue().clear();
                        setPage(1);
                    }
                    if (items.size() == 0) {
                        return;
                    }
                    videos.getValue().addAll(items);
                    setPage(getPage() + 1);
                }
            }

            @Override
            public void onFailure(Call<JsonResponseGeneric<List<Video>>> call, Throwable t) {
                Logger.e(t, "Network error.");
                getShowSnackbarEvent().invokeAsync(new ShowSnackbarEventArg(R.string.network_error_popup, true));
            }
        });
        if (isRefresh) {
            videos.getValue().clear();
        }
    }

    private void refresh(SwipeRefreshLayout layout) {
        loadData(1, true);
        layout.setRefreshing(false);
    }

    private void loadMore(RecyclerView view) {
        loadData(page, false);
    }

    public Consumer<SwipeRefreshLayout> getRefreshAction() {
        return this::refresh;
    }

    public Consumer<RecyclerView> getLoadMoreAction() {
        return this::loadMore;
    }

    public Event<ShowSnackbarEventArg> getShowSnackbarEvent() {
        return showSnackbarEvent;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    @Override
    public int getTargetLayoutId() {
        return R.layout.video_list_fragment;
    }

    @Override
    public int getTargetDataId() {
        return BR.videoListData;
    }
}