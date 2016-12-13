package com.example.diego.i_review.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.diego.i_review.R;

import java.util.ArrayList;
import java.util.HashSet;

public class DeseadasActivity extends AppCompatActivity {
    private ArrayList<String> list;
    private ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deseadas);

        //Colocar icono en la Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        Toast.makeText(getApplicationContext(),"Añade las series que te gustaría ver.",Toast.LENGTH_LONG).show();

        Button btAdd = (Button) this.findViewById(R.id.btadd);
        final ListView lvlist = (ListView) this.findViewById(R.id.listSeries);
        this.list = new ArrayList<String>();
        this.listAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_selectable_list_item,
                this.list
        );

        lvlist.setAdapter(this.listAdapter);

        lvlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (position >= 0) {
                    DeseadasActivity.this.list.remove(position);
                    DeseadasActivity.this.listAdapter.notifyDataSetChanged();
                }

                return false;
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeseadasActivity.this.onAdd();
            }
        });
    }

    //Función que añade a la lista un string que será el nombre de de una serie que queramos ver en el futuro.
    public void onAdd(){
        final EditText edItem = new EditText(this);

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("Nueva Serie");
        dlg.setView(edItem);
        dlg.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String text = edItem.getText().toString();

                DeseadasActivity.this.listAdapter.add(text);
            }
        });
        dlg.setNegativeButton("Cancel", null);
        dlg.create().show();
    }

    //Guardamos los datos
    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences.Editor saver = this.getPreferences(Context.MODE_PRIVATE).edit();

        StringBuilder builder = new StringBuilder();
        for(String item : this.list){
            builder.append(item);
            builder.append(',');
        }
        saver.putString("items", builder.toString());
        saver.apply();//Cambiamos los datos para que realmente permanezcan.
    }

    //Recuperamos los datos.
    @Override
    public void onResume(){
        super.onResume();

        SharedPreferences prefs = this.getPreferences( Context.MODE_PRIVATE );
        String codedItems = prefs.getString("items", ",");
        String[] items = codedItems.split(",");
        for(int i = 0;i< (items.length); i++){
            this.list.add(items [ i ]);
        }
        this.listAdapter.notifyDataSetChanged();
    }
}
