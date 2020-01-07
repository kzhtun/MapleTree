package com.info121.mapletree.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.info121.mapletree.R;
import com.info121.mapletree.activities.LevelsActivity;
import com.info121.mapletree.models.RoundsDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.ViewHolder>{
    private int lastPosition = -1;
    Context mContext;
    List<RoundsDetails> roundsDetailsList = new ArrayList<>();

    public RoundAdapter(Context context, List<RoundsDetails> roundsDetailsList) {
        this.mContext = context;
        this.roundsDetailsList = roundsDetailsList;
    }

    public void updateList(List<RoundsDetails> list){
        roundsDetailsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View promotionView = inflater.inflate(R.layout.cell_round, parent, false);

        // Return a new holder instance
        return new ViewHolder(promotionView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        setAnimation(viewHolder.itemView, i);

        if(roundsDetailsList.get(i).getStatus().equalsIgnoreCase("CLOSE")){
            viewHolder.parent.setBackgroundResource(R.drawable.rounded_layout_dark);
        }

        viewHolder.title.setText(roundsDetailsList.get(i).getDescription());

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LevelsActivity.class);
                intent.putExtra("ROUND", roundsDetailsList.get(i).getCode());
                intent.putExtra("NAME", roundsDetailsList.get(i).getDescription());

                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (roundsDetailsList == null) ? 0 : roundsDetailsList.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.round_title)
        TextView title;

        @BindView(R.id.main_layout)
        LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
