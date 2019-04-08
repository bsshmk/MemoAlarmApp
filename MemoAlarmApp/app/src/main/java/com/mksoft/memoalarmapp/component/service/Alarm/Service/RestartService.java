package com.mksoft.memoalarmapp.component.service.Alarm.Service;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.mksoft.memoalarmapp.R;

import androidx.core.app.NotificationCompat;

public class RestartService extends Service {

    NotificationCompat.Builder notificationFake = null;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private NotificationCompat.Builder makeFakeNotification(){
        if(notificationFake != null)
            return notificationFake;
        String fakeCH = "-1";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(fakeCH, "-1", NotificationManager
                    .IMPORTANCE_DEFAULT);
            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            notificationFake = new NotificationCompat.Builder(RestartService.this, fakeCH);
        }
        notificationFake.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("fake")
                .setContentTitle("fake");
        return notificationFake;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        startForeground(-1,makeFakeNotification().build());
        Intent in = new Intent(this, AlarmService.class);
        startService(in);

        stopForeground(true);
        stopSelf();

        return START_NOT_STICKY;
    }

}