package com.example.francisco.pruebabackground.attrs;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by jhovy on 24/09/2017.
 */

public class Global {

    @BindingAdapter("app:imgUrl")
    public static void loadImage(ImageView view, String url){
        Context context = view.getContext();
        Uri uri = Uri.parse(url);
        Picasso.with(context).load(uri).into(view);

    }
}
