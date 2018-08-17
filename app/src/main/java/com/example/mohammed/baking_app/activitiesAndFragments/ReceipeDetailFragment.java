package com.example.mohammed.baking_app.activitiesAndFragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohammed.baking_app.R;
import com.example.mohammed.baking_app.databinding.ReceipeDetailBinding;
import com.example.mohammed.baking_app.models.StepsBean;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
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
    private static final String CURRENT_TIME_POSITION = "CURRENT_TIME_POSITION";
    private static final String PLAY_BACK_STATE = "PLAY_BACK_STATE";


    private ArrayList<StepsBean> steps;
    private int currentStepPoisition;
    private ReceipeDetailBinding binding;
    private SimpleExoPlayer player;
    private String videourl;
    private long currentVideoPosition;
    private boolean playBackState;

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

        if (savedInstanceState != null) {
            currentVideoPosition = savedInstanceState.getLong(CURRENT_TIME_POSITION, 0);
            steps = savedInstanceState.getParcelableArrayList(STEPS);
            playBackState = savedInstanceState.getBoolean(PLAY_BACK_STATE, true);
            currentStepPoisition = savedInstanceState.getInt(POSITION, 0);
        }
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
        binding.currentPage.setText(getString(R.string.page_indicator, currentStepPoisition + 1, steps.size()));
        binding.stepDescription.setText(stepsBean.getDescription());


        checkNavigationFabVisibitlity();
        checkVideoAvailability();

    }

    private void checkVideoAvailability() {

        if (TextUtils.isEmpty(videourl)) {
            binding.noVideo.setVisibility(View.VISIBLE);
            binding.videoContainer.setVisibility(View.GONE);
        } else {

            binding.noVideo.setVisibility(View.GONE);
            binding.videoContainer.setVisibility(View.VISIBLE);
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
        if (Util.SDK_INT >= 24)
            initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null)
            initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        currentVideoPosition = player.getCurrentPosition();

    }

    private void initializePlayer() {

        //Initialize the player
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter()));

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        //Initialize simpleExoPlayerView
        binding.stepVideo.setPlayer(player);
        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.e(getActivity().getLocalClassName(), isLoading + "");
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                switch (playbackState) {
                    case Player.STATE_BUFFERING:
                        binding.videoProgress.setVisibility(View.VISIBLE);
                        break;
                    default:
                        binding.videoProgress.setVisibility(View.GONE);
                }


//                if(playWhenReady)
//                    binding.videoProgress.setVisibility(View.GONE);
//                else
//                    binding.videoProgress.setVisibility(View.VISIBLE);
                Log.e("STATE", playbackState + "");
                Log.e("Ready", playWhenReady + "");

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        setVideoMediaSource();
    }

    private void setVideoMediaSource() {

        if (player != null) {
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "ExoPlayer"));


            // This is the MediaSource representing the media to be played.
            Uri videoUri = Uri.parse(videourl);


            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).
                    createMediaSource(videoUri);

            player.prepare(videoSource);
            player.seekTo(currentVideoPosition);
            player.setPlayWhenReady(playBackState);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        player.release();
        binding.stepVideo.setPlayer(null);
        player = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CURRENT_TIME_POSITION, currentVideoPosition);
        outState.putParcelableArrayList(STEPS, steps);
        outState.putBoolean(PLAY_BACK_STATE, player.getPlayWhenReady());
        outState.putInt(POSITION, currentStepPoisition);
    }
}
