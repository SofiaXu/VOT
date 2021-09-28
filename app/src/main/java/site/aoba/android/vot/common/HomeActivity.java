package site.aoba.android.vot.common;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.Observable;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.architecture.EventArg;
import site.aoba.android.vot.common.architecture.EventArgumentHolder;
import site.aoba.android.vot.modules.votclient.VOTClient;
import site.aoba.android.vot.services.IdentityService;
import site.aoba.android.vot.services.OnLoginEventArg;

import javax.inject.Inject;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {
    @Inject
    IdentityService identityService;
    @Inject
    VOTClient client;

    private AppBarConfiguration mAppBarConfiguration;

    private ImageView userAvatar;
    private TextView username;
    private TextView userRole;
    private NavController navController;

    private final Object locker = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        username = navigationView.getHeaderView(0).findViewById(R.id.nav_username);
        userAvatar = navigationView.getHeaderView(0).findViewById(R.id.nav_user_avatar);
        userRole = navigationView.getHeaderView(0).findViewById(R.id.nav_user_role);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_smooth_home, R.id.nav_search)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        identityService.getOnLoginEvent().observe(this, new Observer<EventArgumentHolder<OnLoginEventArg>>() {
            private final int handlerId = (int) (Math.random() * 100);
            @Override
            public void onChanged(EventArgumentHolder<OnLoginEventArg> eventArgEventArgumentHolder) {
                OnLoginEventArg arg = eventArgEventArgumentHolder.tryGetValue(handlerId);
                if (arg == null) return;
                setNavHeader(arg.isLogin());
            }
        });
        identityService.isValid();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setNavHeader(boolean isLogin) {
        if (isLogin) {
            username.setText(identityService.getUser().getUserName());
            userRole.setText(identityService.getUser().getUserRole().getName());
            userAvatar.setImageDrawable(getDrawable(R.drawable.ic_launcher));
        } else {
            username.setText(R.string.nav_username);
            userRole.setText(R.string.nav_user_role);
            userAvatar.setImageDrawable(getDrawable(R.drawable.ic_launcher));
        }
    }
}