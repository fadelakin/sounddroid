package com.fisheradelakin.sounddroid.soundcloud;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Fisher on 6/20/15.
 */
public interface SoundCloudService {

    String CLIENT_ID = "142ae5918859b572711bdaeeae3bef0a";

    @GET("/tracks?client_id="+CLIENT_ID)
    void searchSongs(@Query("q") String query, Callback<List<Track>> cb);
}
