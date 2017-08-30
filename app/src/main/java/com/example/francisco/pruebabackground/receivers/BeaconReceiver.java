package com.example.francisco.pruebabackground.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;


public class BeaconReceiver extends BroadcastReceiver {

    private int resultmajor,resultmajor2,resultminor,resultminor2 =0;

    public static final String ACTION_BEACON = "com.example.francisco.pruebabackground.ACTION_BEACONS";
    public static final String EXTRA_MAJOR = "major";
    public static final String EXTRA_MINOR = "minor";
    public static final String EXTRA_MAJOR2 = "major2";
    public static final String EXTRA_MINOR2 = "minor2";



    public interface OnBeaconListener{
        void onBeacon(int major, int minor,int major2, int minor2);
    }

    OnBeaconListener onBeaconListener;

    public BeaconReceiver(OnBeaconListener onBeaconListener) {
        this.onBeaconListener = onBeaconListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras =  intent.getExtras();
        int major = extras.getInt(EXTRA_MAJOR);
        int minor = extras.getInt(EXTRA_MINOR);
        int major2 = extras.getInt(EXTRA_MAJOR2);
        int minor2 = extras.getInt(EXTRA_MINOR2);
        //Log.e("Reactivex", "onReceive"+ major);

/************************************************************************************************************************************/
        /*Observable.just(major)
                        .distinctUntilChanged()
                        .subscribe(new Subscriber<Integer>() {

                            @Override
                            public void onNext(Integer integer) {
                                resultmajor=integer;
                                //Log.e("Reactivex", "valor reactive"+ integer);
                            }


                            @Override
                            public void onCompleted() {
                                onBeaconListener.onBeacon(resultmajor, resultminor, resultmajor2, resultminor2);
                                //Log.e("reactivex", "Sequence complete.");
                                System.out.println("Sequence complete.");
                            }

                            @Override
                            public void onError(Throwable t) {
                                //Log.e("reactivex", "Error: " + t.getMessage());
                                System.err.println("Error: " + t.getMessage());

                            }

                        });*/

/************************************************************************************************************************************/

    onBeaconListener.onBeacon(major, minor, major2, minor2);

    }


}
