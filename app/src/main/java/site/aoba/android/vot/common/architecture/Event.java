package site.aoba.android.vot.common.architecture;

import androidx.lifecycle.LiveData;

public class Event<T extends EventArg> extends LiveData<EventArgumentHolder<T>> {
    public void invokeAsync(T value) {
        EventArgumentHolder<T> arg = new EventArgumentHolder<>(value);
        super.postValue(arg);
    }

    public void invoke(T value) {
        EventArgumentHolder<T> arg = new EventArgumentHolder<>(value);
        super.setValue(arg);
    }
}
