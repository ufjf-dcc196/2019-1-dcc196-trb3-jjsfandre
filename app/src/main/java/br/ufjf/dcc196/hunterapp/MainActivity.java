package br.ufjf.dcc196.hunterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.ufjf.dcc196.hunterapp.Activities.*;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CATEGORIAS = 100;
    public static final int REQUEST_CANDIDATOS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOpenCandidatos = findViewById(R.id.btnOpenCandidatos);
        Button btnOpenCategorias = findViewById(R.id.btnOpenCategorias);

        btnOpenCandidatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CandidatoActivity.class);

                startActivityForResult(intent, REQUEST_CANDIDATOS);
            }
        });
        btnOpenCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriaActivity.class);

                startActivityForResult(intent, REQUEST_CATEGORIAS);
            }
        });

    }
}
