package com.info121.mapletree.activities;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.info121.mapletree.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UnderMaintenanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_maintenance);

        ButterKnife.bind(this);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            ActivityCompat.finishAffinity(UnderMaintenanceActivity.this);
        }
    }


    @OnClick(R.id.exit)
    public void exitOnClick(){
        Intent intent = new Intent(getApplicationContext(), UnderMaintenanceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);

        ActivityCompat.finishAffinity(UnderMaintenanceActivity.this);
    }
}
