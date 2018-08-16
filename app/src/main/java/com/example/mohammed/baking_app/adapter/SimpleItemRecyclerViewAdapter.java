package com.example.mohammed.baking_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohammed.baking_app.R;
import com.example.mohammed.baking_app.activitiesAndFragments.ReceipeDetailActivity;
import com.example.mohammed.baking_app.activitiesAndFragments.ReceipeDetailFragment;
import com.example.mohammed.baking_app.activitiesAndFragments.ReceipeListActivity;
import com.example.mohammed.baking_app.models.StepsBean;

import java.util.ArrayList;

public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {


    private final ReceipeListActivity mParentActivity;
    private final boolean mTwoPane;
    private ArrayList<StepsBean> steps;

    public SimpleItemRecyclerViewAdapter(ReceipeListActivity mParentActivity, ArrayList<StepsBean> steps, boolean mTwoPane) {
        this.mParentActivity = mParentActivity;
        this.steps = steps;
        this.mTwoPane = mTwoPane;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receipe_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        holder.mContentView.setText(steps.get(position).getShortDescription());
        holder.itemView.setTag(steps.get(position));

        holder.itemView.setOnClickListener(view -> {
            if (mTwoPane) {
                /*
                    the same thing as the acidity algorithm,but now there not no activity between
                    the DetailsFragment and the Clicked Adapter position
                 */
                displayFragment(holder.getAdapterPosition());
            } else {
                /*
                        1- pass the the steps to the activity
                        2- from the activity pass the steps and the position to the fragment with setArguments
                        3-in detailsFragment, show the clicked steps positon using the steps from previois step,
                         you can navigate through the steps with next and prev button
                 */
                Context context = view.getContext();
                Intent intent = new Intent(context, ReceipeDetailActivity.class);
                intent.putParcelableArrayListExtra(ReceipeDetailFragment.STEPS, steps);
                intent.putExtra(ReceipeDetailFragment.POSITION, holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });

    }

    public void displayFragment(int position) {
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ReceipeDetailFragment.STEPS, steps);
        arguments.putInt(ReceipeDetailFragment.POSITION, position);

        ReceipeDetailFragment fragment = new ReceipeDetailFragment();
        fragment.setArguments(arguments);

        mParentActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.receipe_detail_container, fragment)
                .commit();
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.content);
        }
    }
}