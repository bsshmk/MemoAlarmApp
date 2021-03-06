package com.mksoft.memoalarmapp.component.service.Alarm;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class RandomTimeMaker {

    int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public String Randomize(String deadline, int deadLineHour, int deadLineMin,
                                        String startingTime, int min_interval) {
        // 알람이 울리는 연도, 달, 날짜, 시간, 분
        StringBuilder timeList = new StringBuilder();

        String sTime = startingTime;
        String nowYear = sTime.substring(0, 2);
        String nowMonth = sTime.substring(3, 5);
        String nowDay = sTime.substring(6, 8);
        String nowHour = sTime.substring(9, 11);
        String nowMinute = sTime.substring(12, 14);
        int now_year = Integer.parseInt(nowYear);
        int now_month = Integer.parseInt(nowMonth);
        int now_day = Integer.parseInt(nowDay);
        int hour = Integer.parseInt(nowHour);
        int min = Integer.parseInt(nowMinute);
        int limit_year = Integer.parseInt(deadline.substring(0,2));
        int limit_month = Integer.parseInt(deadline.substring(3,5));
        int limit_day = Integer.parseInt(deadline.substring(6,8));
        int interval;

        StringBuilder str = new StringBuilder();
        while (now_year <= limit_year) {

            interval = MakeInterval(min_interval);
            min += interval;
            if (min >= 60) {
                hour += min / 60;
                min %= 60;
            }
            if(hour == 24){
                now_day += hour / 24;
            }//시간의 시작은 24시부터...
            if (hour > 24) {
                hour %= 24;
            }
            // 간격의 최대값(interval*2)이 31일을 넘지 않게 해야 함
            // 윤년 처리
            if (now_year % 4 == 0) {
                if (now_month == 2) {
                    if (now_day > 29) {
                        now_day %= 29;
                        now_month++;
                    }
                }
            } else {
                if (now_day > days[now_month]) {
                    now_day %= days[now_month];
                    now_month++;
                }
            }
            if (now_month > 12) {
                now_month %= 12;
                now_year++;
            }

            if (now_year == limit_year) {
                if (now_month > limit_month) break;
                else if (now_month == limit_month) {
                    if (now_day > limit_day) break;
                    else if(now_day == limit_day){
                        if(hour > deadLineHour - 1) break;
                        else if(min > deadLineMin) break;
                    }
                }
            }

            // 알람 금지 시간
            str.setLength(0);

            str.append(String.format("%02d", now_year)  + String.format("%02d", now_month)  + String.format("%02d", now_day)
                    + String.format("%02d", hour)  + String.format("%02d", min));

            //Log.d("timeString", str.toString());
            timeList = str.append(timeList);
        }

        StringBuilder dday = new StringBuilder();

        // 1시간 전에 울려주기
        dday.append(String.format("%02d", limit_year)  + String.format("%02d", limit_month)  + String.format("%02d", limit_day)
                + String.format("%02d", deadLineHour - 1)  + String.format("%02d", deadLineMin));

        timeList = dday.append(timeList);
        //Log.d("timeString", dday.toString());

        dday.setLength(0);
        // 해당 시간에 울려주기
        dday.append(String.format("%02d", limit_year)  + String.format("%02d", limit_month)  + String.format("%02d", limit_day)
                + String.format("%02d", deadLineHour)  + String.format("%02d", deadLineMin));

        timeList = dday.append(timeList);
        //Log.d("timeString", dday.toString());

        dday.setLength(0);
        dday.append(String.format("%02d", limit_year)  + String.format("%02d", limit_month)  + String.format("%02d", limit_day)
                + "23"  + "59");

        timeList = dday.append(timeList);
        //Log.d("timeString", dday.toString());

        return timeList.toString();
    }

    private int MakeInterval(int min_interval) {
        double rand = Math.random();
        int interval = (int) (Math.abs(rand * (min_interval + 1)) + min_interval);
        return interval;
    }
}