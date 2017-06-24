package com.example.francisco.pruebabackground;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

/**
 * MainActivity class
 * Implements a PebbleDataLogReceiver to process received log data,
 * as well as a finished session.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    Button iniciar, terminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar = (Button) findViewById(R.id.iniciar);
        terminar = (Button) findViewById(R.id.terminar);

        iniciar.setOnClickListener(this);
        terminar.setOnClickListener(this);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d("LOG", "aplicacion iniciada");




        //Log.d("LOG", "Region "+region);
        //region = new Region("ranged region", UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), null, null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //estimote requires turn on BT and to access GPS location?
        //SystemRequirementsChecker.checkWithDefaultDialogs(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        // Always unregister callbacks
        //if(dataloggingReceiver != null) {
        //    unregisterReceiver(dataloggingReceiver);
        //}
        //SensorManager mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        //mSensorManager.unregisterListener(this, mAccelerometer);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, BeaconService.class);
        startService(intent);
        switch (v.getId()) {
            case R.id.iniciar:

                break;

            case R.id.terminar:
                stopService(intent);
                break;
        }



}
}