package com.ysy.exploresensor;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private TextView tvAccelerometer;
    private TextView tvMagentic;
    private TextView tvLight;
    private TextView tvOrientation;
    private TextView tvSensors;
    private Switch switchAccelerometer;
    private Switch switchMagentic;
    private Switch switchLight;
    private Switch switchOrientation;

    private Button btnBallGame;
    private Button btnTouchLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBallGame = (Button) findViewById(R.id.ball_game_btn);
        btnBallGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BallGameActivity.class));
            }
        });

        btnTouchLong = (Button) findViewById(R.id.touch_long_btn);
        btnTouchLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TouchLongActivity.class));
            }
        });

        tvAccelerometer = (TextView) findViewById(R.id.tvAccelerometer);
        tvMagentic = (TextView) findViewById(R.id.tvMagentic);
        tvLight = (TextView) findViewById(R.id.tvLight);
        tvOrientation = (TextView) findViewById(R.id.tvOrientation);
        tvSensors = (TextView) findViewById(R.id.tvSensors);
        switchAccelerometer = (Switch) findViewById(R.id.switchAccelerometer);
        switchLight = (Switch) findViewById(R.id.switchLight);
        switchMagentic = (Switch) findViewById(R.id.switchMagentic);
        switchOrientation = (Switch) findViewById(R.id.switchOrientation);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors) {
            tvSensors.append("\n" + "Name:" + sensor.getName()
                    + " Version:" + sensor.getVersion()
                    + " Vendor:" + sensor.getVendor() + "\n" + "\n");
        }

        switchAccelerometer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
                else
                    sensorManager.registerListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        switchMagentic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
                else
                    sensorManager.registerListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        switchLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
                else
                    sensorManager.registerListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        switchOrientation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));
                else
                    sensorManager.registerListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                String accelerometer = "加速度\n" + "X：" + event.values[0] + "\n"
                        + "Y:" + event.values[1] + "\n" + "Z:" + event.values[2] + "\n";
                tvAccelerometer.setText(accelerometer);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                String magentic = "磁场\n" + "X：" + event.values[0] + "\n" + "Y:"
                        + event.values[1] + "\n" + "Z:" + event.values[2] + "\n";
                tvMagentic.setText(magentic);
                break;
            case Sensor.TYPE_LIGHT: // 处理光线传感器传回的数据
                tvLight.setText("亮度：" + event.values[0]);
                break;
            case Sensor.TYPE_ORIENTATION: // 处理方向传感器传回的数据
                String orientation = "方向\n" + "X：" + event.values[0] + "\n"
                        + "Y:" + event.values[1] + "\n" + "Z:" + event.values[2] + "\n";
                tvOrientation.setText(orientation);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

}
