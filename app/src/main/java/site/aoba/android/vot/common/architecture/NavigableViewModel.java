package site.aoba.android.vot.common.architecture;

import androidx.lifecycle.ViewModel;

import java.io.Serializable;

public class NavigableViewModel<T extends Serializable> extends ViewModel {
    private final Event<NavigationEventArg<T>> navigationEvent = new Event<>();

    public Event<NavigationEventArg<T>> getNavigationEvent() {
        return navigationEvent;
    }

    public void invokeNavigate(String deepLink) {
        invokeNavigate(deepLink, null);
    }

    public void invokeNavigate(String deepLink, T args) {
        navigationEvent.invoke(new NavigationEventArg<>(deepLink, args));
    }
}
