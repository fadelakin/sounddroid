package com.fisheradelakin.sounddroid;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fisheradelakin.sounddroid.soundcloud.SoundCloud;
import com.fisheradelakin.sounddroid.soundcloud.SoundCloudService;
import com.fisheradelakin.sounddroid.soundcloud.Track;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TracksAdapter mAdapter;
    private List<Track> mTracks;
    private TextView mSelectedTitle;
    private ImageView mSelectedThumbnail;
    private MediaPlayer mMediaPlayer;
    private ImageView mPlayerStateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                toggleSongState();
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayerStateButton.setImageResource(R.drawable.ic_play_circle);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.player_toolbar);
        mSelectedTitle = (TextView) findViewById(R.id.selected_title);
        mSelectedThumbnail = (ImageView) findViewById(R.id.selected_thumbnail);

        mPlayerStateButton = (ImageView) findViewById(R.id.player_state);
        mPlayerStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSongState();

            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.songs_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTracks = new ArrayList<>();
        mAdapter = new TracksAdapter(this, mTracks);
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Track selectedTrack = mTracks.get(position);

                mSelectedTitle.setText(selectedTrack.getTitle());
                Picasso.with(MainActivity.this).load(selectedTrack.getAvatarURL()).into(mSelectedThumbnail);

                // song lifecycle
                if(mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.reset();
                }

                try {
                    mMediaPlayer.setDataSource(selectedTrack.getStreamURL()+"?client_id="+SoundCloudService.CLIENT_ID);
                    mMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        // retrofit request
        SoundCloudService service = SoundCloud.getService();
        service.searchSongs("kygo", new Callback<List<Track>>() {
            @Override
            public void success(List<Track> tracks, Response response) {
                mTracks.clear();
                mTracks.addAll(tracks);
                Log.d(TAG, "Track 1 avatar url is " + mTracks.get(0).getAvatarURL());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void toggleSongState() {
        if(mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mPlayerStateButton.setImageResource(R.drawable.ic_play_circle);
        } else {
            mMediaPlayer.start();
            mPlayerStateButton.setImageResource(R.drawable.ic_pause_circle);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMediaPlayer != null) {
            if(mMediaPlayer.isPlaying())
                mMediaPlayer.stop();
            
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
