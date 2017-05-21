package com.fuhai.mobile.fuhai.utils;

import java.util.Timer;
import java.util.TimerTask;

public class CustomTimer {
    private Timer timer = new Timer();
    private long  msecond =5;//默认5秒

    public void setTimes(long msecond) {
        this.msecond = msecond;
  
    }  
  private TimerTask timerTask;
    public void beginRun() {
        timer.schedule(timerTask = new TimerTask() {
            @Override
            public void run() {
                if(null !=timerListener){
                    timerListener.onCompleted();
                }
            }
        }, 100, 3000);
    }
      
    public void stopRun(){  
        if(timerTask!=null){
            timerTask.cancel();
        }
    }
    public void stopTimer(){
        timer.cancel();
    }
    private TimerListener timerListener;


    public void setOnTimerListener(TimerListener listener) {
        this.timerListener = listener;
    }   
    public interface TimerListener {
         void onCompleted();
    }

}  