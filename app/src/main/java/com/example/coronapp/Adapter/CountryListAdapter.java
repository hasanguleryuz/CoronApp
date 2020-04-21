package com.example.coronapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.coronapp.Model.Country;
import com.example.coronapp.R;

import java.util.List;

public class CountryListAdapter extends BaseAdapter {

    Context context;
    List<Country> countries;

    public CountryListAdapter(Context context, List<Country> countries) {
        this.context = context;
        this.countries = countries;
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.country_list_layout,parent,false);

        TextView tv_countryName = convertView.findViewById(R.id.tv_countryName);
        TextView tv_totalCase = convertView.findViewById(R.id.tv_totalCase);
        TextView tv_totalDeaths = convertView.findViewById(R.id.tv_totalDeaths);

        tv_countryName.setText(countries.get(position).getCountryName());
        tv_totalCase.setText(countries.get(position).getTotalCases());
        tv_totalDeaths.setText(countries.get(position).getTotalDeaths());

        return convertView;
    }
}
