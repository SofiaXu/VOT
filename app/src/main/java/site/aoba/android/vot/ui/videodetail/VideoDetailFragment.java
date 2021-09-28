package site.aoba.android.vot.ui.videodetail;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDeepLinkRequest;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.snackbar.Snackbar;
import dagger.hilt.android.AndroidEntryPoint;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.adapters.TagsAdapter;
import site.aoba.android.vot.common.architecture.EventArgumentHolder;
import site.aoba.android.vot.models.Tag;
import site.aoba.android.vot.models.VideoTag;

import java.text.SimpleDateFormat;

@AndroidEntryPoint
public class VideoDetailFragment extends Fragment {

    private VideoDetailViewModel mViewModel;
    private TextView info;
    private TextView title;
    private TextView uploader;
    private ImageView cover;
    private Button navi;
    private RecyclerView tags;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VideoDetailViewModel.class);
        mViewModel.setVideoId(getArguments().getLong("videoId"));
        mViewModel.loadData();
        View view = inflater.inflate(R.layout.video_detail_fragment, container, false);
        navi = view.findViewById(R.id.detail_go_to_player);
        title = view.findViewById(R.id.detail_title);
        uploader = view.findViewById(R.id.detail_uploader);
        info = view.findViewById(R.id.detail_de);
        cover = view.findViewById(R.id.detail_cover);
        tags = view.findViewById(R.id.detail_tags);
        navi.setOnClickListener((x) -> Navigation.findNavController(getView())
                .navigate(NavDeepLinkRequest.Builder
                        .fromUri(Uri.parse("vot://video/play/" + mViewModel.getVideoId())).build()));
        Glide.with(view)
                .load(getString(R.string.base_url) + "/img/covers/"
                        + mViewModel.getVideoId() + ".png")
                .placeholder(R.drawable.no_image)
                .fallback(R.drawable.no_image)
                .into(cover);
        TagsAdapter tagsAdapter = new TagsAdapter();
        ObservableList<Tag> tagObservableList = new ObservableArrayList<>();
        tagsAdapter.setTags(tagObservableList);
        tags.setLayoutManager(new FlexboxLayoutManager(view.getContext()));
        tags.setAdapter(tagsAdapter);
        mViewModel.getVideoUpdatedEventArgEvent().observe(this, new Observer<EventArgumentHolder<VideoUpdatedEventArg>>() {
            private final int handlerId = (int) (Math.random() * 100);
            @Override
            public void onChanged(EventArgumentHolder<VideoUpdatedEventArg> videoUpdatedEventArgEventArgumentHolder) {
                VideoTag videoTag = videoUpdatedEventArgEventArgumentHolder.tryGetValue(handlerId).getVideoTag();
                if (videoTag != null) {
                    title.setText(videoTag.getTitle());
                    info.setText(videoTag.getInfo());
                    uploader.setText(videoTag.getUploader().getUserName() + "    " + new SimpleDateFormat().format(videoTag.getUploadTime()));
                    if (videoTag.getTags() != null) {
                        tagObservableList.addAll(videoTag.getTags());
                    }
                }
            }
        });
        return view;
    }
}