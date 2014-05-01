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
	private long time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Call superclass's method
    	super.onCreate(savedInstanceState);
    	
    	// Set this Activity's content view
        setContentView(R.layout.activity_accelerometer);
        
        time = System.currentTimeMillis();
		
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
			if(System.currentTimeMillis() - time >= 125) {
				double accelx = event.values[0];
				double accely = event.values[1];
				double accelz = event.values[2];
				
				double gravx = 0;
				double gravy = accelz > accely ? 0 : 9.8;
				double gravz = accelz > accely ? 9.8 : 0;
				
				double accelmag = Math.sqrt(Math.pow(accelx, 2) + Math.pow(accely, 2) + Math.pow(accelz, 2));
				double gravmag = 9.8;
				
				double num = accelx * gravx + accely * gravy + accelz * gravz;
				double den = accelmag * gravmag;
				
				double angle = Math.toDegrees(Math.acos(num / den));
				
				vector.setText(String.format("{%2.2f, %2.2f, %2.2f}", accelx, accely, accelz));
				Tester.this.angle.setText(String.format("%2.2f degrees", angle));
				time = System.currentTimeMillis();
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			Date time = new Date();
			angle.setText(String.format("At time %s, accuracy changed to %d", time.toString(), accuracy));
		}
	};
}