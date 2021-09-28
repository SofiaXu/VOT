package site.aoba.android.vot.ui.videolist;

import android.net.Uri;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavDeepLinkRequest;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import dagger.hilt.android.AndroidEntryPoint;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.architecture.EventArgumentHolder;
import site.aoba.android.vot.common.architecture.NavigationEventArg;
import site.aoba.android.vot.common.databindingadapters.*;
import site.aoba.android.vot.common.eventargs.ShowSnackbarEventArg;
import site.aoba.android.vot.databinding.VideoListFragmentBinding;

import javax.inject.Inject;

@AndroidEntryPoint
public class VideoListFragment extends Fragment {

    private VideoListViewModel mViewModel;

    @Inject
    public VideoListFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VideoListViewModel.class);
        VideoListFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.video_list_fragment, container, false);
        binding.setVideoListData(mViewModel);
        mViewModel.getNavigationEvent().observe(this, new Observer<EventArgumentHolder<NavigationEventArg<String>>>() {
            private final int id = 30;
            @Override
            public void onChanged(EventArgumentHolder<NavigationEventArg<String>> navigationEventArgEventArgumentHolder) {
                NavigationEventArg<?> x = navigationEventArgEventArgumentHolder.tryGetValue(id);
                if (x == null) return;
                Navigation.findNavController(getView())
                        .navigate(NavDeepLinkRequest.Builder
                                .fromUri(Uri.parse(x.getDestination())).build());
            }
        });
        mViewModel.getShowSnackbarEvent().observe(this,
                new Observer<EventArgumentHolder<ShowSnackbarEventArg>>() {
                    private final int handlerId = (int) (Math.random() * 100);
                    @Override
                    public void onChanged(EventArgumentHolder<ShowSnackbarEventArg> x) {
                        ShowSnackbarEventArg arg = x.tryGetValue(handlerId);
                        final Snackbar s = Snackbar.make(getView().getRootView(), arg.getTextId(), Snackbar.LENGTH_LONG);
                        if (arg.isCloseButtonShow()) {
                            s.setAction(getString(R.string.popup_close), l -> s.dismiss()).show();
                        } else {
                            s.show();
                        }
                    }
                });
        return binding.getRoot();
    }
}