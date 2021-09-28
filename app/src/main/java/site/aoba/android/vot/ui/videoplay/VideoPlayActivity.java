package site.aoba.android.vot.ui.videoplay;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.snackbar.Snackbar;
import dagger.hilt.android.AndroidEntryPoint;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.architecture.EventArgumentHolder;
import site.aoba.android.vot.common.databindingadapters.*;
import site.aoba.android.vot.common.eventargs.ShowSnackbarEventArg;
import site.aoba.android.vot.databinding.ActivityVideoPlayBinding;

import javax.inject.Inject;

@AndroidEntryPoint
public class VideoPlayActivity extends AppCompatActivity {
    private VideoPlayViewModel vm;
    private PlayerView playerView;
    @Inject
    ExoPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long id = getIntent().getExtras().getLong("videoId");
        super.onCreate(savedInstanceState);
        ActivityVideoPlayBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_video_play);
        vm = new ViewModelProvider(this).get(VideoPlayViewModel.class);
        binding.setVideoPlayData(vm);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        playerView = findViewById(R.id.video_play_player);
        playerView.setPlayer(player);
        player.setMediaItem(MediaItem.fromUri(getString(R.string.base_url) + "/api/video/stream/" + id));
        player.prepare();
        player.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}