package com.example.tech.swipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import static com.example.tech.swipe.R.layout.game_activity;


public class Game_Screen extends AppCompatActivity {

    private GestureDetectorCompat mDetector;
    private static final String DEBUG_TAG = "Gestures";
    CountDownTimer cTimer = null;
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(game_activity);
        View myView = (View) findViewById(R.id.myView);
        myView.setOnTouchListener(new SwipeListener1(this) {
            @Override
            public boolean onSwipe(Direction d) {

                if (d == d.up) {
                    cancelTimer();
                    Log.d(DEBUG_TAG, "up " + d);
                    i++;
                } else if (d == d.down) {
                    cancelTimer();
                    Log.d(DEBUG_TAG, "down " + d);
                    i++;
                }else {
                    startTimer(i);
                }
                return false;
            }

        });
        // mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event){
//        //this.mDetector.onTouchEvent(event);
//        return super.onTouchEvent(event);
//    }

    void startTimer(final int i) {
        cTimer = new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                game_over_dialog(i);
            }
        };
        cTimer.start();
    }

    //cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

    void game_over_dialog(int i){
        new AlertDialog.Builder(Game_Screen.this)
                .setTitle("Game Over")
                .setMessage("You Have Scored : " + i +"\n Do Want to play again?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(),Game_Screen.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
    }*/

}
