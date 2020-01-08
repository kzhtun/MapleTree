package com.info121.mapletree.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.mapletree.AbstractActivity;
import com.info121.mapletree.App;
import com.info121.mapletree.R;
import com.info121.mapletree.api.RestClient;
import com.info121.mapletree.models.ObjectRes;
import com.info121.mapletree.services.SmartLocationService;
import com.info121.mapletree.utils.PrefDB;
import com.info121.mapletree.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AbstractActivity {

    String TAG = this.getClass().getSimpleName();

    Context mContext = LoginActivity.this;
    PrefDB prefDB;

    @BindView(R.id.user_name)
    EditText mUserName;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.remember_me)
    CheckBox mRemember;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.api_version)
    TextView mApiVersion;

    @BindView(R.id.ui_version)
    TextView mUiVersion;

    String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        prefDB = new PrefDB(getApplicationContext());

        if (prefDB.getBoolean(App.CONST_REMEMBER_ME)) {
            mUserName.setText(prefDB.getString(App.CONST_USER_NAME));
            mRemember.setChecked(true);
        }

        callCheckVersion();

        mApiVersion.setText("Api " + Util.getVersionCode(mContext));
        mUiVersion.setText("Ver " + Util.getVersionName(mContext));

        mPassword.setText("info121");
    }


    @OnClick(R.id.login)
    public void loginOnClick() {
        if (mUserName.getText().length() == 0 ) {
            mUserName.setError("User name required.");
            mUserName.requestFocus();
        }

        if (mPassword.getText().length() == 0) {
            mPassword.setError("Password required.");
            mPassword.requestFocus();
        }

        if (mUserName.getText().length() >  0 && mPassword.getText().length() > 0 ) {
            mProgressBar.setVisibility(View.VISIBLE);
            callValidateUser();
        }
    }

    private void callValidateUser() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        todayDate = df.format(c.getTime());


        Call<ObjectRes> call = RestClient.MAPLE().getApiService().ValidateUser(
                mUserName.getText().toString().trim(),
                mPassword.getText().toString().trim(),
                "info121" + todayDate
        );

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response.body().getResponsemessage().equalsIgnoreCase("Valid")) {

                    App.userName = mUserName.getText().toString().trim();
                    App.authToken = response.body().getToken();

                    loginSuccessful();

                } else {
                    mUserName.setError("Invalid user name or password.");
                    mUserName.requestFocus();
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {

            }
        });


    }

    private void callCheckVersion() {
//        Call<ObjectRes> call = RestClient.COACH().getApiService().CheckVersion(String.valueOf(Util.getVersionCode(mContext)));
//
//        call.enqueue(new Callback<ObjectRes>() {
//            @Override
//            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
//                if(response.body().getResponsemessage().equalsIgnoreCase("OUTDATED")) {
//                    showOutdatedDialog();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ObjectRes> call, Throwable t) {
//
//            }
//        });
    }

    private void callUpdateDevice() {
        Log.e("====", "=========================================");
        Log.e("DEVICE ID: ", Util.getDeviceID(getApplicationContext()));
        Log.e("DEVICE TYPE ", App.DEVICE_TYPE);
        Log.e("FCM TOKEN", App.FCM_TOKEN);
        Log.e("====", "=========================================");


    }

    private void loginSuccessful() {
        mProgressBar.setVisibility(View.GONE);


        // instantiate wiht new Token
        //RestClient.Dismiss();


        prefDB.putString(App.CONST_USER_NAME, App.userName);
        prefDB.putString(App.CONST_DEVICE_ID, App.deviceID);
        prefDB.putLong(App.CONST_TIMER_DELAY, App.timerDelay);

        // location
        //startLocationService();

        if (mRemember.isChecked())
            prefDB.putBoolean(App.CONST_REMEMBER_ME, true);
        else
            prefDB.putBoolean(App.CONST_REMEMBER_ME, false);

        Log.e(TAG, "Login Successful");

        // login successful
        startActivity(new Intent(LoginActivity.this, RoundsActivity.class));
    }

    private void startLocationService() {
        if (isGPSEnabled()) {
            Intent serviceIntent = new Intent(LoginActivity.this, SmartLocationService.class);
            LoginActivity.this.startService(serviceIntent);
        }
    }

    private boolean isGPSEnabled() {

        mContext = LoginActivity.this;

        final LocationManager manager = (LocationManager) getSystemService(mContext.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

            alertDialog.setTitle("GPS Settings");
            alertDialog.setMessage("Your GPS/Location service is off. \n Do you want to turn on location service?");

            // On pressing Settings button
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(intent);
                }
            });

            // on pressing cancel button
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();

            return false;
        } else
            return true;
    }


    private void showOutdatedDialog() {
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(R.string.AppName)
                .setMessage(R.string.message_version_outdated)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishAffinity();
                    }
                })
                .setNegativeButton("Go to Play Store", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                })
                .create();


        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
