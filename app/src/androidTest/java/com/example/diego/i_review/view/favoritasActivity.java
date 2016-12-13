package com.example.diego.i_review.view;

import android.net.http.SslError;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import com.example.diego.i_review.R;

public class favoritasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritas);

        //Colocar icono en la Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        WebView wvView =(WebView) this.findViewById(R.id.wvView);
        wvView.getSettings().setJavaScriptEnabled(true);

        wvView.loadUrl("http://www.sensacine.com/series/top/");
    }

}
