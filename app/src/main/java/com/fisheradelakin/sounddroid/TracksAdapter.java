package com.fisheradelakin.sounddroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fisheradelakin.sounddroid.soundcloud.Track;

import java.util.List;

/**
 * Created by Fisher on 6/20/15.
 */
public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder> {

    private List<Track> mTracks;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.track_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Track track = mTracks.get(i);
        viewHolder.titleTextView.setText(track.getTitle());
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }



    TracksAdapter(List<Track> tracks) {
        mTracks = tracks;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private ImageView thumbImageView;


        ViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.track_title);
            thumbImageView = (ImageView) v.findViewById(R.id.track_thumbnail);
        }
    }
}
