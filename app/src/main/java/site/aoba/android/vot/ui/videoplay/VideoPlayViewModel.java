package site.aoba.android.vot.ui.videoplay;

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
import site.aoba.android.vot.common.architecture.NavigableViewModel;
import site.aoba.android.vot.common.eventargs.ShowSnackbarEventArg;
import site.aoba.android.vot.models.Tag;
import site.aoba.android.vot.models.Video;
import site.aoba.android.vot.models.VideoTag;
import site.aoba.android.vot.models.responses.JsonResponseGeneric;
import site.aoba.android.vot.modules.votclient.VOTClient;

import javax.inject.Inject;

@HiltViewModel
public class VideoPlayViewModel extends NavigableViewModel<String> {

    @Inject
    public VideoPlayViewModel() {

    }
}
