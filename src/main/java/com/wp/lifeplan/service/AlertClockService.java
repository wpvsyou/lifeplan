package com.wp.lifeplan.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.wp.lifeplan.R;
import com.wp.lifeplan.ui.BuzzActivity;
import com.wp.lifeplan.ui.LpActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangpeng on 2017/5/28.
 */

public class AlertClockService extends Service {
    private final static String TAG = "AlertClockService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final static String START_ALERT_CLOCK_SERVICE = "start_alert_clock_service";
    private final static String STOP_ALERT_CLOCK_SERVICE = "stop_alert_clock_service";
    private final static String UPDATE_ALERT_CLOCK_SERVICE = "update_alert_clock_service";
    private final static String ALARM_ACTION = "com.wp.lifeplan.ALARM_ACTION";
    private final static String EXTRA_NAME = "extra_name";
    private final static String EXTRA_NOTIFY_FREQUENCY = "extra_notify_frequency";
    private final static String EXTRA_IS_ADD = "extra_is_add";
    private final static String ACTION_MODIFY_ALARM = "com.wp.lifeplan.ACTION_MODIFY_ALARM";
    public final static String ACTION_MDM_STOP_BUZZ = "com.wp.lifeplan.ACTION_MDM_STOP_BUZZ";
    private final static HashMap<String, PendingIntent> ALARM_CLOCKS = new HashMap<>();
    private final static long ONE_DAY = 1000 * 60 * 60 * 24;

    public static void addAlarmClock(Context ctx, String name, long notifyFrequency) {
        Intent i = new Intent(ACTION_MODIFY_ALARM);
        i.setPackage(ctx.getPackageName());
        i.putExtra(EXTRA_NAME, name);
        i.putExtra(EXTRA_NOTIFY_FREQUENCY, notifyFrequency);
        i.putExtra(EXTRA_IS_ADD, true);
        ctx.startService(i);
    }

    public static void stopBuzz(Context ctx) {
        Intent i = new Intent(ACTION_MDM_STOP_BUZZ);
        i.setPackage(ctx.getPackageName());
        ctx.startService(i);
    }

    public static void stopAlarmClock(Context ctx, String name) {
        Intent i = new Intent(ACTION_MODIFY_ALARM);
        i.setPackage(ctx.getPackageName());
        i.putExtra(EXTRA_NAME, name);
        i.putExtra(EXTRA_IS_ADD, false);
        ctx.startService(i);
    }

    public static void startAlertClockService(Context context) {
        Intent i = new Intent(START_ALERT_CLOCK_SERVICE);
        i.setPackage(context.getPackageName());
        context.startService(i);
    }

    public static void stopAlertClockService(Context context) {
        Intent i = new Intent(STOP_ALERT_CLOCK_SERVICE);
        i.setPackage(context.getPackageName());
        context.startService(i);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMediaPlayer = MediaPlayer.create(this, R.raw.buzz);
    }

    private AlarmManager mAlarmManager;
    private AudioManager mAudioManager;
    private MediaPlayer mMediaPlayer;

    private void stopBuzz() {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;

        Intent intent = new Intent(ACTION_MDM_STOP_BUZZ);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
            switch (intent.getAction()) {
                case ACTION_MDM_STOP_BUZZ:
                    stopBuzz();
                    break;
                case START_ALERT_CLOCK_SERVICE:

                    break;
                case STOP_ALERT_CLOCK_SERVICE:
                    stopForeground(true);
                    break;
                case UPDATE_ALERT_CLOCK_SERVICE:

                    break;
                case ALARM_ACTION:
                    String key = intent.getStringExtra(EXTRA_NAME);
                    Log.i(TAG, String.format("Action [%s], key[%s]", intent.getAction(), key));
                    synchronized (ALARM_CLOCKS) {
                        PendingIntent pi = ALARM_CLOCKS.get(key);
                        mAlarmManager.cancel(pi);
                        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, ONE_DAY, pi);
                    }

                    //Show UI
                    Intent notifyUi = new Intent(this, BuzzActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(notifyUi);

                    //Play sound
                    mAudioManager.setStreamVolume(3, mAudioManager.
                            getStreamMaxVolume(3), 0);

                    mMediaPlayer.setLooping(true);
                    mMediaPlayer.setVolume(1.0F, 1.0F);
                    mMediaPlayer.start();
                    break;
                case ACTION_MODIFY_ALARM:
                    String name = intent.getStringExtra(EXTRA_NAME);
                    boolean isAdd = intent.getBooleanExtra(EXTRA_IS_ADD, true);
                    long notifyFrequency = intent.getLongExtra(EXTRA_NOTIFY_FREQUENCY,
                            10000);

                    if (isAdd) {
                        synchronized (ALARM_CLOCKS) {
                            if (ALARM_CLOCKS.isEmpty()) {
                                Intent i = new Intent(getApplicationContext(), LpActivity.class);
                                i.setPackage(getPackageName());
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),
                                        0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                                Notification.Builder builder = new Notification.Builder(this);
                                builder.setContentIntent(pi);
                                builder.setSmallIcon(R.mipmap.ic_launcher);
                                builder.setTicker("Foreground Service Start");
                                builder.setContentTitle("Foreground Service");
                                builder.setContentText("Make this service run in the foreground.");
                                Notification notification = builder.build();
                                startForeground(0x001, notification);
                            }

                            PendingIntent originPi = ALARM_CLOCKS.get(name);
                            if (originPi != null) {
                                mAlarmManager.cancel(originPi);
                            }
                            ALARM_CLOCKS.remove(name);

                            Intent i = new Intent(ALARM_ACTION);
                            i.setPackage(getPackageName());
                            i.putExtra(EXTRA_NAME, name);
                            PendingIntent pi = PendingIntent.getService(getApplicationContext(),
                                    0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                            long time = SystemClock.elapsedRealtime() + notifyFrequency;
                            mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, time,
                                    pi);
                            ALARM_CLOCKS.put(name, pi);
                        }

                    } else {
                        synchronized (ALARM_CLOCKS) {
                            PendingIntent pendingIntent = ALARM_CLOCKS.get(name);
                            if (pendingIntent != null) {
                                mAlarmManager.cancel(pendingIntent);
                                ALARM_CLOCKS.remove(name);
                            }

                            if (ALARM_CLOCKS.isEmpty()) {
                                stopForeground(true);
                            }
                        }
                    }
                    break;
            }
        }
        return START_STICKY;
    }
}
