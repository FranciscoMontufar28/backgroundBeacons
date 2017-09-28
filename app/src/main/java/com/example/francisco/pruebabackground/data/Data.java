package com.example.francisco.pruebabackground.data;

import com.example.francisco.pruebabackground.models.Recomendation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhovy on 25/09/2017.
 */

public class Data {

    private static List<Recomendation> recomendaciones;
    public static List<Recomendation> getRecomendaciones(){
        if (recomendaciones == null){

            recomendaciones = new ArrayList<>();
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

            recomendaciones.add(recomendation1);
            recomendaciones.add(recomendation2);
            recomendaciones.add(recomendation3);
        }

        return recomendaciones;
    }
}
