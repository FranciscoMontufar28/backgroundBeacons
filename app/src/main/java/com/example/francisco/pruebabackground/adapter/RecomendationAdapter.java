package com.example.francisco.pruebabackground.adapter;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.francisco.pruebabackground.R;
import com.example.francisco.pruebabackground.databinding.TemplateRecomendationBinding;
import com.example.francisco.pruebabackground.models.Recomendation;

import java.util.List;

/**
 * Created by jhovy on 22/09/2017.
 */


public class RecomendationAdapter extends RecyclerView.Adapter<RecomendationAdapter.RecomendationHolder>{

    LayoutInflater inflater;
    List<Recomendation> data;


    public RecomendationAdapter(LayoutInflater inflater, List<Recomendation> data) {
        this.inflater = inflater;
        this.data = data;
    }

    @Override
    public RecomendationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.template_recomendation, parent, false);
        return new RecomendationHolder(v);
    }

    @Override
    public void onBindViewHolder(RecomendationHolder holder, int position) {
        holder.binding.setRecomendation(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    //region RecomendationHolder
    static class RecomendationHolder extends RecyclerView.ViewHolder{

        TemplateRecomendationBinding binding;

        public RecomendationHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
    //endregion

/*public class RecomendationAdapter extends BaseAdapter{

    LayoutInflater inflater;
    List<Recomendation> data;

    public RecomendationAdapter(LayoutInflater inflater, List<Recomendation> data) {
        this.inflater = inflater;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        /*View v = view;

        if (v== null)
            v = inflater.inflate(R.layout.template_recomendation, viewGroup, false);

        Recomendation recomendation = data.get(i);

        TextView title = (TextView) v.findViewById(R.id.id_title_recomendation);
        TextView description = (TextView) v.findViewById(R.id.id_recomendation_desciption);

        title.setText(recomendation.getName());
        description.setText(recomendation.getDescription());


        return v;

        /// using binging
        TemplateRecomendationBinding binding = TemplateRecomendationBinding.inflate(inflater);
        binding.setRecomendation(data.get(i));
        return binding.getRoot();
    }*/
}
