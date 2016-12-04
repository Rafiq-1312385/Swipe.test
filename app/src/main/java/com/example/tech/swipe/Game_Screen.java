package com.example.tech.swipe;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;



public class Game_Screen extends AppCompatActivity {

    private GestureDetectorCompat mDetector;
    private static final String DEBUG_TAG = "Gestures";
    CountDownTimer cTimer = null;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event){
//        this.mDetector.onTouchEvent(event);
//        return super.onTouchEvent(event);
//
//    }


    private class MyGestureListener extends SwipeListener {
        @Override
        public boolean onSwipe(Direction d){


            if (d==d.up){
                Log.d(DEBUG_TAG, "up "+d);
                i++;
            }
            else if(d==d.down){
                Log.d(DEBUG_TAG, "down "+d);
                i++;

            }else{

            }

            return false;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "Action was MOVE");
                this.mDetector.onTouchEvent(event);
                cancelTimer();
            case (MotionEvent.ACTION_UP):
                startTimer();
                Log.d(DEBUG_TAG, "Action was UP");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
    void startTimer() {
        cTimer = new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                Log.d(DEBUG_TAG, ""+i);
            }
        };
        cTimer.start();
    }
    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }

}
