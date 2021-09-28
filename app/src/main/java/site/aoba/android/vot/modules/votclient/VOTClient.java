package site.aoba.android.vot.modules.votclient;

import retrofit2.Call;
import retrofit2.http.*;
import site.aoba.android.vot.models.Video;
import site.aoba.android.vot.models.VideoTag;
import site.aoba.android.vot.models.requests.LoginRequest;
import site.aoba.android.vot.models.responses.JsonResponseGeneric;
import site.aoba.android.vot.models.responses.LoginResponse;

import java.util.List;

public interface VOTClient {
    @POST("Identity/Login")
    Call<LoginResponse> login(@Body LoginRequest request);
    @GET("Video")
    Call<JsonResponseGeneric<List<Video>>> getVideos(@Query("page") int page, @Query("pageSize") int pageSize);
    @GET("Video/search")
    Call<JsonResponseGeneric<List<Video>>> getVideos(@Query("name") String name, @Query("page") int page, @Query("pageSize") int pageSize);
    @GET("Video/{id}")
    Call<JsonResponseGeneric<VideoTag>> getVideo(@Path("id") long id);
}
