package com.example.tyler.lockpick;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        start.set(event.getX(), event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().translationXBy(event.getX() - start.x).translationYBy(event.getY() - start.y);
                        break;
                    default:
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
