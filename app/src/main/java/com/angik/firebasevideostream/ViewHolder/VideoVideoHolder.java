package com.angik.firebasevideostream.ViewHolder;

import android.app.Application;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.angik.firebasevideostream.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class VideoVideoHolder extends RecyclerView.ViewHolder {

    SimpleExoPlayer mSimpleExoplayer;
    PlayerView mPlayerView;

    public VideoVideoHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setVideo(final Application context, String title, String url) {
        TextView titleTextView = itemView.findViewById(R.id.videoTitleTextView);

        mPlayerView = itemView.findViewById(R.id.player);

        titleTextView.setText(title);

        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(context).build();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            mSimpleExoplayer = ExoPlayerFactory.newSimpleInstance(context);
            Uri video = Uri.parse(url);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(video, dataSourceFactory, extractorsFactory, null, null);
            mPlayerView.setPlayer(mSimpleExoplayer);
            mSimpleExoplayer.prepare(mediaSource);
            mSimpleExoplayer.setPlayWhenReady(false);
        } catch (Exception e) {
            Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void setTitle(String title){
        TextView titleTextView = itemView.findViewById(R.id.videoTitleTextView);
        titleTextView.setText(title);
    }
}
