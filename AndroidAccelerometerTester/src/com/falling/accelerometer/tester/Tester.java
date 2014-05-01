package com.falling.accelerometer.tester;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Tester extends Activity {
	
	private SensorManager manager;
	private TextView vector;
	private TextView angle;
	private long startTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Call superclass's method
    	super.onCreate(savedInstanceState);
    	
    	// Set this Activity's content view
        setContentView(R.layout.activity_accelerometer);
        
        startTime = System.currentTimeMillis();
		
		vector = (TextView) findViewById(R.id.vector);
		angle = (TextView) findViewById(R.id.angle);
		
		manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		manager.registerListener(listener, 
				manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
				SensorManager.SENSOR_DELAY_GAME);
	}
	
	
	private SensorEventListener listener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			vector.setText(String.format("{%2.0f, %2.0f, %2.0f}", event.values[0], 
					event.values[1], event.values[2]));
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			Date time = new Date();
			angle.setText(String.format("At time %s, accuracy changed to %d", time.toString(), accuracy));
		}
	};
}
