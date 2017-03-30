package com.example.francisco.pruebabackground;

import android.app.Activity;
import android.app.AlertDialog;
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

    private String id, activity_name2, buff;
    private android.hardware.Sensor mAccelerometer;

    private final ArrayList<Sensor> sensors = new ArrayList<Sensor>();
    private ArrayAdapter<Sensor> adapter;
    private Button startStopButton, saveButton;
    EditText idEditText;
    RadioGroup radioGroup_Activities;
    RadioButton radioButton_tv_sitting;
    RadioButton radioButton_tv_lying;
    RadioButton radioButton_computer;
    RadioButton radioButton_eating;
    RadioButton radioButton_driving;
    RadioButton radioButton_transport;

    /********************** Utilizado por ESTIMOTE *******************************************/

    int current_location=0, current_location2=0;

    private static final Map<String, List<String>> PLACES_BY_BEACONS;

    // TODO: replace "<major>:<minor>" strings to match your own beacons.
    static {
        Map<String, List<String>> placesByBeacons = new HashMap<>();
        placesByBeacons.put("1:1", new ArrayList<String>() {{
            add("Cama");
            // se lee: "cama" esta mas cercana
            // al beacon con major 9682 y minor 5279
        }});

        placesByBeacons.put("1:2", new ArrayList<String>() {{
            add("Escritorio");
        }});


        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
    }

    private List<String> placesNearBeacon(Beacon beacon) {
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
            return PLACES_BY_BEACONS.get(beaconKey);
        }
        return Collections.emptyList();
    }

    private BeaconManager beaconManager;
    private Region region;
    /*****************************************************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get listview
        ListView sensorsView = (ListView)findViewById(R.id.listView);
        // Setup progress bar
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        sensorsView.setEmptyView(progressBar);

         // Add listview display adapter
        sensorsView.setAdapter(adapter);

        // Setup start/stop button
        startStopButton = (Button)findViewById(R.id.startstopbutton);
        startStopButton.setOnClickListener(this);
        startStopButton.setText("EMPEZAR");

        // Setup save button
        saveButton = (Button)findViewById(R.id.savebutton);
        saveButton.setOnClickListener(this);
        saveButton.setText("GUARDAR");
        saveButton.setEnabled(false);

        // Setup ID edit text

        idEditText = (EditText)findViewById(R.id.editText_id);
        radioGroup_Activities = (RadioGroup)findViewById(R.id.radioGroup_Activities);
        radioButton_tv_sitting = (RadioButton)findViewById(R.id.radioButton_tv_sitting);
        radioButton_tv_lying = (RadioButton)findViewById(R.id.radioButton_tv_lying);
        radioButton_computer = (RadioButton)findViewById(R.id.radioButton_computer);
        radioButton_eating = (RadioButton)findViewById(R.id.radioButton_eating);
        radioButton_driving = (RadioButton)findViewById(R.id.radioButton_driving);
        radioButton_transport = (RadioButton)findViewById(R.id.radioButton_transport);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d("LOG", "aplicacion iniciada");

        /****************************** ESTIMOTE ***********************************/

        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                Log.d("LOG", "onBeaconsDiscovered");
                Log.d("LOG", ""+list);
                if (!list.isEmpty()) {
                    Log.d("LOG", "noEmpty");
                    /**Se detecta cual beacon esta mas cerca y se relaciona con la ubicacion que representa**/
                    Beacon nearestBeacon = list.get(0);
                    Log.e("LOG", ""+nearestBeacon );
                    List<String> place = placesNearBeacon(nearestBeacon);
                    // TODO: update the UI here
                    /**Log.d("Beacon", "lugar inmediato: " + place);
                     Log.d("Beacon", String.valueOf(place));**/

                    /**Se calcula la proximidad al beacon, si esta cerca, se pone el indicador adecuado en el dataset**/
                    Utils.Proximity proximity = Utils.computeProximity(nearestBeacon);
                    if (proximity == Utils.Proximity.FAR || proximity == Utils.Proximity.NEAR || proximity == Utils.Proximity.IMMEDIATE) {

                        if (String.valueOf(place).equals("[Cama]")) {
                            Log.d("LUGAR", "tv");
                            current_location = 1;
                        }
                        if (String.valueOf(place).equals("[Escritorio]")) {
                            Log.d("LUGAR", "Escritorio");
                            current_location = 2;
                        }
                    } else {
                        current_location = 0;
                    }

                    if(list.size()>1) {
                        Beacon nearestBeacon2 = list.get(1);
                        Log.e("nearestBeacon", ""+nearestBeacon2 );
                        List<String> place2 = placesNearBeacon(nearestBeacon2);
                        /**Se calcula la proximidad al  2 beacon, si esta cerca, se pone el indicador adecuado en el dataset**/
                        Utils.Proximity proximity2 = Utils.computeProximity(nearestBeacon2);
                        if (proximity2 == Utils.Proximity.FAR || proximity2 == Utils.Proximity.NEAR || proximity2 == Utils.Proximity.IMMEDIATE) {

                            if (String.valueOf(place2).equals("[Cama]")) {
                                Log.d("LUGAR", "tv");
                                current_location2 = 1;
                            }
                            if (String.valueOf(place2).equals("[Escritorio]")) {
                                Log.d("LUGAR", "Escritorio");
                                current_location2 = 2;
                            }
                        } else {
                            current_location2 = 0;
                        }
                    }



                    /** else if (proximity == Utils.Proximity.FAR) {
                     Log.d("DISTANCIA A BEACON", "lejos");
                     }else if (proximity == Utils.Proximity.IMMEDIATE) {
                     Log.d("DISTANCIA A BEACON", "muy cerca");**/
                } else {
                    Log.d("LOG", "empty");
                    /**Como no se detectan Beacons, el indicador retorna a "0" **/
                    current_location = 0;
                    current_location2 = 0;

                }
            }


        });
        Log.d("LOG", "Region "+region);
        region = new Region("ranged region", UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), null, null);
        /*****************************************************************/

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

    private void finishAndSaveReading() {

        Calendar c = Calendar.getInstance();
        String date = Integer.toString(c.get(Calendar.DATE));
        String hour = Integer.toString(c.get(Calendar.HOUR));
        String minutes = Integer.toString(c.get(Calendar.MINUTE));
        String seconds = Integer.toString(c.get(Calendar.SECOND));
        String am = Integer.toString(c.get(Calendar.AM_PM));


        // Get/create our application's save folder
        try {

            String filename = id + " telefono " + activity_name2 + " " + date+ " "+hour+":"+minutes+":"+seconds+":"+am+ ".csv";
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/DatosExperimento/");
        dir.mkdir();
            Log.e("ubicacion",""+dir);
        // Create the file in the <activity name>-<sensor name>-<system time>.csv format
        File file = new File(dir, filename);
        FileOutputStream outputStream = new FileOutputStream(file);
        // Write the colunm headers
            outputStream.write("id,Time(ms),X(mG),Y(mG),Z(mG),Location,Location2,Classification\n".getBytes());
            outputStream.write(buff.getBytes());
            outputStream.close();
            // Workaround for Android bug #38282
            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.w("MainActivity", sensors.toString());
    }

    private AlertDialog displayDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title)
                .setNeutralButton("OK", null);
        AlertDialog dia = builder.create();
        dia.show();
        return dia;
    }


    @Override
    public void onClick(View v) {

        if(v.equals(startStopButton)) {
        if (idEditText.getText().length() == 0) {
            displayDialog("ID", "Ingresa el ID del participante");
        } else {
            if (startStopButton.getText().equals("EMPEZAR")){
                // Start recording

                id = idEditText.getText().toString();

                /**Obtengo la actividad que se va a realizar**/

                activity_name2 = "";
                if (radioButton_tv_sitting.isChecked()) {
                    activity_name2 = "Viendo tv sentado";
                } else if (radioButton_tv_lying.isChecked()) {
                    activity_name2 = "Viendo tv recostado";
                } else if (radioButton_eating.isChecked()) {
                    activity_name2 = "Almorzando-Comiendo";
                } else if (radioButton_computer.isChecked()) {
                    activity_name2 = "Trabajando en el computador";
                } else if (radioButton_driving.isChecked()) {
                    activity_name2 = "Conduciendo automovil";
                } else if (radioButton_transport.isChecked()) {
                    activity_name2 = "Transportado en automovil";
                }

                if (activity_name2.length() != 0 && id.length() != 0) {

                        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                            @Override
                            public void onServiceReady() {
                                Log.d("LOG","onServiceReady startRanging");
                                Log.d("LOG","onServiceReady startRanging "+region);
                                beaconManager.startRanging(region);
                            }
                        });



                }

                startStopButton.setText("PARAR");
                saveButton.setEnabled(false);
            } else {
                Log.d("LOG","End recording, beaconmanager disconnect");
                // End recording
                startStopButton.setText("EMPEZAR");
                saveButton.setEnabled(true);
                // stop beacon detection
                beaconManager.disconnect();
                // stop accelerometer recording

            }

        }
        }else{
            if(v.equals(saveButton)){

                finishAndSaveReading();
                Toast.makeText(MainActivity.this,"Datos Guardados", Toast.LENGTH_LONG).show();

            }
        }
    }

}