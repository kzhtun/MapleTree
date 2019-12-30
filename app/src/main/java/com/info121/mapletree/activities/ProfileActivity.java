package com.info121.mapletree.activities;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.mapletree.App;
import com.info121.mapletree.R;
import com.info121.mapletree.api.RestClient;
import com.info121.mapletree.models.ObjectRes;
import com.info121.mapletree.models.ProfileDetails;
import com.info121.mapletree.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    Context mContext = ProfileActivity.this;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.date)
    TextView mDate;

    @BindView(R.id.time)
    TextView mTime;


    @BindView(R.id.name)
    TextView mDisplayName;

    @BindView(R.id.phone_no)
    TextView mPhoneNo;

    @BindView(R.id.user_name)
    TextView mUserName;

    @BindView(R.id.old_password)
    TextView mOldPassword;

    @BindView(R.id.new_password)
    TextView mNewPassword;

    @BindView(R.id.confirm_password)
    TextView mConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);


        // set toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


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

        callGetRoundsApi();


    }

    private void showTime() {
        String dateString = Util.convertDateToString(Calendar.getInstance().getTime(), "hh:mm a");
        mTime.setText(dateString.toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void callGetRoundsApi() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String todayDate = df.format(c.getTime());

        Call<ObjectRes> call = RestClient.MAPLE().getApiService().GetUserProfile(App.userName,"info121" + todayDate);

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                if (response.body().getResponsemessage().equalsIgnoreCase("Success")) {
                    displayProfile(response.body().getProfileDetails());
                } else {
                    Toast.makeText(mContext, "Error in getting profile detial", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Toast.makeText(mContext, "Getting profile detail failed", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void displayProfile(ProfileDetails profileDetails){
        mDisplayName.setText(profileDetails.getDisplayname());
        mPhoneNo.setText(profileDetails.getTelno());
        mUserName.setText(profileDetails.getUsername());
        mOldPassword.setText(profileDetails.getPassword());

    }
}
