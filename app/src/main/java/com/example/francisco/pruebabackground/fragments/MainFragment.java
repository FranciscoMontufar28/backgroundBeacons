package com.example.francisco.pruebabackground.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.francisco.pruebabackground.BeaconRecomendation;
import com.example.francisco.pruebabackground.R;
import com.example.francisco.pruebabackground.adapter.RecomendationAdapter;
import com.example.francisco.pruebabackground.data.Data;
import com.example.francisco.pruebabackground.databinding.FragmentMainBinding;


public class MainFragment extends Fragment {

    public static MainFragment instance(){
        return new MainFragment();
    }

    FragmentMainBinding binding;
    RecomendationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        adapter = new RecomendationAdapter(getLayoutInflater(null), Data.getRecomendaciones());
        binding.recycler.setAdapter(adapter);
        //binding.recycler.setLayoutManager(new GridLayoutManager(getContext(),2)); para hacer una grilla
        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }
}
