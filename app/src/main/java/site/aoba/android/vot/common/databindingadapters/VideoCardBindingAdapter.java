package site.aoba.android.vot.common.databindingadapters;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.scopes.FragmentScoped;
import site.aoba.android.vot.common.adapters.VideoListAdapter;
import site.aoba.android.vot.common.architecture.NavigableViewModel;
import site.aoba.android.vot.models.Video;

import javax.inject.Inject;

public class VideoCardBindingAdapter {

    @BindingAdapter({ "videoItems", "videoItemsParent" })
    public static void setVideoListItems(RecyclerView recyclerView, ObservableList<Video> list, NavigableViewModel<?> parent) {
        if (recyclerView.getAdapter() == null) {
            VideoListAdapter adapter = new VideoListAdapter(parent);
            adapter.setVideos(list);
            recyclerView.setAdapter(adapter);
        } else {
            ((VideoListAdapter)recyclerView.getAdapter()).setVideos(list);
        }
    }
}
