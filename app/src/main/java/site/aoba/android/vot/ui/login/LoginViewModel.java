package site.aoba.android.vot.ui.login;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.StringRes;
import androidx.lifecycle.MutableLiveData;
import com.orhanobut.logger.Logger;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.architecture.EventArg;
import site.aoba.android.vot.common.architecture.NavigableViewModel;
import site.aoba.android.vot.common.architecture.NavigationEventArg;
import site.aoba.android.vot.common.architecture.Event;
import site.aoba.android.vot.models.requests.LoginRequest;
import site.aoba.android.vot.models.responses.LoginResponse;
import site.aoba.android.vot.modules.votclient.VOTClient;
import site.aoba.android.vot.services.IdentityService;

import javax.inject.Inject;

@HiltViewModel
public class LoginViewModel extends NavigableViewModel<String> {
    private MutableLiveData<String> userId;
    private MutableLiveData<String> password;
    private MutableLiveData<Boolean> isConnecting;
    private final IdentityService identityService;
    private final VOTClient client;
    private final Event<ShowSnackbarEventArg> showSnackbarEvent;
    @Inject
    public LoginViewModel(IdentityService identityService, VOTClient client) {
        showSnackbarEvent = new Event<>();
        this.identityService = identityService;
        this.client = client;
        password = new MutableLiveData<>();
        userId = new MutableLiveData<>();
        isConnecting = new MutableLiveData<>();
        isConnecting.postValue(true);
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public MutableLiveData<String> getUserId() {
        return userId;
    }

    public void setPassword(MutableLiveData<String> password) {
        this.password = password;
    }

    public void setUserId(MutableLiveData<String> userId) {
        this.userId = userId;
    }

    public MutableLiveData<Boolean> getIsConnecting() {
        return isConnecting;
    }

    public void setIsConnecting(MutableLiveData<Boolean> isConnecting) {
        this.isConnecting = isConnecting;
    }

    public Event<ShowSnackbarEventArg> getShowSnackbarEvent() {
        return showSnackbarEvent;
    }

    public void Login(View v) {
        if (password.getValue() == null) {
            return;
        }
        if (userId.getValue() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        LoginRequest request = new LoginRequest();
        request.setPassword(password.getValue());
        request.setUserId(Long.parseLong(getUserId().getValue()));
        getShowSnackbarEvent().invokeAsync(new ShowSnackbarEventArg(R.string.login_fragment_login_popup));
        isConnecting.postValue(true);
        client.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                int code = response.code();
                if (response.isSuccessful()) {
                    LoginResponse body = response.body();
                    assert body != null;
                    identityService.login(body.getToken(), body.getContent());
                    identityService.save();
                    LoginViewModel.super.getNavigationEvent().invokeAsync(new NavigationEventArg<>("vot://home", "LoginSuccess"));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Logger.e(t, "Log in failed. Network error.");
                getShowSnackbarEvent().invokeAsync(new ShowSnackbarEventArg(R.string.login_fragment_login_failed_network_error_popup, true));
            }
        });
    }
}

class ShowSnackbarEventArg extends EventArg {
    private final int textId;

    private final boolean isCloseButtonShow;

    public int getTextId() {
        return textId;
    }

    public boolean isCloseButtonShow() {
        return isCloseButtonShow;
    }

    public ShowSnackbarEventArg(@StringRes int textId, boolean isCloseButtonShow) {
        this.textId = textId;
        this.isCloseButtonShow = isCloseButtonShow;
    }

    public ShowSnackbarEventArg(@StringRes int textId) {
        this.textId = textId;
        this.isCloseButtonShow = false;
    }
}