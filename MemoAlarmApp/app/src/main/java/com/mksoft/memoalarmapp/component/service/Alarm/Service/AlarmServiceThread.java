package com.mksoft.memoalarmapp.component.service.Alarm.Service;

import android.os.Handler;

public class AlarmServiceThread extends Thread{

    Handler handler;
    boolean isRun = true;

    public AlarmServiceThread(Handler handler){
        this.handler = handler;
    }



    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
        //반복적으로 수행할 작업을 한다.
        while(isRun){
            handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
            try{
                Thread.sleep(5000); //5초 마다 쉬자
            }catch (Exception e) {}
        }
    }
}
