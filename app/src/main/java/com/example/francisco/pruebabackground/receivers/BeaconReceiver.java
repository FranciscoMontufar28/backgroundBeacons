package com.example.francisco.pruebabackground.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class BeaconReceiver extends BroadcastReceiver {

    private int resultmajor,resultmajor2,resultminor,resultminor2 =0;

    public static final String ACTION_BEACON = "com.example.francisco.pruebabackground.ACTION_BEACONS";
    public static final String EXTRA_MAJOR = "major";
    public static final String EXTRA_MINOR = "minor";
    public static final String EXTRA_MAJOR2 = "major2";
    public static final String EXTRA_MINOR2 = "minor2";


    PublishSubject<Integer[]> subject;


    public BeaconReceiver() {

        subject = PublishSubject.create();
        /*subject.distinctUntilChanged()
                .subscribe(new Subscriber<Integer>())*/
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras =  intent.getExtras();
        int major = extras.getInt(EXTRA_MAJOR);
        int minor = extras.getInt(EXTRA_MINOR);
        int major2 = extras.getInt(EXTRA_MAJOR2);
        int minor2 = extras.getInt(EXTRA_MINOR2);

        subject.onNext(new Integer[]{major, minor, major2, minor2});

        /*Log.e("reactiveX", "dato 1 "+major);
        Log.e("reactiveX", "dato 2 "+minor);
        Log.e("reactiveX", "dato 3 "+major2);
        Log.e("reactiveX", "dato 4 "+minor2);*/


//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer)
//                    {
//                        subject.onNext(major);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                })


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



    }


    public PublishSubject<Integer[]> getBeaconNotify(){
        return subject;
    }

}
