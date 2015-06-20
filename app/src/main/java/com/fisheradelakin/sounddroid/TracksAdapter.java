package com.fisheradelakin.sounddroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fisheradelakin.sounddroid.soundcloud.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Fisher on 6/20/15.
 */
public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder> {

    private List<Track> mTracks;
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.track_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Track track = mTracks.get(i);
        viewHolder.titleTextView.setText(track.getTitle());
        Picasso.with(mContext).load(track.getAvatarURL()).into(viewHolder.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }



    TracksAdapter(Context context, List<Track> tracks) {
        mContext = context;
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
