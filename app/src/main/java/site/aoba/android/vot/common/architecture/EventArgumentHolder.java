package site.aoba.android.vot.common.architecture;

import java.util.ArrayList;
import java.util.List;

public class EventArgumentHolder<T extends EventArg> {
    private final List<Integer> handlerIds;
    private T value;

    public EventArgumentHolder(T value) {
        this.value = value;
        handlerIds = new ArrayList<>();
    }

    public EventArgumentHolder() {
        handlerIds = new ArrayList<>();
    }

    public T tryGetValue(Integer id) {
        if (handlerIds.stream().anyMatch(x -> x.compareTo(id) == 0)) {
            return null;
        }
        handlerIds.add(id);
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        handlerIds.clear();
    }
}
