package site.aoba.android.vot.common.databindingadapters;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Consumer;

public class RefreshLayoutBindingAdapter {

    @BindingAdapter("onRefresh")
    public static void setOnRefresh(SwipeRefreshLayout refreshLayout, Consumer<SwipeRefreshLayout> function) {
        refreshLayout.setOnRefreshListener(() -> function.accept(refreshLayout));
    }

    @BindingAdapter("onLoadMore")
    public static void setOnLoadMore(RecyclerView view, Consumer<RecyclerView> function) {
        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItemPosition;
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                }
                if (recyclerView.getAdapter() != null) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                            && lastVisibleItemPosition + 1 == recyclerView.getAdapter().getItemCount()) {
                        function.accept(recyclerView);
                    }
                }
            }
        });
    }
}
