package com.example.diego.i_review.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.diego.i_review.R;

public class CommentActivity extends AppCompatActivity {
    private SeriesApp appCapitulos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //Colocar icono en la Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        this.appCapitulos = (SeriesApp) this.getApplication();

        Bundle datosEnviados = this.getIntent().getExtras();
        final int idCapitulo = datosEnviados.getInt("idCapitulo");

        final Button btAdd = (Button) this.findViewById(R.id.btadd);
        final TextView title = (TextView) this.findViewById(R.id.titulo);
        final TextView coment = (TextView) this.findViewById(R.id.comentario);

        try{
            StringBuilder cabecera = new StringBuilder();
            String titulo = appCapitulos.getCapituloById(idCapitulo).getNombre();
            cabecera.append(titulo);
            int numCap = appCapitulos.getCapituloById(idCapitulo).getNumCap();
            cabecera.append("X");
            cabecera.append(numCap);
            String comentario = appCapitulos.getCapituloById(idCapitulo).getComentario();

            title.setText(cabecera.toString());
            coment.setText(comentario);

        }catch (NumberFormatException fmt) {
            title.setText(R.string.label_default_result);
            coment.setText(R.string.label_default_result);
        }

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.this.addComentario(idCapitulo);
            }
        });
    }

    //Función que añade un comentario.
    public void addComentario(final int idCapitulo){
        final EditText edText = new EditText(this);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Escriba su comentario");
        builder.setView(edText);
        final TextView coment = (TextView) this.findViewById(R.id.comentario);

        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String text = edText.getText().toString();
                appCapitulos.añadirComentario(idCapitulo, text);
                coment.setText(edText.getText());
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
}
