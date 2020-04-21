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

public class WorldAdapter extends BaseAdapter {

    Context context;
    List<Country> countries;

    public WorldAdapter(Context context, List<Country> countries) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.country_detail_alert_layout,parent,false);
        TextView tv_countryName = view.findViewById(R.id.tv_countryName);
        TextView tv_totalCase = view.findViewById(R.id.tv_totalCase);
        TextView tv_newCases = view.findViewById(R.id.tv_newCases);
        TextView tv_totalDeaths = view.findViewById(R.id.tv_totalDeaths);
        TextView tv_newDeaths = view.findViewById(R.id.tv_newDeaths);
        TextView tv_activeCase = view.findViewById(R.id.tv_activeCase);
        TextView tv_criticCase = view.findViewById(R.id.tv_criticCase);
        TextView tv_totCases1MPop = view.findViewById(R.id.tv_totCases1MPop);
        TextView tv_totDeaths1MPop = view.findViewById(R.id.tv_totDeaths1MPop);
        TextView tv_totalRecover = view.findViewById(R.id.tv_totalRecover);

        tv_countryName.setText(countries.get(position).getCountryName());
        tv_totalCase.setText(countries.get(position).getTotalCases());
        tv_newCases.setText(countries.get(position).getNewCases());
        tv_activeCase.setText(countries.get(position).getActiveCases());
        tv_totalDeaths.setText(countries.get(position).getTotalDeaths());
        tv_newDeaths.setText(countries.get(position).getNewDeaths());
        tv_criticCase.setText(countries.get(position).getSeriousCritical());
        tv_totCases1MPop.setText(countries.get(position).getTotCases1MPop());
        tv_totDeaths1MPop.setText(countries.get(position).getTotDeaths1MPop());
        tv_totalRecover.setText(countries.get(position).getTotalRecovered());


        return view;
    }
}
