package com.example.francisco.pruebabackground;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.francisco.pruebabackground.adapter.RecomendationAdapter;
import com.example.francisco.pruebabackground.databinding.ActivityBeaconRecomendationBinding;
import com.example.francisco.pruebabackground.databinding.TemplateRecomendationBinding;
import com.example.francisco.pruebabackground.fragments.MainFragment;
import com.example.francisco.pruebabackground.models.Recomendation;
import com.example.francisco.pruebabackground.receivers.BeaconReceiver;

import java.util.ArrayList;
import java.util.List;

public class BeaconRecomendation extends AppCompatActivity {

    List<Recomendation> data;
    RecomendationAdapter adapter;

    ActivityBeaconRecomendationBinding binding;
    Intent intent = new Intent(this, BeaconService.class);
    BeaconReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beacon_recomendation);
        binding.setPromotionhandler(this);
        //setContentView(R.layout.activity_beacon_recomendation);

        putFragment(R.id.container, MainFragment.instance());

        //ListView list = (ListView) findViewById(R.id.ListRecomendations);


        //data = new ArrayList<>();
        //adapter = new RecomendationAdapter(getLayoutInflater(), data);

        // list.setAdapter(adapter);

        //loadRecomendations();

        //adapter.notifyDataSetChanged();
    }

    public void putFragment(int container, Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(container, fragment);
        ft.commit();
    }

    public void start(){

        Toast.makeText(this, "Inicar Servicio", Toast.LENGTH_SHORT).show();
        startService(intent);
        receiver = new BeaconReceiver();
        IntentFilter filter = new IntentFilter(BeaconReceiver.ACTION_BEACON);
        registerReceiver(receiver, filter);

    }

    public void stop(){

        Toast.makeText(this, "Detener Servicio", Toast.LENGTH_SHORT).show();
        stopService(intent);
    }

    //region Data first adapter
    /*public void loadRecomendations(){
        Recomendation recomendation1 = new Recomendation();
        recomendation1.setName("Arroz doña pepa");
        recomendation1.setDescription("Arroz de los cultivos de doña pepa, lugar donde trabajan los minions criollos");
        recomendation1.setImage("http://www.curiosfera.com/wp-content/uploads/2016/08/Qu%C3%A9-es-el-arroz.jpg");

        Recomendation recomendation2 = new Recomendation();
        recomendation2.setName("dulces wonka");
        recomendation2.setDescription("aprobados por los umpalumpas");
        recomendation2.setImage("https://i.pinimg.com/originals/05/31/3f/05313fa859c8baa006852d2d2ff70f3f.jpg");

        Recomendation recomendation3 = new Recomendation();
        recomendation3.setName("agua del mar muerto");
        recomendation3.setDescription("agua de los guerreros se bañaban en sus aguas despues de la batalla");
        recomendation3.setImage("http://s1.1zoom.me/b5050/302/Boats_Water_Vikings_499620_3840x2400.jpg");

        data.add(recomendation1);
        data.add(recomendation2);
        data.add(recomendation3);

    }*/
    //endregion
}
