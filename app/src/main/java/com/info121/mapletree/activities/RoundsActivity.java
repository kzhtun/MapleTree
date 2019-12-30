package com.info121.mapletree.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.mapletree.App;
import com.info121.mapletree.R;
import com.info121.mapletree.adapters.RoundAdapter;
import com.info121.mapletree.api.RestClient;
import com.info121.mapletree.models.ObjectRes;
import com.info121.mapletree.models.RoundsDetails;
import com.info121.mapletree.utils.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoundsActivity extends AppCompatActivity {
    Context mContext = RoundsActivity.this;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.drawer)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rv_round)
    RecyclerView mRecyclerView;

    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout mSwipeLayout;

    @BindView(R.id.date)
    TextView mDate;

    @BindView(R.id.time)
    TextView mTime;

    RoundAdapter roundAdapter;
    List<RoundsDetails> roundsDetailsList = new ArrayList<>();


    MenuItem mProfile, mLevels, mLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rounds);

        ButterKnife.bind(this);


        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        mDrawerToggle.syncState();


        mDrawerToggle.setDrawerIndicatorEnabled(false);

        mDrawerToggle.setHomeAsUpIndicator(R.mipmap.ic_drawer);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });


        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        roundAdapter = new RoundAdapter(mContext, roundsDetailsList);
        mRecyclerView.setAdapter(roundAdapter);

        callGetRoundsApi();

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callGetRoundsApi();
            }
        });

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddd, dd MM yyyy");
        String formattedDate = df.format(c.getTime());
        showTime();

        final Handler timer = new Handler(getMainLooper());
        timer.postDelayed(new Runnable() {
            @Override
            public void run() {

                showTime();
                timer.postDelayed(this, 60000);
            }
        }, 60000);

        TextView mUserName = mNavigationView.getHeaderView(0).findViewById(R.id.user_name);

        mUserName.setText(App.userName);


        mProfile = mNavigationView.getMenu().findItem(R.id.profile);
        mProfile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(RoundsActivity.this, ProfileActivity.class);
                startActivity(intent);
                return false;
            }
        });


        mLevels = mNavigationView.getMenu().findItem(R.id.open_close);
        mLevels.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(RoundsActivity.this, LevelsActivity.class);
                startActivity(intent);
                return false;
            }
        });

    }

    private void showTime() {
        String dateString = Util.convertDateToString(Calendar.getInstance().getTime(), "hh:mm a");
        mTime.setText(dateString.toString());
    }

    private void callGetRoundsApi() {
        Call<ObjectRes> call = RestClient.MAPLE().getApiService().GetRounds();

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                mSwipeLayout.setRefreshing(false);
                roundsDetailsList = (List<RoundsDetails>) response.body().getRoundsDetails();

                if (response.body().getResponsemessage().equalsIgnoreCase("Success")) {
                    roundAdapter.updateList(roundsDetailsList);
                    roundAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "Error in getting round list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                mSwipeLayout.setRefreshing(false);
                Toast.makeText(mContext, "Getting round list failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
