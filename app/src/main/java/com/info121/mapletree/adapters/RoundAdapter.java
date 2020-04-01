package com.info121.mapletree.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.info121.mapletree.App;
import com.info121.mapletree.R;
import com.info121.mapletree.activities.LevelsActivity;
import com.info121.mapletree.api.RestClient;
import com.info121.mapletree.models.ObjectRes;
import com.info121.mapletree.models.RoundsDetails;
import com.info121.mapletree.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.ViewHolder> {
    private int lastPosition = -1;
    Context mContext;
    List<RoundsDetails> roundsDetailsList = new ArrayList<>();

    public RoundAdapter(Context context, List<RoundsDetails> roundsDetailsList) {
        this.mContext = context;
        this.roundsDetailsList = roundsDetailsList;
    }

    public void updateList(List<RoundsDetails> list) {
        lastPosition = -1;
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

        if (roundsDetailsList.get(i).getStatus().equalsIgnoreCase("OPEN")) {
            viewHolder.parent.setBackgroundResource(R.drawable.rounded_layout_green);
        }

        if (roundsDetailsList.get(i).getStatus().equalsIgnoreCase("CLOSE")) {
            viewHolder.parent.setBackgroundResource(R.drawable.rounded_layout_dark);
        }

        viewHolder.title.setText(roundsDetailsList.get(i).getDescription());
        viewHolder.info.setText(roundsDetailsList.get(i).getRoundinfo());

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LevelsActivity.class);
                intent.putExtra("ROUND", roundsDetailsList.get(i).getCode());
                intent.putExtra("NAME", roundsDetailsList.get(i).getDescription());

                if (roundsDetailsList.get(i).getStatus().equalsIgnoreCase("OPEN"))
                    mContext.startActivity(intent);
            }
        });


        final LinearLayout uncheckLayout = viewHolder.uncheck_levels;

        Call<ObjectRes> call = RestClient.MAPLE().getApiService().GetNotCheckedLevels(
                roundsDetailsList.get(i).getCode(),
                App.specialKey);

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {

                uncheckLayout.removeAllViews();

                if (response.body().getLevelNotCheckedDetails() != null)
                    for (int i = 0; i < response.body().getLevelNotCheckedDetails().size(); i++) {
                        RelativeLayout.LayoutParams tvParam = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);


                        TextView tv = new TextView(mContext);

                        tvParam.setMargins(0, 0, 16, 0);

                        tv.setLayoutParams(tvParam);
                        tv.setText(response.body().getLevelNotCheckedDetails().get(i).getLevel());
                        tv.setTextSize(16);
                        tv.setTypeface(null, Typeface.BOLD);
                        tv.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                        tv.setGravity(Gravity.CENTER);
                        tv.setPaddingRelative(20, 50, 20, 50);

                        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            tv.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_layout_orange));
                        } else {
                            tv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_layout_orange));
                        }

                        uncheckLayout.addView(tv);
                    }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.round_title)
        TextView title;

        @BindView(R.id.round_info)
        TextView info;

        @BindView(R.id.main_layout)
        LinearLayout parent;

        @BindView(R.id.layout_uncheck_levels)
        LinearLayout uncheck_levels;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
