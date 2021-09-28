package site.aoba.android.vot.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
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
import site.aoba.android.vot.databinding.VideoCardBinding;
import site.aoba.android.vot.models.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder> {
    private ObservableList<Video> videos;
    private final NavigableViewModel<?> parent;

    public VideoListAdapter(NavigableViewModel<?> parent) {
        this.parent = parent;
    }

    public void setVideos(ObservableList<Video> videos) {
        this.videos = videos;
        videos.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Video>>() {
            @Override
            public void onChanged(ObservableList<Video> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Video> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Video> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Video> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemRangeRemoved(fromPosition, itemCount);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Video> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public VideoListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        VideoCardBinding binding = DataBindingUtil.inflate(inflater, R.layout.video_card, parent, false);
        return new VideoListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VideoListViewHolder holder, int position) {
        holder.binding.setVideoCardData(videos.get(position));
        holder.binding.setVideoCardParentViewModel(parent);
        holder.bindingImage();
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoListViewHolder extends RecyclerView.ViewHolder {
        VideoCardBinding binding;
        public VideoListViewHolder(@NonNull VideoCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindingImage() {
            Glide.with(binding.getRoot())
                    .load(binding.getRoot().getContext().getString(R.string.base_url) + "/img/covers/"
                            + binding.getVideoCardData().getId() + ".png")
                    .placeholder(R.drawable.no_image)
                    .fallback(R.drawable.no_image)
                    .into((ImageView) binding.getRoot().findViewById(R.id.video_card_image));
        }
    }
}
