package site.aoba.android.vot.common.eventargs;

import androidx.annotation.StringRes;
import site.aoba.android.vot.common.architecture.EventArg;

public class ShowSnackbarEventArg extends EventArg {
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
