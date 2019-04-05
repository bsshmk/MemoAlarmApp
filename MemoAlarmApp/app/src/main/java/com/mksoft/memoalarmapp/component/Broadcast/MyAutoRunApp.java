package com.mksoft.memoalarmapp.component.Broadcast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.mksoft.memoalarmapp.component.service.Alarm.Service.AlarmService;


public class MyAutoRunApp extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Toast.makeText(context,"Boot completed!!",Toast.LENGTH_SHORT).show();
            /*
            Intent it = new Intent(context, MainActivity.class);
            context.startActivity(it);
            */

            Intent its=new Intent(context,AlarmService.class);

            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
                context.startForegroundService(its);
            }
            else
                context.startService(its);
        }
    }
}
