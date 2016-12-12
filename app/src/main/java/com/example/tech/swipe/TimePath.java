package com.example.tech.swipe;

/**
 * Created by Tech on 07/12/16.
 */

import android.graphics.Path;

public class TimePath extends Path {

    private long timeDrawn;

    public long getTimeDrawn() {
        return timeDrawn;
    }

    public void updateTimeDrawn(long timeDrawn) {
        this.timeDrawn = timeDrawn;
    }
}