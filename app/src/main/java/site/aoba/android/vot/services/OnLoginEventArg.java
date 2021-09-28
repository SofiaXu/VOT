package site.aoba.android.vot.services;

import site.aoba.android.vot.common.architecture.EventArg;

public class OnLoginEventArg extends EventArg {
    private final boolean isLogin;

    public OnLoginEventArg(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
