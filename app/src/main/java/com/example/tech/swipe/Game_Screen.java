package com.example.tech.swipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;



public class Game_Screen extends AppCompatActivity {

    private GestureDetectorCompat mDetector;
    private static final String DEBUG_TAG = "Gestures";
    CountDownTimer cTimer = null;
    static int count_down_timer,swipe_limit, swipe_area_sq_hieght, swipe_area_sq_width,limiter;
    int i;
    static boolean stop_watch = false, set_backgroud = false;
    private View myView;
    Chronometer mChronometer;
    static Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        myView = findViewById(R.id.myView);
        MainActivity m = new MainActivity();
        if(set_backgroud == true){
            //Intent intent = getIntent();
            //Bitmap bm = (Bitmap) intent.getParcelableExtra("backgroundimage");
            Drawable drawable = new BitmapDrawable(getResources(), bm);
            myView.setBackgroundDrawable(drawable);
        }
        else myView.setBackgroundResource(R.drawable.bg_transparent);

        mChronometer = (Chronometer) findViewById(R.id.chronometer);
        if(swipe_area_sq_hieght != myView.getWidth()|| swipe_area_sq_hieght != myView.getWidth()) {
            myView.getLayoutParams().height= swipe_area_sq_hieght;
            myView.getLayoutParams().width = swipe_area_sq_width;

        }

        myView.setOnTouchListener(new SwipeListener1(this) {
            MediaPlayer sound_effect;
            @Override
            public boolean onSwipe(Direction d) {
                TextView user_action_text = (TextView)findViewById(R.id.User_Action_Text);
                stopPlaying();
                sound_effect = MediaPlayer.create(getApplicationContext(), R.raw.big_swish_with_ding);

                if (d == Direction.up) {
                    cancelTimer();
                    sound_effect.start();
                    i++;
                    user_action_text.setText("Swiped up" + i);
                } else if (d == Direction.down) {
                    cancelTimer();
                    sound_effect.start();
                    i++;
                    user_action_text.setText("Swiped Down" + i);
                }else{
                    i--;
                    user_action_text.setText("out" + i);
                }

                if(stop_watch==false) startcountdownTimer(i);
                else if(stop_watch==true) {
                    if (limiter == 0) {
                        mChronometer.setBase(SystemClock.elapsedRealtime());
                        mChronometer.start();
                        limiter=1;
                    }
                    while(i>=swipe_limit) {
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                        calendar.setTimeInMillis(SystemClock.elapsedRealtime() - mChronometer.getBase());
                        int miliseconds = calendar.get(Calendar.SECOND);
                        game_over_dialog("Weldone !!", ("you have swiped "+swipe_limit+" swipes in "+ miliseconds + " seconds \nDo you want to play again ?"));
                        mChronometer.stop();
                        i=limiter=0;
                    }
                }

                return false;
            }

            void stopPlaying(){
                try {
                    sound_effect.reset();
                    sound_effect.prepare();
                    sound_effect.stop();
                    sound_effect.release();
                    sound_effect=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        // mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event){
//        //this.mDetector.onTouchEvent(event);
//        return super.onTouchEvent(event);
//    }

    void startcountdownTimer(final int i) {
        if(count_down_timer ==0){
            count_down_timer =1000;}

        cTimer = new CountDownTimer(count_down_timer, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                game_over_dialog("Game Over", "you have Scored : "+ i);
            }
        };
        cTimer.start();
    }

    //cancel count_down_timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

    void game_over_dialog(String header_txt, String user_message){
        new AlertDialog.Builder(Game_Screen.this)
                .setTitle(header_txt)
                .setMessage(user_message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Game_Screen.this.finish();
                        startActivity(new Intent(getApplicationContext(),Game_Screen.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent launchNext = new Intent(getApplicationContext(), MainActivity.class);
                        Game_Screen.this.finish();
                        startActivity(launchNext);
                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false)
                .show();
    }




/*
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

    private int mActivePointerId;

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mActivePointerId = event.getPointerId(0);
        int action = MotionEventCompat.getActionMasked(event);
// Get the index of the pointer associated with the action.
        int index = MotionEventCompat.getActionIndex(event);
        int xPos = -1;
        int yPos = -1;

        Log.d(DEBUG_TAG,"The action is " + actionToString(action));

        if (event.getPointerCount() > 1) {
            Log.d(DEBUG_TAG,"Multitouch event");
            // The coordinates of the current screen contact, relative to
            // the responding View or Activity.
            xPos = (int)MotionEventCompat.getX(event, index);
            yPos = (int)MotionEventCompat.getY(event, index);

        } else {
            // Single touch event
            Log.d(DEBUG_TAG,"Single touch event");
            xPos = (int)MotionEventCompat.getX(event, index);
            yPos = (int)MotionEventCompat.getY(event, index);
        }

        // ... Many touch events later...

        // Use the pointer ID to find the index of the active pointer
        // and fetch its position
        int pointerIndex = event.findPointerIndex(mActivePointerId);
        // Get the pointer's current position
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        return false;
    }

    // Given an action int, returns a string description
    public static String actionToString(int action) {
        switch (action) {

            case MotionEvent.ACTION_DOWN: return "Down";
            case MotionEvent.ACTION_MOVE: return "Move";
            case MotionEvent.ACTION_POINTER_DOWN: return "Pointer Down";
            case MotionEvent.ACTION_UP: return "Up";
            case MotionEvent.ACTION_POINTER_UP: return "Pointer Up";
            case MotionEvent.ACTION_OUTSIDE: return "Outside";
            case MotionEvent.ACTION_CANCEL: return "Cancel";
        }
        return "";
    }

    void startcountdownTimer() {
        cTimer = new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                Log.d(DEBUG_TAG, ""+i);
            }
        };
        cTimer.start();
    }
    //cancel count_down_timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }*/

}
