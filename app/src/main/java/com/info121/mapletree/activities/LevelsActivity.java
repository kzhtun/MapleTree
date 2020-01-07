package com.info121.mapletree.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.mapletree.App;
import com.info121.mapletree.R;
import com.info121.mapletree.adapters.RoundAdapter;
import com.info121.mapletree.adapters.UnitAdapter;
import com.info121.mapletree.api.RestClient;
import com.info121.mapletree.models.LevelDetail;
import com.info121.mapletree.models.ObjectRes;
import com.info121.mapletree.models.RoundsDetails;
import com.info121.mapletree.models.UnitDetail;
import com.info121.mapletree.utils.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LevelsActivity extends AppCompatActivity {
    Context mContext = LevelsActivity.this;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rv_round)
    RecyclerView mRecyclerView;

    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout mSwipeLayout;

    @BindView(R.id.sub_title)
    TextView mSubTitle;

    @BindView(R.id.date)
    TextView mDate;

    @BindView(R.id.time)
    TextView mTime;

    @BindView(R.id.shop_count)
    TextView mShopCount;

    @BindView(R.id.level)
    Spinner mLevel;

    @BindView(R.id.update)
    Button mUpdate;

    UnitAdapter unitAdapter;
    List<UnitDetail> unitDetailList = new ArrayList<>();

    String mRoundCode = "";
    String mRoundName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

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


        Intent intent = getIntent();
        mRoundCode = intent.getStringExtra("ROUND");
        mRoundName = intent.getStringExtra("NAME");

        mSubTitle.setText(mRoundName);

        callGetLevels(mRoundCode);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));




        unitAdapter = new UnitAdapter(mContext, unitDetailList);
        mRecyclerView.setAdapter(unitAdapter);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callGetUnits(mLevel.getSelectedItem().toString(), mRoundCode);
            }
        });

    }


    @OnClick(R.id.update)
    public void updateOnClick() {

        for (int i = 0; i < unitDetailList.size(); i++) {
            unitDetailList.get(i).setProgress("P");
            unitAdapter.notifyDataSetChanged();
        }

        for (int i = 0; i < unitDetailList.size(); i++) {

            final int j = i;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callUpdateUnits(unitDetailList.get(j), j);
                }
            }, 500);

        }
    }

    private void callUpdateUnits(final UnitDetail unit, final int index) {
//        1. roundcode
//        2. block
//        3. level
//        4. unit
//        5. tennantcode
//        6. tennantname
//        7. status

        Call<ObjectRes> call = RestClient.MAPLE().getApiService().SaveUnits(mRoundCode,
                unit.getBlock(),
                unit.getLevel(),
                unit.getUnit(),
                unit.getCode(),
                unit.getName(),
                unit.getStatus());



        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                Log.e("Update ", unit.getName() + " ----OK ");
                unitDetailList.get(index).setProgress("S");
                unitAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Log.e("Update ", unit.getName() + " ---- Fail ");
                unitDetailList.get(index).setProgress("F");
                unitAdapter.notifyDataSetChanged();
            }
        });

    }


    private void callGetLevels(final String round) {
        Call<ObjectRes> call = RestClient.MAPLE().getApiService().GetLevels();

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                if (response.body().getResponsemessage().equalsIgnoreCase("Success")) {

                    List<String> levels = new ArrayList<>();
                    levels.add(" -- ");

                    for (int i = 0; i < response.body().getLevelDetails().size(); i++) {
                        levels.add(response.body().getLevelDetails().get(i).getLevel());
                    }


                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, levels);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    mLevel.setAdapter(dataAdapter);

                    mLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (!mLevel.getSelectedItem().toString().equalsIgnoreCase("-"))
                                callGetUnits(mLevel.getSelectedItem().toString(), round);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } else {
                    Toast.makeText(mContext, "Error in getting profile detial", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {

            }
        });
    }


    private void callGetUnits(String level, String code) {
        mSwipeLayout.setRefreshing(true);
        Call<ObjectRes> call = RestClient.MAPLE().getApiService().GetUnits(level, code);

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                mSwipeLayout.setRefreshing(false);
                unitDetailList = (List<UnitDetail>) response.body().getUnitDetails();

                if (response.body().getResponsemessage().equalsIgnoreCase("Success")) {

                    mShopCount.setText(unitDetailList.size() + "");

                    unitAdapter.updateList(unitDetailList);
                    unitAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                mSwipeLayout.setRefreshing(false);
            }
        });


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

}