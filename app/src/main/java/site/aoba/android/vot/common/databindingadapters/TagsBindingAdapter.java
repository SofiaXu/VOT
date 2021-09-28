package site.aoba.android.vot.common.databindingadapters;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.scopes.ActivityScoped;
import dagger.hilt.android.scopes.FragmentScoped;
import site.aoba.android.vot.common.adapters.TagsAdapter;
import site.aoba.android.vot.common.adapters.VideoListAdapter;
import site.aoba.android.vot.models.Tag;

import javax.inject.Inject;

public class TagsBindingAdapter {

    @BindingAdapter("tagItems")
    public static void setTagItems(RecyclerView recyclerView, ObservableList<Tag> tags) {
        if (recyclerView.getAdapter() == null) {
            TagsAdapter adapter = new TagsAdapter();
            adapter.setTags(tags);
            recyclerView.setAdapter(adapter);
        } else {
            ((TagsAdapter)recyclerView.getAdapter()).setTags(tags);
        }
    }
}
