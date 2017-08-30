package com.example.francisco.pruebabackground;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.example.francisco.pruebabackground.receivers.BeaconReceiver;

import rx.Observable;
import rx.Subscriber;

/**
 * MainActivity class
 * Implements a PebbleDataLogReceiver to process received log data,
 * as well as a finished session.
 */
public class MainActivity extends Activity implements View.OnClickListener, BeaconReceiver.OnBeaconListener{

    Button iniciar, terminar;
    BeaconReceiver receiver;
    TextView Tmajor1, Tmajor2, Tminor1, Tminor2;
    String major1, major2, minor2, minor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar = (Button) findViewById(R.id.iniciar);
        terminar = (Button) findViewById(R.id.terminar);

        Tmajor1= (TextView) findViewById(R.id.major1);
        Tmajor2= (TextView) findViewById(R.id.major2);
        Tminor1 = (TextView) findViewById(R.id.minor1);
        Tminor2 = (TextView) findViewById(R.id.minor2);


        iniciar.setOnClickListener(this);
        terminar.setOnClickListener(this);

        //receiver = new BeaconReceiver(this);
        //IntentFilter filter = new IntentFilter(BeaconReceiver.ACTION_BEACON);
        //registerReceiver(receiver, filter);

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
        switch (v.getId()) {
            case R.id.iniciar:
                startService(intent);
                receiver = new BeaconReceiver(this);
                IntentFilter filter = new IntentFilter(BeaconReceiver.ACTION_BEACON);
                Log.e("Reactive", "Filter: "+filter);
                registerReceiver(receiver, filter);
                break;

            case R.id.terminar:
                Log.e("reactivex", "Terminar");
                Toast.makeText(this,"", Toast.LENGTH_SHORT).show();
                stopService(intent);
                break;
        }



}

    @Override
    public void onBeacon(int major, int minor,int major2, int minor2) {

        Tmajor1.setText(""+major);
        Tmajor2.setText(""+major2);
        Tminor1.setText(""+minor);
        Tminor2.setText(""+minor2);

        Log.e("Reactivex", "valor reactive"+ major);



    }
}