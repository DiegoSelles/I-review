package com.example.diego.i_review.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.diego.i_review.Core.Serie;
import com.example.diego.i_review.Core.Temporada;
import com.example.diego.i_review.R;

import java.util.ArrayList;

public class SeriesActivity extends AppCompatActivity {
    private ArrayList<String> list;
    private ArrayAdapter<Temporada> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        Button btAdd = (Button) this.findViewById(R.id.btadd);
        final ListView listTemporadas = (ListView) this.findViewById(R.id.listTemporadas);


        listTemporadas.setAdapter(this.listAdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeriesActivity.this.addTemporada();
            }
        });
    }

    public void addTemporada(){
        final EditText edText = new EditText( this );

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setMessage( "¿Cuantas temporadas quieres añadir?" );
        builder.setView( edText );
        builder.setPositiveButton( "Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
}
