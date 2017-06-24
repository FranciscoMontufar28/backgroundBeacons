package com.example.francisco.pruebabackground;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.example.francisco.pruebabackground.receivers.BeaconReceiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jhovy on 30/03/2017.
 */

public class BeaconService extends Service {
    private Looper mServiceLooper;
    private Handler mServiceHandler;

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

    private final class ServiceHandler extends Handler{
        private ServiceHandler(Looper looper){
            super(looper);
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        region = new Region("ranged region", UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), null, null);
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    Intent intent =new Intent(BeaconReceiver.ACTION_BEACON);
                    intent.putExtra(BeaconReceiver.EXTRA_MAJOR, nearestBeacon.getMajor());
                    intent.putExtra(BeaconReceiver.EXTRA_MINOR, nearestBeacon.getMinor());
                    sendBroadcast(intent);
                    //List<String> place = placesNearBeacon(nearestBeacon);
                    //Utils.Proximity proximity = Utils.computeProximity(nearestBeacon);

                    if(list.size()>1) {
                        Beacon nearestBeacon2 = list.get(1);
                        Intent intent1 = new Intent(BeaconReceiver.ACTION_BEACON);
                        intent1.putExtra(BeaconReceiver.EXTRA_MAJOR, nearestBeacon2.getMajor());
                        intent1.putExtra(BeaconReceiver.EXTRA_MINOR, nearestBeacon2.getMinor());
                        sendBroadcast(intent1);
                    }

                } else {
                    Log.d("LOG", "empty");
                    /**Como no se detectan Beacons, el indicador retorna a "0" **/
                    current_location = 0;
                    current_location2 = 0;

                }
            }


        });







        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d("LOG","onServiceReady startRanging");
                Log.d("LOG","onServiceReady startRanging "+region);
                beaconManager.startRanging(region);
            }
        });



        return super.onStartCommand(intent, flags, startId);
    }
}
