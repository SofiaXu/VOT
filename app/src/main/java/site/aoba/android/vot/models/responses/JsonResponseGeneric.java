package site.aoba.android.vot.models.responses;

public class JsonResponseGeneric<T> extends JsonResponse {
    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
