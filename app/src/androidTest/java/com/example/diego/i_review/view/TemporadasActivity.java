package com.example.diego.i_review.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.diego.i_review.Core.Capitulo;
import com.example.diego.i_review.R;

import java.util.List;

public class TemporadasActivity extends AppCompatActivity {
    private List<Capitulo> list;
    private ArrayAdapter<Capitulo> listAdapter;
    private SeriesApp appCapitulos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporadas);

        //Colocar icono en la Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        Bundle datosEnviados = this.getIntent().getExtras();
        final int idTemporada = datosEnviados.getInt("idTemporada");

        this.appCapitulos = (SeriesApp) this.getApplication();

        Button btAdd = (Button) this.findViewById(R.id.btadd);
        final ListView listCapitulos = (ListView) this.findViewById(R.id.listCapitulos);

        this.list = appCapitulos.getListaCapitulos(idTemporada);


        this.listAdapter = new ArrayAdapter<Capitulo>(
                this,
                android.R.layout.simple_selectable_list_item,
                this.list
        ){
          @Override
            public View getView(int position,View convertView,ViewGroup parent) {
              View view = super.getView(position, convertView, parent);

              List<Capitulo> lista = appCapitulos.getListaCapitulos(idTemporada);
              if (!lista.isEmpty()) {
                  Capitulo capitulo = lista.get(position);
                  int idCapitulo = capitulo.getId();

                  if (appCapitulos.getCapituloById(idCapitulo).getVisto() == 0) {//Si el capitulo esta visto
                      view.setBackgroundColor(Color.WHITE);//Lo desmarco
                  } else {//Si no esta visto
                      view.setBackgroundColor(Color.GREEN);//Lo marco
                  }
              }
              return view;
            }

        };

        listCapitulos.setAdapter( this.listAdapter );

        //Al hacer clic marco o dermarco el capítulo según lo haya visto o no.
        listCapitulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Capitulo capitulo = appCapitulos.getListaCapitulos(idTemporada).get( position );
                int idCapitulo = capitulo.getId();
                View viewList = listCapitulos.getChildAt(position - listCapitulos.getFirstVisiblePosition());

                if(appCapitulos.getCapituloById(idCapitulo).getVisto() == 1){//Si el capitulo esta visto
                    viewList.setBackgroundColor(Color.WHITE);//Lo desmarco
                    appCapitulos.capituloVisto(idCapitulo,0);//Pongo en la bd que esta desmarcado
                }else{//Si no esta visto
                    viewList.setBackgroundColor(Color.GREEN);//Lo marco
                    appCapitulos.capituloVisto(idCapitulo,1);//Pongo en la bd que esta marcado
                }
                TemporadasActivity.this.list = appCapitulos.getListaCapitulos(idTemporada);
                TemporadasActivity.this.listAdapter.notifyDataSetChanged();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemporadasActivity.this.addCapitulo(idTemporada);
            }
        });

        //Al hacer un clic largo entro en la otra actividad donde realizo comentarios del capitulo.
        listCapitulos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Capitulo capitulo = appCapitulos.getListaCapitulos(idTemporada).get( position );
                int idCapitulo = capitulo.getId();
                TemporadasActivity.this.addComentario(idCapitulo);
                return false;
            }
        });

        if(listAdapter.getCount() != 0){
            Toast t =Toast.makeText(getApplicationContext(),"Marca el capítulo o comentalo con una pulsación larga.",Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER,0,0);
            t.show();
        }else{
            Toast t =Toast.makeText(getApplicationContext(),"¿Cuántos capítulos tiene la temporada?",Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER,0,0);
            t.show();
        }
    }

    //Crea el menu cuando se pulse en la opcion
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate(R.menu.capitulos_menu, menu );
        return true;
    }

    //Recorre el menu para saber cual es la opcion que hemos escogido,eliminar los capítulos o volver a atrás.
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.opDelete:
                Bundle datosEnviados = this.getIntent().getExtras();
                final int idTemporada = datosEnviados.getInt("idTemporada");
                appCapitulos.eliminarCapitulos(idTemporada);
                this.list = appCapitulos.getListaCapitulos(idTemporada);
                TemporadasActivity.this.listAdapter.notifyDataSetChanged();
            case R.id.opSalir:
                finish();
        }
        return true;
    }

    //Funcion que añade a la lista capítulos según una cantidad indicada.
    public void addCapitulo(final int idTemporada){
        final EditText edText = new EditText( this );

        edText.setInputType(InputType.TYPE_CLASS_NUMBER);

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setMessage( "¿Cuantos capitulos quieres añadir?" );
        builder.setView( edText );

        builder.setPositiveButton( "Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String text = edText.getText().toString();
                final int cantidad = Integer.parseInt(text);
                appCapitulos.insertarCapitulo( cantidad, idTemporada );
                TemporadasActivity.this.list = appCapitulos.getListaCapitulos(idTemporada);
                TemporadasActivity.this.listAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    //Función que es llamada al realizar una pulsación larga, moviendonos de actividad para poder añadir un comentario.
    public void addComentario(final int idCapitulo){
            Intent intent = new Intent(TemporadasActivity.this,CommentActivity.class);
            intent.putExtra("idCapitulo",idCapitulo);
            TemporadasActivity.this.startActivity(intent);
    }
}
