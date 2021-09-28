package site.aoba.android.vot.common.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.jetbrains.annotations.NotNull;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.architecture.NavigableViewModel;
import site.aoba.android.vot.databinding.TagItemBinding;
import site.aoba.android.vot.databinding.VideoCardBinding;
import site.aoba.android.vot.models.Tag;
import site.aoba.android.vot.models.Video;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder> {
    private ObservableList<Tag> tags;

    public void setTags(ObservableList<Tag> tags) {
        this.tags = tags;
        this.tags.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Tag>>() {
            @Override
            public void onChanged(ObservableList<Tag> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Tag> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Tag> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Tag> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemRangeRemoved(fromPosition, itemCount);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Tag> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public TagsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TagItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.tag_item, parent, false);
        return new TagsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TagsViewHolder holder, int position) {
        holder.binding.setTagData(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class TagsViewHolder extends RecyclerView.ViewHolder {
        TagItemBinding binding;
        public TagsViewHolder(@NonNull TagItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

