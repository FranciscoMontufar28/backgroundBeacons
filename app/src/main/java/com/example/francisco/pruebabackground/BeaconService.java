package com.example.francisco.pruebabackground;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
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
import java.util.Observable;
import java.util.UUID;

import rx.Subscriber;

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
            add("Pasillo1");
         }});

        placesByBeacons.put("1:2", new ArrayList<String>() {{
            add("Pasillo2");
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
    public int onStartCommand(Intent intent2, int flags, int startId) {
        final Intent intent =new Intent(BeaconReceiver.ACTION_BEACON);
        //region = new Region("ranged region", UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), null, null);
        region = new Region("ranged region", UUID.fromString("FDA50693-A4E2-4FB1-AFCF-C6EB07647825"), null, null);
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    //Log.e("Reactive", "size "+list.size());
                    switch (list.size()){

                        case 1:
                            //Log.e("Reactive", "case 2");
                            intent.putExtra(BeaconReceiver.EXTRA_MAJOR, nearestBeacon.getMajor());
                            //intent.putExtra(BeaconReceiver.EXTRA_MINOR, nearestBeacon.getMinor());

                            break;
                        case 2:
                            //Log.e("Reactive", "case 2");
                            Beacon nearestBeacon2 = list.get(1);
                            intent.putExtra(BeaconReceiver.EXTRA_MAJOR, nearestBeacon.getMajor());
                            //intent.putExtra(BeaconReceiver.EXTRA_MINOR, nearestBeacon.getMinor());
/**************************************************************************************************************************/
                            intent.putExtra(BeaconReceiver.EXTRA_MAJOR2, nearestBeacon2.getMajor());
                            //intent.putExtra(BeaconReceiver.EXTRA_MINOR2, nearestBeacon2.getMinor());
                            break;


                    }
                    sendBroadcast(intent);

                } else {
                    Log.d("ReactiveX", "empty");
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



        return super.onStartCommand(intent2, flags, startId);
    }
}
