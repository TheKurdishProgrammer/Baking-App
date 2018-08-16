package com.example.mohammed.baking_app.activitiesAndFragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohammed.baking_app.R;
import com.example.mohammed.baking_app.databinding.ReceipeDetailBinding;
import com.example.mohammed.baking_app.models.StepsBean;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * A fragment representing a single Receipe detail screen.
 * This fragment is either contained in a {@link ReceipeListActivity}
 * in two-pane mode (on tablets) or a {@link ReceipeDetailActivity}
 * on handsets.
 */
public class ReceipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String STEPS = "item_id";
    public static final String POSITION = "POSITION";
    private String userAgent =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:40.0) Gecko/20100101 Firefox/40.0";
    /**
     * The dummy content this fragment is presenting.
     */
    private ArrayList<StepsBean> steps;
    private int currentStepPoisition;
    private ReceipeDetailBinding binding;
    private SimpleExoPlayer player;
    private String videourl;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReceipeDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(STEPS)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            steps = getArguments().getParcelableArrayList(STEPS);
            currentStepPoisition = getArguments().getInt(POSITION);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.receipe_detail, container, false);

        initializePlayer();
        setListeners();
        populateDate();

        return binding.getRoot();
    }

    private void setListeners() {
        binding.fabNext.setOnClickListener(v -> {
            currentStepPoisition++;
            populateDate();
        });
        binding.fabPrev.setOnClickListener(v -> {
            currentStepPoisition--;
            populateDate();
        });
    }

    private void populateDate() {

        StepsBean stepsBean = steps.get(currentStepPoisition);
        videourl = stepsBean.getVideoURL();
        binding.currentPage.setText(getString(R.string.page_indicator, currentStepPoisition, steps.size()));
        binding.stepDescription.setText(stepsBean.getDescription());


        checkNavigationFabVisibitlity();
        checkVideoAvailability();

    }

    private void checkVideoAvailability() {

        if (TextUtils.isEmpty(videourl)) {
            binding.noVideo.setVisibility(View.VISIBLE);
            binding.stepVideo.setVisibility(View.GONE);
        } else {

            binding.noVideo.setVisibility(View.GONE);
            binding.stepVideo.setVisibility(View.VISIBLE);
            setVideoMediaSource();

        }
    }

    @SuppressLint("RestrictedApi")
    private void checkNavigationFabVisibitlity() {

        if (currentStepPoisition == 0) {
            binding.fabPrev.setVisibility(View.INVISIBLE);
            binding.fabNext.setVisibility(View.VISIBLE);
        } else if (currentStepPoisition == steps.size() - 1) {

            binding.fabPrev.setVisibility(View.VISIBLE);
            binding.fabNext.setVisibility(View.INVISIBLE);
        } else {

            binding.fabPrev.setVisibility(View.VISIBLE);
            binding.fabNext.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    private void initializePlayer() {
        // Create a default TrackSelector
//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//
//        TrackSelection.Factory videoTrackSelectionFactory =
//                new AdaptiveTrackSelection.Factory(bandwidthMeter);
//
//        TrackSelector trackSelector
//                new DefaultTrackSelector(videoTrackSelectionFactory);

        //Initialize the player
        player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
        //Initialize simpleExoPlayerView
        binding.stepVideo.setPlayer(player);


        // Produces DataSource instances through which media data is loaded.
//        DataSource.Factory dataSourceFactory =
//                new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "baking_app"));

        // Produces Extractor instances for parsing the media data.
//        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


    }

    private void setVideoMediaSource() {

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "ExoPlayer"));


        // This is the MediaSource representing the media to be played.
        Uri videoUri = Uri.parse(videourl);


        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).
                createMediaSource(videoUri);

        player.prepare(videoSource);
        player.setPlayWhenReady(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.stepVideo.setPlayer(null);
        player.release();
        player = null;
    }
}
