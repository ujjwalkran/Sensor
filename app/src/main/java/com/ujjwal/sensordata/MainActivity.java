package com.ujjwal.sensordata;

import static android.util.Half.EPSILON;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import  java.util.concurrent.TimeUnit;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    String acceldata;
    FirebaseDatabase db;
    DatabaseReference reference;

    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp;

    private static final float ALPHA = 0.6f; // low-pass filter coefficient
    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensorManager != null){

            Sensor accelerosensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Sensor gyroscopedata = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

            if(accelerosensor!=null){
                sensorManager.registerListener(this,accelerosensor,sensorManager.SENSOR_DELAY_NORMAL);
            }

        } else{
            Toast.makeText(this, "sensor service not availaible", Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensordataas sesor1val = null;
        gyro gyrodata = null;
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            gravity[0] = ALPHA * gravity[0] + (1 - ALPHA) * event.values[0];
            gravity[1] = ALPHA * gravity[1] + (1 - ALPHA) * event.values[1];
            gravity[2] = ALPHA * gravity[2] + (1 - ALPHA) * event.values[2];

            linear_acceleration[0] = event.values[0] - gravity[0];
            linear_acceleration[1] = event.values[1] - gravity[1];
            linear_acceleration[2] = event.values[2] - gravity[2];

            float a = linear_acceleration[0];
            float b = linear_acceleration[1];
            float c = linear_acceleration[2];
            ((TextView)findViewById(R.id.acceltextx)).setText(a+" ");
            ((TextView)findViewById(R.id.acceltexty)).setText(b+" ");
            ((TextView)findViewById(R.id.acceltextz)).setText(c+" ");
            sesor1val = new  Sensordataas(a,b,c);
        }

        if (timestamp != 0) {
            final float dT = (event.timestamp - timestamp) * NS2S;
            // Axis of the rotation sample, not normalized yet.
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            // Calculate the angular speed of the sample
            float omegaMagnitude = (float) sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

            // Normalize the rotation vector if it's big enough to get the axis
            // (that is, EPSILON should represent your maximum allowable margin of error)
            if (omegaMagnitude > EPSILON) {
                axisX /= omegaMagnitude;
                axisY /= omegaMagnitude;
                axisZ /= omegaMagnitude;
            }

            // Integrate around this axis with the angular speed by the timestep
            // in order to get a delta rotation from this sample over the timestep
            // We will convert this axis-angle representation of the delta rotation
            // into a quaternion before turning it into the rotation matrix.
            float thetaOverTwo = omegaMagnitude * dT / 2.0f;
            float sinThetaOverTwo = (float) sin(thetaOverTwo);
            float cosThetaOverTwo = (float) cos(thetaOverTwo);
            deltaRotationVector[0] = sinThetaOverTwo * axisX;
            deltaRotationVector[1] = sinThetaOverTwo * axisY;
            deltaRotationVector[2] = sinThetaOverTwo * axisZ;
            deltaRotationVector[3] = cosThetaOverTwo;
        }
        timestamp = event.timestamp;
        float[] deltaRotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
        gyrodata = new gyro(deltaRotationVector[0],deltaRotationVector[1],deltaRotationVector[2],deltaRotationVector[3]);
        ((TextView)findViewById(R.id.gyrotextx)).setText(deltaRotationVector[0]+"");
        ((TextView)findViewById(R.id.gyrotexty)).setText(deltaRotationVector[1]+"");
        ((TextView)findViewById(R.id.gyrotextz)).setText(deltaRotationVector[2]+"");

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Sensorvalue");
        reference.child("acclerometer").setValue(sesor1val).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
            }
        });
        reference = db.getReference("Sensorvalue");
        reference.child("gyroscope9").setValue(gyrodata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}