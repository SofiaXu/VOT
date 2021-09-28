package site.aoba.android.vot.ui.home;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.orhanobut.logger.Logger;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.architecture.BindableViewModel;
import site.aoba.android.vot.common.architecture.NavigableViewModel;
import site.aoba.android.vot.common.eventargs.ShowSnackbarEventArg;
import site.aoba.android.vot.models.Video;
import site.aoba.android.vot.models.responses.JsonResponseGeneric;
import site.aoba.android.vot.modules.votclient.VOTClient;

import javax.inject.Inject;
import java.util.List;

@HiltViewModel
public class HomeViewModel extends NavigableViewModel<String> implements BindableViewModel {

    private final MutableLiveData<ObservableList<Video>> videos = new MutableLiveData<>(new ObservableArrayList<>());
    private final VOTClient client;

    @Inject
    public HomeViewModel(VOTClient client) {
        this.client = client;
        loadData();
    }

    public MutableLiveData<ObservableList<Video>> getVideos() {
        return videos;
    }

    private void loadData() {
        client.getVideos(1, 5).enqueue(new Callback<JsonResponseGeneric<List<Video>>>() {
            @Override
            public void onResponse(Call<JsonResponseGeneric<List<Video>>> call, Response<JsonResponseGeneric<List<Video>>> response) {
                if (response.isSuccessful()) {
                    List<Video> items = response.body().getContent();
                    if (items.size() == 0) {
                        return;
                    }
                    videos.getValue().addAll(items);
                }
            }

            @Override
            public void onFailure(Call<JsonResponseGeneric<List<Video>>> call, Throwable t) {
                Logger.e(t, "Network error.");
            }
        });
    }

    @Override
    public int getTargetLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    public int getTargetDataId() {
        return BR.homeData;
    }
}