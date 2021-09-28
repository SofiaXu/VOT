package site.aoba.android.vot.ui.login;

import android.net.Uri;
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
import org.jetbrains.annotations.NotNull;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.architecture.NavigationEventArg;
import site.aoba.android.vot.common.architecture.EventArgumentHolder;
import site.aoba.android.vot.databinding.LoginFragmentBinding;
import site.aoba.android.vot.services.IdentityService;

import javax.inject.Inject;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    @Inject
    IdentityService identityService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LoginFragmentBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.login_fragment, container, false);
        LoginViewModel vm = new ViewModelProvider(this).get(LoginViewModel.class);
        vm.getShowSnackbarEvent().observe(this,
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
        vm.getNavigationEvent().observe(this,
                new Observer<EventArgumentHolder<NavigationEventArg<String>>>() {
            private final int handlerId = (int) (Math.random() * 100);
            @Override
            public void onChanged(EventArgumentHolder<NavigationEventArg<String>> x) {
                NavigationEventArg<?> a = x.tryGetValue(handlerId);
                if (a == null) return;
                Navigation.findNavController(getView())
                        .navigate(NavDeepLinkRequest.Builder
                                .fromUri(Uri.parse(a.getDestination())).build());
            }
        });
        binding.setData(vm);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (identityService.isValid()) {
            identityService.logout();
            Snackbar s = Snackbar.make(getView().getRootView(), R.string.login_fragment_logout_popup, Snackbar.LENGTH_LONG);
            s.setAction(getString(R.string.popup_close), l -> s.dismiss()).show();
            identityService.save();
        }
    }
}