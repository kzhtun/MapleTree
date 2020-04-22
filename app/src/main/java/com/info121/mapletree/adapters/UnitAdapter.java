package com.info121.mapletree.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.info121.mapletree.R;
import com.info121.mapletree.activities.LevelsActivity;
import com.info121.mapletree.models.RoundsDetails;
import com.info121.mapletree.models.UnitDetail;
import com.info121.mapletree.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.ViewHolder> {

    int[][] states = new int[][]{
            new int[]{-android.R.attr.state_checked},
            new int[]{android.R.attr.state_checked},
    };

    int[] thumbColors = new int[]{
            Color.parseColor("#FF1400"),
            Color.parseColor("#25A200")
    };

    int[] trackColors = new int[]{
            Color.parseColor("#55FF1400"),
            Color.parseColor("#5525A200")
    };

    private int lastPosition = -1;
    Context mContext;
    int mScreenSize;
    static List<UnitDetail> unitDetailList = new ArrayList<>();
    // List<Boolean> shopStatusList = new ArrayList<>();

    SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public UnitAdapter(Context context, List<UnitDetail> unitDetailList, int screenSize) {
        this.mContext = context;
        this.unitDetailList = unitDetailList;
        this.mScreenSize = screenSize;
    }

    public void updateList(List<UnitDetail> list) {
        lastPosition = -1;
        unitDetailList = list;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View promotionView;
        // Inflate the custom layout
        if (mScreenSize == 0)
            promotionView = inflater.inflate(R.layout.cell_unit, parent, false);
        else
            promotionView = inflater.inflate(R.layout.cell_unit_tablet, parent, false);

        // Return a new holder instance
        return new ViewHolder(promotionView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        setAnimation(viewHolder.itemView, i);

        DrawableCompat.setTintList(DrawableCompat.wrap(viewHolder.shopSwitch.getThumbDrawable()), new ColorStateList(states, thumbColors));
        DrawableCompat.setTintList(DrawableCompat.wrap(viewHolder.shopSwitch.getTrackDrawable()), new ColorStateList(states, trackColors));

        viewHolder.srNo.setText(i + 1 + "");
        viewHolder.unitNo.setText("#" + unitDetailList.get(i).getLevel() + "-" + unitDetailList.get(i).getUnit());
        viewHolder.shopName.setText(unitDetailList.get(i).getName());

        if (unitDetailList.get(i).getStatus().equalsIgnoreCase("OPEN")) {
            viewHolder.shopSwitch.setChecked(true);
            viewHolder.shopStatus.setText("OPEN");
            viewHolder.shopStatus.setTextColor(Color.parseColor("#3d8c00"));
        } else {
            viewHolder.shopSwitch.setChecked(false);
            viewHolder.shopStatus.setText("CLOSE");
            viewHolder.shopStatus.setTextColor(Color.parseColor("#FF1400"));
        }

        //       itemStateArray.put(i, (unitDetailList.get(i).getStatus().equalsIgnoreCase("OPEN"))? true : false);


//        //if(itemStateArray.get(i)){
//        if (unitDetailList.get(i).getStatus().equalsIgnoreCase("CLOSE")) {
//            viewHolder.shopSwitch.setChecked(false);
//            viewHolder.shopStatus.setText("CLOSE");
//            viewHolder.shopStatus.setTextColor(mContext.getResources().getColor(R.color.reject));
//        } else {
//            viewHolder.shopSwitch.setChecked(true);
//            viewHolder.shopStatus.setText("OPEN");
//            viewHolder.shopStatus.setTextColor(mContext.getResources().getColor(R.color.dark_green));
//        }


//        viewHolder.shopSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b) {
//                    unitDetailList.get(i).setStatus("OPEN");
//                    viewHolder.shopStatus.setTextColor(mContext.getResources().getColor(R.color.dark_green));
//                }else {
//                    unitDetailList.get(i).setStatus("CLOSE");
//                    viewHolder.shopStatus.setTextColor(mContext.getResources().getColor(R.color.reject));
//                }
//
//                viewHolder.shopStatus.setText( unitDetailList.get(i).getStatus());
//            }
//        });


        if (unitDetailList.get(i).getProgress() != null) {

            if (unitDetailList.get(i).getProgress().equalsIgnoreCase("P")) {
                viewHolder.success.setVisibility(View.GONE);
                viewHolder.failed.setVisibility(View.GONE);
                viewHolder.progressBar.setVisibility(View.VISIBLE);
            }

            if (unitDetailList.get(i).getProgress().equalsIgnoreCase("S")) {
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.success.setVisibility(View.VISIBLE);
            }

            if (unitDetailList.get(i).getProgress().equalsIgnoreCase("F")) {
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.failed.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.success.setVisibility(View.GONE);
            viewHolder.failed.setVisibility(View.GONE);
            viewHolder.progressBar.setVisibility(View.GONE);
        }

        if(unitDetailList.get(i).getShopreason().length() > 0)
        viewHolder.reason.setText(unitDetailList.get(i).getShopreason());
        else
            viewHolder.reason.setVisibility(View.GONE);

        if (unitDetailList.get(i).getShowremarks().equals("0")) {
           // viewHolder.layoutRemark.setVisibility(View.GONE);
            viewHolder.remark.setVisibility(View.GONE);
        } else {
           // viewHolder.layoutRemark.setVisibility(View.VISIBLE);
            viewHolder.remark.setVisibility(View.VISIBLE);


            viewHolder.remark.setText(unitDetailList.get(i).getRemarks());
        }

    }

    @Override
    public int getItemCount() {
        return (unitDetailList == null) ? 0 : unitDetailList.size();
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

        @BindView(R.id.main_layout)
        LinearLayout parent;

        @BindView(R.id.sr_no)
        TextView srNo;

        @BindView(R.id.unit_no)
        TextView unitNo;

        @BindView(R.id.shop_name)
        TextView shopName;

        @BindView(R.id.shop_status)
        TextView shopStatus;

        @BindView(R.id.shop_switch)
        SwitchCompat shopSwitch;

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        @BindView(R.id.success)
        ImageView success;

        @BindView(R.id.failed)
        ImageView failed;

        @BindView(R.id.shop_reason)
        TextView reason;

        @BindView(R.id.shop_remark)
        TextView remark;

        @BindView(R.id.layout_remark)
        LinearLayout layoutRemark;

        CheckedChangeListener checkChangeListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.setIsRecyclable(false);


            shopSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (b) {
                        unitDetailList.get(getAdapterPosition()).setStatus("OPEN");
                        shopStatus.setText("OPEN");
                        shopStatus.setTextColor(Color.parseColor("#3d8c00"));
                    } else {
                        unitDetailList.get(getAdapterPosition()).setStatus("CLOSE");
                        shopStatus.setText("CLOSE");
                        shopStatus.setTextColor(Color.parseColor("#FF1400"));
                    }
                }
            });
        }


    }

}
