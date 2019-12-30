package com.info121.mapletree.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.info121.mapletree.R;
import com.info121.mapletree.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LevelsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.date)
    TextView mDate;

    @BindView(R.id.time)
    TextView mTime;

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
