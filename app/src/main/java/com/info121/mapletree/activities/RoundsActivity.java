package com.info121.mapletree.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.mapletree.App;
import com.info121.mapletree.R;
import com.info121.mapletree.adapters.RoundAdapter;
import com.info121.mapletree.api.RestClient;
import com.info121.mapletree.models.ObjectRes;
import com.info121.mapletree.models.RoundsDetails;
import com.info121.mapletree.utils.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView(R.id.refresh)
    ImageView mRefresh;

    @BindView(R.id.date)
    TextView mDate;

    @BindView(R.id.time)
    TextView mTime;

    @BindView(R.id.login_as)
    TextView mLoginAs;

    @BindView(R.id.phone_no)
    TextView mPhoneNo;


    RoundAdapter roundAdapter;
    List<RoundsDetails> roundsDetailsList = new ArrayList<>();


    MenuItem mProfile, mLevels ;
    Animation animation;


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


        //mDrawerToggle.setDrawerIndicatorEnabled(false);
        //mDrawerToggle.setHomeAsUpIndicator(R.mipmap.ic_drawer);

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


//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("ddd, dd MM yyyy");
//        String formattedDate = df.format(c.getTime());
//
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
        TextView mLastLogin = mNavigationView.getHeaderView(0).findViewById(R.id.last_login);
        ImageView mLogout = mNavigationView.getHeaderView(0).findViewById(R.id.logout);

        mUserName.setText(App.userName);
        mLastLogin.setText(App.lastLogin);


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


        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(RoundsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // refresh animation
        animation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(500);


        //mNavigationView.setItemIconTintList(null);

        // set login info
        mLoginAs.setText("Welcome " + App.userName);
        //  mLastLogin.setText("Last Login : " + App.lastLogin);
    }


    @OnClick(R.id.phone_no)
    public void phoneOnClick() {
        callPhone();
    }

    @OnClick(R.id.phone_icon)
    public void phoneIconClick() {
        callPhone();
    }

    private void callPhone() {
        String phoneNo = "6848 2791";
        Uri number = Uri.parse("tel:" + phoneNo);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);

        Toast.makeText(mContext, "Phone Call .... to  " + phoneNo, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefresh.startAnimation(animation);
        callGetRoundsApi();
    }

    @OnClick(R.id.refresh)
    public void refreshOnClick() {
        mRefresh.startAnimation(animation);
        callGetRoundsApi();
    }

    private void showTime() {
      //  mDate.setText(Util.convertDateToString(Calendar.getInstance().getTime(), "EEE, dd MMM yyyy") + ", ");
        String dateString = Util.convertDateToString(Calendar.getInstance().getTime(), "EEE dd MMM yyyy hh:mma");

        mTime.setText(dateString.toString());
    }

    private void callGetRoundsApi() {

        Call<ObjectRes> call = RestClient.MAPLE().getApiService().GetRounds(Util.convertToSpecial(mContext));

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                roundsDetailsList = (List<RoundsDetails>) response.body().getRoundsDetails();

                if (response.body().getResponsemessage().equalsIgnoreCase("Success")) {
                    roundAdapter.updateList(roundsDetailsList);
                    roundAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "Error in getting round list", Toast.LENGTH_SHORT).show();
                }

                clearRefreshAnimation();
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Toast.makeText(mContext, "Getting round list failed", Toast.LENGTH_SHORT).show();
                clearRefreshAnimation();
            }
        });
    }


    private void clearRefreshAnimation() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefresh.clearAnimation();
            }
        }, 500);

    }


}
