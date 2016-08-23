package com.example.tyler.lockpick;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {
    private static final boolean TOOLBOX_CLOSED = false;
    private static final boolean TOOLBOX_OPEN = true;
    private boolean toolbox_state;
    PointF start = new PointF();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toolbox_state = TOOLBOX_CLOSED;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.lockpick).setOnTouchListener(new View.OnTouchListener(){
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

    /**
     * When the toolbox button is clicked
     * @param view
     */
    public void toolboxTap(View view) {
        if (!toolbox_state){
            Toast.makeText(this,"Opening the toolbox", Toast.LENGTH_SHORT).show();
            toolbox_state = TOOLBOX_OPEN;
        } else {
            Toast.makeText(this,"Closing the toolbox", Toast.LENGTH_SHORT).show();
            toolbox_state = TOOLBOX_CLOSED;
        }
    }
}
