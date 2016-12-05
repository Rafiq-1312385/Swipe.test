package com.example.tech.swipe;


import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Tech on 03/12/16.
 */


class SwipeListener extends GestureDetector.SimpleOnGestureListener {
    /*
               90(UP)
                 |
                 |
        180--------------0/360
                 |
                 |
              270(Down)

    */
    //http://www.mathatube.com/images/full_circle_protractor-008.jpg <--use this protractor as a reference or above
    static private final double MAX_ANGLE = 15;
    static private final double SWIPE_UP_UPPER_RANGE = 90 + (MAX_ANGLE / 2), SWIPE_UP_LOWER_RANGE = 90 - (MAX_ANGLE / 2); //97.5 - 82.5 = 15 degrees
    static private final double SWIPE_DOWN_UPPER_RANGE = 270 + (MAX_ANGLE / 2), SWIPE_DOWN_LOWER_RANGE = 270 - (MAX_ANGLE / 2); //277.5- 262.5 = 15 degrees
    //private static final String DEBUG_TAG = "Gestures";
    //static int i;



    //onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    //Notified of a fling event when it occurs with the initial on down MotionEvent and the matching up MotionEvent.
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float x1 = e1.getX(), y1 = e1.getY(); //The First point x,y axis
        float x2 = e2.getX(), y2 = e2.getY(); //The second point x,y axis

        Direction direction = getDirectionandAngle(x1, y1, x2, y2); //Sent for Directional calculation
        return onSwipe(direction);
    }

    /**
     * Override this method. The Direction enum will tell you how the user swiped.
     */
    public boolean onSwipe(Direction direction) {
        return false;
    }

    //http://stackoverflow.com/questions/1311049/how-to-map-atan2-to-degrees-0-360
    //http://stackoverflow.com/questions/7235839/calculating-the-angle-between-the-horizontal-and-a-line-through-two-points
    //http://stackoverflow.com/questions/1707151/finding-angles-0-360
    public Direction getDirectionandAngle(float x1, float y1, float x2, float y2) {
        double angle = (Math.atan2(y1 - y2, x2 - x1) * 180 / Math.PI + 180) % 360; //First section m.a2 Calculates the angle between the horizontal and a line through two points (radian) * convert to radians % from 0 to 360
        return Direction.get(angle);
    }


    //Enum used as I have a fixed set of constants
    public enum Direction {
        up, down, other;

        public static Direction get(double angle) {
            if ((angle >= SWIPE_UP_LOWER_RANGE) && (angle < SWIPE_UP_UPPER_RANGE)) {
                //Log.d(DEBUG_TAG, "" + Direction.up);
                //i++;
                return Direction.up;
            } else if ((angle >= SWIPE_DOWN_LOWER_RANGE) && (angle < SWIPE_DOWN_UPPER_RANGE)) {
                //Log.d(DEBUG_TAG, "" + Direction.down);
                //i++;
                return Direction.down;

            } else {
                //Log.d(DEBUG_TAG, "i " + i);
                return Direction.other;
            }
        }
    }
}


