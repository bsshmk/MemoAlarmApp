package com.mksoft.memoalarmapp.component.service.Alarm.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.mksoft.memoalarmapp.DB.MemoReposityDB;
import com.mksoft.memoalarmapp.DB.data.OptionData;
import com.mksoft.memoalarmapp.R;
import com.mksoft.memoalarmapp.DB.data.MemoData;
import com.mksoft.memoalarmapp.component.activity.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import dagger.android.AndroidInjection;

public class AlarmService extends Service {

    NotificationManager Notifi_M;
    AlarmServiceThread thread;
    Notification notification;
    Notification summary;

    private List<MemoData> memoDataList;
    SimpleDateFormat mFormat;
    String time;

    Intent intent;
    TaskStackBuilder stackBuilder;
    PendingIntent pendingIntent;
//testetset
    OptionData optionData;

    @Inject
    MemoReposityDB memoReposityDB;

    @Override
    public void onCreate() {
        super.onCreate();
        this.configureDagger();
    }

    private void configureDagger(){

        AndroidInjection.inject(this);//서비스 주입입니당~
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel("MemoCHID", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            Notifi_M.createNotificationChannel(notificationChannel);
        }//오레오 버젼 이상부터는 채널 처리



        summary = new NotificationCompat.Builder(getApplicationContext(), "MemoCHID")
                .setContentTitle("MemoAlarm")
                .setContentText("Check Memo")
                .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_event_black_24dp))
                .setAutoCancel(true)//다른 메모들이 다 취소되면 자동으로 취소
                .setGroup("MemoGroup")
                .setGroupSummary(true)//중요
                .build();
        //번들로 서머리에 메모 노티피케이션을 담자.
        mFormat=new SimpleDateFormat("yyMMddkkmm");//날짜 형식 지정

        myServiceHandler handler = new myServiceHandler();

        thread = new AlarmServiceThread(handler);
        thread.start();

        Log.d("test","fine");
        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업

    public void onDestroy() {
        //thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
        Intent its=new Intent(this, RestartService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(its);
        }
    }
    class myServiceHandler extends Handler {


        private boolean CheckComfort(int hour, int comfortA, int comfortB) {
            if (comfortA <= comfortB)
                return comfortA <= hour && hour < comfortB;
            else
                return comfortA <= hour || hour < comfortB;
        }

        public void checkNotify(MemoData memoData){
            String tempRandomTime = memoData.getRandomTime().substring(memoData.getRandomTime().length()-10,memoData.getRandomTime().length());
            Log.d("tempRT", tempRandomTime);
            Log.d("tempRTraw", memoData.getRandomTime());
            if(tempRandomTime.length() != 0){

                Calendar calendar = Calendar.getInstance();
                time=mFormat.format(calendar.getTime());

                Log.d("tempRT", time);
                if(tempRandomTime.equals(time))
                {
                    if(optionData != null){
                        if (CheckComfort(Integer.parseInt(time.substring(6,8)),
                                optionData.getSleepStartTime(), optionData.getSleepEndTime())){
                            Notifi_M.notify(memoData.getId(), notification);
                            Notifi_M.notify(0, summary);
                            memoData.setRandomTime(memoData.getRandomTime().substring(0, memoData.getRandomTime().length()-10));
                            //방해금지 설정이 되어있고, 방해금지 설정 시간에 들어가면 노티파이를 소리없이 뛰어줌
                        }else{
                            notification.defaults = Notification.DEFAULT_SOUND;
                            Log.d("testHandler", "pass~");
                            Notifi_M.notify(memoData.getId(), notification);
                            Notifi_M.notify(0, summary);
                            memoData.setRandomTime(memoData.getRandomTime().substring(0, memoData.getRandomTime().length()-10));
                        }//방해금지 설정이 되어있고, 방해금지 설정 시간에 들어가지 않는 경우

                    }else{
                        notification.defaults = Notification.DEFAULT_SOUND;
                        Log.d("testHandler", "pass~");
                        Notifi_M.notify(memoData.getId(), notification);
                        Notifi_M.notify(0, summary);
                        memoData.setRandomTime(memoData.getRandomTime().substring(0, memoData.getRandomTime().length()-10));
                    }//방해금지 설정이 되어있지 않음.


                }else{
                    Date current = null;
                    Date RT = null;
                    try {
                        current = mFormat.parse(time);
                        RT = mFormat.parse(tempRandomTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int compare = current.compareTo(RT);
                    if(compare>0){
                        memoData.setRandomTime(memoData.getRandomTime().substring(0, memoData.getRandomTime().length()-10));
                        //이미 지난 시간....
                    }else {
                        Log.d("tempRT", "finePass");
                    }
                }
                //startForeground(-1,null);
                //stopForeground(true);

                if(memoData.getRandomTime().length() == 0){
                    //디비에서 지우기
                    Log.d("DBdel", "it");
                    memoReposityDB.deleteMemo(memoData);
                }else{
                    memoReposityDB.insertMemo(memoData);
                    //스트링 값을 갱신한 메모데이터를 insert하자.
                }
            }
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void handleMessage(android.os.Message msg) {
            Log.d("testHandler", "running");
            memoDataList = memoReposityDB.getStaticMemoDataList();
            optionData = memoReposityDB.getOptionData();

            //Toast.makeText(getApplicationContext(),"제발",Toast.LENGTH_SHORT).show();

            // 알림 클릭시 앱 열기
            intent = new Intent(getApplicationContext(), MainActivity.class);
            stackBuilder = TaskStackBuilder.create(AlarmService.this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(intent);

            for(int i =0; i<memoDataList.size(); i++){
                MemoData memoData = memoDataList.get(i);

                pendingIntent = stackBuilder.getPendingIntent(memoData.getId(), PendingIntent.FLAG_UPDATE_CURRENT);

                notification = new NotificationCompat.Builder(getApplicationContext(), "MemoCHID")
                        .setContentTitle(memoData.getMemoTitle())
                        .setContentText(memoData.getMemoText())
                        .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_event_black_24dp))
                        .setAutoCancel(true)//클릭시 자동삭제
                        .setContentIntent(pendingIntent)//클릭시 app으로 이동
                        .setGroup("MemoGroup")
                        .build();



                checkNotify(memoData);

                //알림 소리를 한번만 내도록
                notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;

                //확인하면 자동으로 알림이 제거 되도록
                notification.flags = Notification.FLAG_AUTO_CANCEL;

            }

        }
    }

}
