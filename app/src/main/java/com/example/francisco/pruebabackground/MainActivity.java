package com.example.francisco.pruebabackground;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;


/**
 * MainActivity class
 * Implements a PebbleDataLogReceiver to process received log data,
 * as well as a finished session.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    Button iniciar, terminar;
    BeaconReceiver receiver;
    TextView Tmajor1, Tmajor2, Tminor1, Tminor2;
    private int contador = 0;

    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar = (Button) findViewById(R.id.iniciar);
        terminar = (Button) findViewById(R.id.terminar);

        Tmajor1 = (TextView) findViewById(R.id.major1);
        Tmajor2 = (TextView) findViewById(R.id.major2);
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


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        CheckBlueToothState();

    }

    private void CheckBlueToothState(){
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    if (requestCode== REQUEST_ENABLE_BT){
        CheckBlueToothState();
    }

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
                receiver = new BeaconReceiver();
                IntentFilter filter = new IntentFilter(BeaconReceiver.ACTION_BEACON);
                Log.e("Reactive", "Filter: " + filter);
                registerReceiver(receiver, filter);
                
                receiver.getBeaconNotify()
                        .distinctUntilChanged()
                        //.observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer[]>() {
                            @Override
                            public void accept(Integer[] data) throws Exception {
                                Log.e("reactiveX", "dato 1 "+data[0]);
                                Log.e("reactiveX", "dato 2 "+data[1]);
                                Log.e("reactiveX", "dato 3 "+data[2]);
                                Log.e("reactiveX", "dato 4 "+data[3]);
                                contador ++;
                                Log.e("reactiveX", "Contador "+contador);

                                Tmajor1.setText("" + data[0]);
                                Tmajor2.setText("" + data[2]);
                                Tminor1.setText("" + data[1]);
                                Tminor2.setText("" + data[3]);
                            }
                        });

                break;

            case R.id.terminar:
                Log.e("reactivex", "Terminar");
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                stopService(intent);
                break;
        }


    }


}