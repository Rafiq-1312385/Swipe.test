package com.example.tech.swipe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View.OnTouchListener;
import android.view.View;
//import com.example.tech.swipe.ui.Game_Fragment.GameOver;



public class Game_UI extends SurfaceView implements OnTouchListener, SurfaceHolder.Callback  {

    private GestureDetector gestureDetector;
    //http://www.mathatube.com/images/full_circle_protractor-008.jpg <--use this protractor as a reference or above
    static private final double MAX_ANGLE = 15;
    static private final double SWIPE_UP_UPPER_RANGE = 90 + (MAX_ANGLE / 2), SWIPE_UP_LOWER_RANGE = 90 - (MAX_ANGLE / 2); //97.5 - 82.5 = 15 degrees
    static private final double SWIPE_DOWN_UPPER_RANGE = 270 + (MAX_ANGLE / 2), SWIPE_DOWN_LOWER_RANGE = 270 - (MAX_ANGLE / 2); //277.5- 262.5 = 15 degrees
    //private static final String DEBUG_TAG = "Gestures";
    //private static final int SLIDE_THRESHOLD_MAX = 200;
    int i;

//    private GameOver gameOverListener;


    public Game_UI(Context context) {
        super(context);
        initialise(context);
    }


    public Game_UI(Context context, AttributeSet attrs) {
        super(context);
        initialise(context);
    }

    public Game_UI(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialise(context);

    }

    private void initialise(Context context) {
        this.setOnTouchListener(this);
        this.setFocusable(true);
        this.getHolder().addCallback( this);
        gestureDetector = new GestureDetector(context, new GestureListener());

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);

    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {


            float x1 = e1.getX(), y1 = e1.getY(); //The First point x,y axis
            float x2 = e2.getX(), y2 = e2.getY(); //The second point x,y axis
            //Log.d(DEBUG_TAG, "D "+distanceY);


            Direction direction = getDirectionandAngle(x1, y1, x2, y2); //Sent for Directional calculation
            return onSwipe(direction);

        }


        //http://stackoverflow.com/questions/1311049/how-to-map-atan2-to-degrees-0-360
        //http://stackoverflow.com/questions/7235839/calculating-the-angle-between-the-horizontal-and-a-line-through-two-points
        //http://stackoverflow.com/questions/1707151/finding-angles-0-360
        public Direction getDirectionandAngle(float x1, float y1, float x2, float y2) {
            double angle = (Math.atan2(y1 - y2, x2 - x1) * 180 / Math.PI + 180) % 360; //First section m.a2 Calculates the angle between the horizontal and a line through two points (radian) * convert to radians % from 0 to 360
            return Direction.get(angle,y1, y2);
        }

    }

    //Enum used as there is a fixed set of constants
    public enum Direction {
        up, down, other;

        public static Direction get(double angle, float y1, float y2) {
            //float deltaY = y2 - y1;
            //if(Math.abs(deltaY) > SLIDE_THRESHOLD_MAX) {
            if ((angle >= SWIPE_UP_LOWER_RANGE) && (angle < SWIPE_UP_UPPER_RANGE) /*&& deltaY > 0*/) { //checks users swipe angle and if its within range
                return Direction.up;
            } else if ((angle >= SWIPE_DOWN_LOWER_RANGE) && (angle < SWIPE_DOWN_UPPER_RANGE) ) {
                return Direction.down;
            } else {
                return Direction.other;
            }
            //}else{
            //    return null;
            //}
        }
    }


    //method that will be overridden, to pass the detected action on.
    public boolean onSwipe(Direction direction) {return false;}





//    public void setGameOverListener(GameOver gameOverListener) {
//        this.gameOverListener = gameOverListener;
//    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}


