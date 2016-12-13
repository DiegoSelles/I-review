package com.example.diego.i_review.view;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.diego.i_review.Core.Temporada;
import com.example.diego.i_review.R;

import java.util.List;

public class SeriesActivity extends AppCompatActivity {
    private List<Temporada> list;
    private ArrayAdapter<Temporada> listAdapter;
    private SeriesApp appTemporada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        //Colocar icono en la Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        Bundle datosEnviados = this.getIntent().getExtras();
        final int idSerie = datosEnviados.getInt("idSerie");

        this.appTemporada = (SeriesApp) this.getApplication();

        Button btAdd = (Button) this.findViewById(R.id.btadd);
        final ListView listTemporadas = (ListView) this.findViewById(R.id.listTemporadas);

        this.list = appTemporada.getListaTemporadas(idSerie);

        this.listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_selectable_list_item,
                this.list
        );

        listTemporadas.setAdapter(this.listAdapter);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeriesActivity.this.addTemporada(idSerie);
            }
        });

        //Al hacer clic en la Temporada pasamos al activity donde se gestionan los capítulos.
        listTemporadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SeriesActivity.this,TemporadasActivity.class);
                Temporada temporadas = appTemporada.getListaTemporadas(idSerie).get( position );
                int idTemporada = temporadas.getId();
                intent.putExtra("idTemporada",idTemporada);
                SeriesActivity.this.startActivity(intent);
            }
        });

        if(listAdapter.getCount() == 0){
            Toast t =Toast.makeText(getApplicationContext(),"¿Cuántas temporadas tiene la serie?",Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER,0,0);
            t.show();
        }

    }
    //Crea el menu cuando se pulse en la opcion.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate(R.menu.temporadas_menu, menu );
        return true;
    }

    //Recorre el menu para saber cual es la opcion que hemos escogido,borrar temporadas o ir a atrás.
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.opDelete:
                Bundle datosEnviados = this.getIntent().getExtras();
                final int idSerie = datosEnviados.getInt("idSerie");
                appTemporada.eliminarTemporadas(idSerie);
                SeriesActivity.this.listAdapter.notifyDataSetChanged();
            case R.id.opSalir:
                finish();
        }
        return true;
    }

    //Funcion que añade a la lista temporadas según una cantidad indicada.
    public void addTemporada(final int idSerie){
        final EditText edText = new EditText( this );

        edText.setInputType(InputType.TYPE_CLASS_NUMBER);//Solo números

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setMessage( "¿Cuantas temporadas quieres añadir?" );
        builder.setView( edText );

        builder.setPositiveButton( "Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String text = edText.getText().toString();
                final int cantidad = Integer.parseInt(text);
                appTemporada.insertarTemporada( cantidad, idSerie );
                SeriesActivity.this.list = appTemporada.getListaTemporadas(idSerie);
                SeriesActivity.this.listAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
}
