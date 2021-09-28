package site.aoba.android.vot.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.architecture.NavigableViewModel;
import site.aoba.android.vot.databinding.CarouselItemBinding;
import site.aoba.android.vot.databinding.VideoCardBinding;
import site.aoba.android.vot.models.Video;

import java.util.Random;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {
    private ObservableList<Video> videos;
    private final NavigableViewModel<?> parent;
    

    public CarouselAdapter(NavigableViewModel<?> parent) {
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
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CarouselItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.carousel_item, parent, false);
        return new CarouselViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        holder.binding.setCarouselAdapterItemData(videos.get(position));
        holder.binding.setCarouselAdapterParentViewModel(parent);
        holder.bindingImage();
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder {
        private final CarouselItemBinding binding;

        public  CarouselViewHolder(@NonNull CarouselItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public CarouselItemBinding getBinding() {
            return binding;
        }

        public void bindingImage() {
            Glide.with(binding.getRoot())
                    .load(binding.getRoot().getContext().getString(R.string.base_url) + "/img/covers/"
                            + binding.getCarouselAdapterItemData().getId() + ".png")
                    .placeholder(R.drawable.no_image)
                    .fallback(R.drawable.no_image)
                    .into((ImageView) binding.getRoot().findViewById(R.id.imgBanner));
        }
    }
}
