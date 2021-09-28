package site.aoba.android.vot.ui.smoothhome;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager2.widget.ViewPager2;
import dagger.hilt.android.AndroidEntryPoint;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.adapters.SmoothSliderAdapter;
import site.aoba.android.vot.ui.home.HomeFragment;
import site.aoba.android.vot.ui.videolist.VideoListFragment;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@AndroidEntryPoint
public class SmoothHomeFragment extends Fragment {
    private List<Fragment> fragments;
    @Inject
    HomeFragment homeFragment;
    @Inject
    VideoListFragment videoListFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        fragments.add(homeFragment);
        fragments.add(videoListFragment);
        View view = inflater.inflate(R.layout.smooth_home_fragment, container, false);
        ((ViewPager2)view.findViewById(R.id.home_slider)).setAdapter(new SmoothSliderAdapter(this, fragments));
        return view;
    }

}