package com.google.android.gms.samples.vision.ocrreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class MedicineActivity extends AppCompatActivity {

    RecyclerView similarsRV;
    TextView nameTV, priceTV, genericTV;

    Medicine medicine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_activity);

        if (getIntent().hasExtra("medicine"))
            medicine = getIntent().getParcelableExtra("medicine");
        else
            finish();


        similarsRV = findViewById(R.id.similarsRV);
        nameTV = findViewById(R.id.nameTV);
        genericTV = findViewById(R.id.genericTV);
        priceTV = findViewById(R.id.priceTV);

        nameTV.setText(medicine.drugName);
        genericTV.setText(medicine.Generic);
        priceTV.setText("Price: " + medicine.GenericPrice + " EGP");

        similarsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        similarsRV.setAdapter(new SimilarsRVAdapter(this, medicine));
    }
}
