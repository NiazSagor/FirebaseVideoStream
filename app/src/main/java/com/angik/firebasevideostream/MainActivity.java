package com.angik.firebasevideostream;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.angik.firebasevideostream.VideoClass.Common;
import com.angik.firebasevideostream.ViewHolder.VideoVideoHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Very important thing here
         * If we use Firebase UI for populating recycler view
         * then we can not use mRecyclerView.setHasFixedSize(true)
         * Otherwise it will not work
         */

        mRecyclerView = findViewById(R.id.videoRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("video");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: onstart called");

        FirebaseRecyclerOptions<Common> options =
                new FirebaseRecyclerOptions.Builder<Common>()
                        .setQuery(databaseReference, Common.class)
                        .setLifecycleOwner(this)
                        .build();

        FirebaseRecyclerAdapter<Common, VideoVideoHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Common, VideoVideoHolder>
                (options) {
            @NonNull
            @Override
            public VideoVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.player, parent, false);

                Log.d(TAG, "onCreateViewHolder: ");

                return new VideoVideoHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull VideoVideoHolder holder, int position, @NonNull Common model) {
                holder.setVideo(getApplication(), model.getTitle(), model.getUrl());
                holder.setTitle(model.getTitle());
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}