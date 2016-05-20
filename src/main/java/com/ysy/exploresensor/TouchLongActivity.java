package com.ysy.exploresensor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TouchLongActivity extends AppCompatActivity {

    private Button btnTouch;
    private TextView tvTime;
    private ImageView circleImg;
    private TextView circleTime;
    private long time;
    private long time_start;
    private long time_end;
    private final float CL = 1369.7344f;
    private double x = 0;
    private double y = 0;
    private double ds = 0;
    private double s = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_long);

        tvTime = (TextView) findViewById(R.id.touch_time_tv);
        circleTime = (TextView) findViewById(R.id.circle_time_tv);

        btnTouch = (Button) findViewById(R.id.touch_btn);
        btnTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tvTime.setText("Getting Touch Time...");
                        time_start = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        time_end = System.currentTimeMillis();
                        time = time_end - time_start;
                        tvTime.setText(time + "ms");
                        break;
                }
                return false;
            }
        });

        circleImg = (ImageView) findViewById(R.id.circle_img);
        circleImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        s = 0;
                        x = event.getX();
                        y = event.getY();
                        circleTime.setText("Getting Circle Info...");
                        time_start = System.currentTimeMillis();
//                        Toast.makeText(TouchLongActivity.this, "" + time_start, Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        Toast.makeText(TouchLongActivity.this, "" + event.getX(), Toast.LENGTH_SHORT).show();
                        ds = Math.sqrt((event.getX() - x) * (event.getX() - x) + (event.getY() - y) * (event.getY() - y));
                        s = s + ds;
                        break;
                    case MotionEvent.ACTION_UP:
                        time_end = System.currentTimeMillis();
                        time = time_end - time_start;
                        circleTime.setText((float) s + "dpi " + time + "ms " + (CL * 1000 / time) + "dpi/s");
                        break;
                }
                return false;
            }
        });

    }
}
