package com.info121.delgro.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.info121.delgro.AbstractActivity;
import com.info121.delgro.App;
import com.info121.delgro.R;
import com.info121.delgro.adapters.JobDetailPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobDetailActivity extends AbstractActivity {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        ButterKnife.bind(this);

        // set toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Welcome " + App.userName);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();

        String jobNo = intent.getStringExtra("jobNo");
        int index = intent.getIntExtra("index", 0);

        // set view pager
        JobDetailPageAdapter pageAdapter  = new JobDetailPageAdapter(getSupportFragmentManager(), App.jobList);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setCurrentItem(index);

        //mTabLayout.setupWithViewPager(mViewPager);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle Action bar item clicks here. The Action bar will
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
