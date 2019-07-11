package br.ufjf.dcc196.hunterapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufjf.dcc196.hunterapp.*;
import br.ufjf.dcc196.hunterapp.DB.*;
import br.ufjf.dcc196.hunterapp.Model.*;

public class CategoriaDetalheActivity extends AppCompatActivity {

    public EditText titulo;
    public Categoria categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_detalhe);
        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());

        titulo = findViewById(R.id.editTituloCategoriaDetalhe);

        String id = getIntent().getStringExtra("idCategoria");
        categoria = dbHelper.getCategoriaById(id);

        titulo.setText(categoria.getTitulo());

        Button btnSaveDetalheTag = findViewById(R.id.btnSaveDetalheCategoria);

        btnSaveDetalheTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());

                categoria.setTitulo(titulo.getText().toString());
                dbHelper.atualizarCategoria(categoria);

                Intent resultado = new Intent();
                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
