package com.tyler.lockpick.Objects;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by tyler on 8/27/16.
 */
public class LockPick {
    ImageView lockpick;

    public LockPick(View lockpick) {
        this.lockpick = (ImageView) lockpick;
        //TODO: Map out the entire visible lockpick
        //TODO: Detect collisions with pins and edges

        // Lockpick movement
        lockpick.setOnTouchListener(new View.OnTouchListener(){
            float x_offset = 0;
            float y_offset = 0;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        x_offset = event.getRawX() - view.getX();
                        y_offset = event.getRawY() - view.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.setX (event.getRawX() - x_offset);
                        view.setY (event.getRawY() - y_offset);
                        break;
                }
                return true;
            }
        });

    }

    public ImageView getImage(){
        return lockpick;
    }
}
