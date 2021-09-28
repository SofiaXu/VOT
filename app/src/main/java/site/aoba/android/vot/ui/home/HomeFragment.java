package site.aoba.android.vot.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDeepLinkRequest;
import androidx.navigation.Navigation;
import dagger.hilt.android.AndroidEntryPoint;
import org.jetbrains.annotations.NotNull;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.architecture.EventArgumentHolder;
import site.aoba.android.vot.common.architecture.NavigationEventArg;
import site.aoba.android.vot.common.databindingadapters.*;
import site.aoba.android.vot.databinding.HomeFragmentBinding;

import javax.inject.Inject;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @Inject
    public HomeFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        HomeFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        binding.setHomeData(homeViewModel);
        homeViewModel.getNavigationEvent().observe(this, new Observer<EventArgumentHolder<NavigationEventArg<String>>>() {
            private final int id = 35;
            @Override
            public void onChanged(EventArgumentHolder<NavigationEventArg<String>> navigationEventArgEventArgumentHolder) {
                NavigationEventArg<?> x = navigationEventArgEventArgumentHolder.tryGetValue(id);
                if (x == null) return;
                Navigation.findNavController(getView())
                        .navigate(NavDeepLinkRequest.Builder
                                .fromUri(Uri.parse(x.getDestination())).build());
            }
        });
        return binding.getRoot();
    }
}