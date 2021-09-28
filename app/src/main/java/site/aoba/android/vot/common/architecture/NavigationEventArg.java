package site.aoba.android.vot.common.architecture;

import java.io.Serializable;

public class NavigationEventArg<T extends Serializable> extends EventArg {
    private String destination;
    private T args;

    public String getDestination() {
        return destination;
    }

    public void setArgs(T args) {
        this.args = args;
    }

    public T getArgs() {
        return args;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public NavigationEventArg(String destination, T args) {
        this.destination = destination;
        this.args = args;
    }
}
