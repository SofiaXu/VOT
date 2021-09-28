package site.aoba.android.vot.modules.votclient;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class VOTClientModule {
    @Provides
    @Singleton
    VOTClient provideMovieClient(Retrofit retrofit) {
        return  retrofit.create(VOTClient.class);
    }
}
