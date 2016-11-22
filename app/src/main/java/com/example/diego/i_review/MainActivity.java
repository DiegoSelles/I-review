package com.example.diego.i_review;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> list;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Button btAdd = (Button) this.findViewById(R.id.btadd);
        final ListView listSeries = (ListView) this.findViewById(R.id.listSeries);
        this.list = new ArrayList<String>();
        this.listAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_selectable_list_item,
                this.list
        );

        listSeries.setAdapter(this.listAdapter);

        listSeries.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.setLista(position);
                return false;
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.onAdd();
            }
        });
    }

    //Crea el mneu cuando se pulse en la opcion
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate( R.menu.main_menu, menu);
        return true;
    }

    //Recorre del menu para saber cual es la que hemos escogido
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch ( menuItem.getItemId()){
            case R.id.opSalir:
                finish();
        }
        return true;
    }

    //Funcion que muestra dos opciones y las llama segun cual eligas(modificar serie o eliminar serie)
    public void setLista(final int position){
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage("¿Que desea hacer?");
        dlg.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.modify(position);
            }
        });
        dlg.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.remove(position);
            }
        });
        dlg.create().show();
    }

    //Funcion que añade a la lista una serie
    private void onAdd(){
        final EditText edText = new EditText( this );

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setMessage( "Nombre" );
        builder.setView( edText );
        builder.setPositiveButton( "Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String text = edText.getText().toString();

                MainActivity.this.listAdapter.add( text );
               // MainActivity.this.updateStatus();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    //Funcion que modifica el nombre de una serie
    private void modify( final int position){
        final EditText edText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Nombre");
        builder.setTitle("Modificacion");
        builder.setView(edText);
        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String text = edText.getText().toString();
                list.set(position, text);
                MainActivity.this.listAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    //Funcion que elimina una serie
    private void remove(final int position){
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage("¿Quiere borrar esta serie?");
        dlg.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if( position >= 0) {
                    MainActivity.this.list.remove(position);
                    MainActivity.this.listAdapter.notifyDataSetChanged();
                }
            }
        });
        dlg.setNegativeButton("No", null);
        dlg.create().show();
    }


    public void onPause(){
        super.onPause();
        SharedPreferences.Editor saver = this.getPreferences( Context.MODE_PRIVATE ).edit();
        saver.putStringSet( "list", new HashSet<String>( this.list ) );
        saver.apply();
    }


    public void OnResume(){
        super.onResume();
        SharedPreferences loader = this.getPreferences( Context.MODE_PRIVATE );
        this.list.clear();
        this.list.addAll( loader.getStringSet( "list", new HashSet<String>() ) );
        this.listAdapter.notifyDataSetChanged();
    }
}
