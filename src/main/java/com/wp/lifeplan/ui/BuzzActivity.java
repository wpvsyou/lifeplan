package com.wp.lifeplan.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.wp.lifeplan.R;
import com.wp.lifeplan.service.AlertClockService;

public class BuzzActivity extends Activity {

    private Animation mMusicBellAnimation;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && AlertClockService.ACTION_MDM_STOP_BUZZ.endsWith(intent.getAction())) {
                finish();
            }
        }
    };

    private final static String EXTRA_MSG = "extra_msg";
    public static void startBuzz(Context ctx, String msg) {
        Intent notifyUi = new Intent(ctx, BuzzActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notifyUi.putExtra(EXTRA_MSG, msg);
        ctx.startActivity(notifyUi);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzz);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopbuzzing();
                finish();
            }
        });
        ((TextView) findViewById(R.id.imageView)).setText(getIntent().getStringExtra(EXTRA_MSG));
        mMusicBellAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_center_clockwise);
        mMusicBellAnimation.setInterpolator(this, android.R.anim.linear_interpolator);
        findViewById(R.id.bell).startAnimation(mMusicBellAnimation);
        IntentFilter filter = new IntentFilter();
        filter.addAction(AlertClockService.ACTION_MDM_STOP_BUZZ);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopbuzzing();
        finish();
    }

    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra("STOP_BUZZ", false) == true) {
            finish();
        }
    }

    public void stopbuzzing() {
        AlertClockService.stopBuzz(BuzzActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(mReceiver);
        } catch (Exception e) {
        }

    }
}
