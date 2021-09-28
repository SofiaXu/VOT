package site.aoba.android.vot.common;

import android.app.Application;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import dagger.hilt.android.HiltAndroidApp;
import site.aoba.android.vot.common.databindingadapters.*;

import javax.inject.Inject;

@HiltAndroidApp
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
