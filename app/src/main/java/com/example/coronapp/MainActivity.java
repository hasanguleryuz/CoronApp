package com.example.coronapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronapp.Adapter.CountryListAdapter;
import com.example.coronapp.Adapter.WorldAdapter;
import com.example.coronapp.Comporators.CountryNameSorter;
import com.example.coronapp.Comporators.TotalCaseSorter;
import com.example.coronapp.Comporators.TotalDeathSorter;
import com.example.coronapp.Model.Country;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView tv_date;
    GridView gv_list, gv_world;
    ArrayList<Country> countries;
    ArrayList<Country> world;
    CountryListAdapter countryListAdapter;
    WorldAdapter worldAdapter;
    EditText et_search;
    Button btn_sort;
    int WactiveCases, WnewCases, WnewDeaths, WseriousCritical, WtotCases1MPop, WtotDeaths1MPop, WtotalCases, WtotalDeaths, WtotalRecovered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assign();
        showData();
        findDate();
        clickGridView();
        editTextAction();
        clickBtnSort();
    }

    private void assign() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        btn_sort = findViewById(R.id.btn_sort);
        gv_list = findViewById(R.id.gv_list);
        gv_world = findViewById(R.id.gv_world);
        tv_date = findViewById(R.id.tv_date);
        et_search = findViewById(R.id.et_search);
        countries = new ArrayList<>();
        world = new ArrayList<>();
        worldAdapter = new WorldAdapter(this, world);
        gv_world.setAdapter(worldAdapter);
        countryListAdapter = new CountryListAdapter(this, countries);
        gv_list.setAdapter(countryListAdapter);

    }

    private void clickBtnSort() {
        btn_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSortAlertDialog();
            }
        });
    }

    private void openSortAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.sort_alert_layout, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        Button btn_countryNameSort = view.findViewById(R.id.btn_countryNameSort);
        Button btn_totalCaseSort = view.findViewById(R.id.btn_totalCaseSort);
        Button btn_totalDeathSort = view.findViewById(R.id.btn_totalDeathSort);

        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog dialog = alert.create();

        btn_countryNameSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(countries, new CountryNameSorter());
                countryListAdapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });
        btn_totalCaseSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(countries, new TotalCaseSorter());
                countryListAdapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });
        btn_totalDeathSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(countries, new TotalDeathSorter() {
                });
                countryListAdapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void editTextAction() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String desiredCountryName = et_search.getText().toString();
                    if (desiredCountryName.equals("")) {
                        Toast.makeText(getApplicationContext(), "Lütfen bir ülke ismi giriniz...", Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < countries.size(); i++) {
                            if (countries.get(i).getCountryName().toLowerCase().equals(desiredCountryName.toLowerCase())) {
                                alertDialogOpen(i);
                                break;
                            } else if (i == countries.size() - 1) {
                                Toast.makeText(getApplicationContext(), "Aradığınız ülke bulunamadı...", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    return true;
                }
                return false;
            }

        });
    }


    private void findDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("    EEEE, dd/MMM/yyyy, HH:mm", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        tv_date.setText(currentDateandTime);
    }

    private void showWorldData() {
        world.clear();
        world.add(new Country(String.valueOf(WactiveCases), "Dünya", String.valueOf(WnewCases), String.valueOf(WnewDeaths), String.valueOf(WseriousCritical), String.valueOf(WtotCases1MPop), String.valueOf(WtotDeaths1MPop), String.valueOf(WtotalCases), String.valueOf(WtotalDeaths), String.valueOf(WtotalRecovered)));
        worldAdapter.notifyDataSetChanged();
    }

    private void showData() {
        myRef.child("Countries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                countries.clear();
                clearWorldData();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Country country = snapshot.getValue(Country.class);
                    countries.add(new Country(country.getActiveCases(), country.getCountryName(), country.getNewCases(), country.getNewDeaths(), country.getSeriousCritical(), country.getTotCases1MPop(), country.getTotDeaths1MPop(), country.getTotalCases(), country.getTotalDeaths(), country.getTotalRecovered()));
                    countryListAdapter.notifyDataSetChanged();
                    WactiveCases += Integer.valueOf(country.getActiveCases());
                    WnewCases += Integer.valueOf(country.getNewCases());
                    WnewDeaths += Integer.valueOf(country.getNewDeaths());
                    WseriousCritical += Integer.valueOf(country.getSeriousCritical());
                    WtotCases1MPop += Integer.valueOf(country.getTotCases1MPop());
                    WtotDeaths1MPop += Integer.valueOf(country.getTotDeaths1MPop());
                    WtotalCases += Integer.valueOf(country.getTotalCases());
                    WtotalDeaths += Integer.valueOf(country.getTotalDeaths());
                    WtotalRecovered += Integer.valueOf(country.getTotalRecovered());
                }
                showWorldData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void clickGridView() {
        gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialogOpen(position);
            }
        });
    }

    private void alertDialogOpen(int position) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.country_detail_alert_layout, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

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


        alert.setView(view);
        alert.setCancelable(true);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void clearWorldData() {
        WactiveCases = 0;
        WnewCases = 0;
        WnewDeaths = 0;
        WseriousCritical = 0;
        WtotCases1MPop = 0;
        WtotDeaths1MPop = 0;
        WtotalCases = 0;
        WtotalDeaths = 0;
        WtotalRecovered = 0;
    }
}
