package site.aoba.android.vot.ui.search;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.orhanobut.logger.Logger;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.architecture.Event;
import site.aoba.android.vot.common.architecture.NavigableViewModel;
import site.aoba.android.vot.common.eventargs.ShowSnackbarEventArg;
import site.aoba.android.vot.models.Video;
import site.aoba.android.vot.models.responses.JsonResponseGeneric;
import site.aoba.android.vot.modules.votclient.VOTClient;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Consumer;

@HiltViewModel
public class SearchViewModel extends NavigableViewModel<String> {
    private MutableLiveData<String> queryText = new MutableLiveData<>();
    private final MutableLiveData<ObservableList<Video>> videos;
    private int page = 1;
    private final Event<ShowSnackbarEventArg> showSnackbarEvent = new Event<>();
    private final VOTClient client;
    @Inject
    public SearchViewModel(VOTClient client) {
        videos = new MutableLiveData<>();
        videos.setValue(new ObservableArrayList<>());
        this.client = client;
        loadData(1, false);
    }

    public MutableLiveData<String> getQueryText() {
        return queryText;
    }

    public void setQueryText(MutableLiveData<String> queryText) {
        this.queryText = queryText;
    }

    public MutableLiveData<ObservableList<Video>> getVideos() {
        return videos;
    }

    private void loadData(int page, boolean isRefresh) {
        client.getVideos(queryText.getValue(), page, 10).enqueue(new Callback<JsonResponseGeneric<List<Video>>>() {
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
                Logger.e(t, "Log in failed. Network error.");
                getShowSnackbarEvent().invokeAsync(new ShowSnackbarEventArg(R.string.network_error_popup, true));
            }
        });
        if (isRefresh) {
            videos.getValue().clear();
        }
    }

    public void refresh(SwipeRefreshLayout layout) {
        loadData(1, true);
        layout.setRefreshing(false);
    }

    public void refresh() {
        loadData(1, true);
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
}