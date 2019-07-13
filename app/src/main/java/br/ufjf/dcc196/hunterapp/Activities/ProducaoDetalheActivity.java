package br.ufjf.dcc196.hunterapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.ufjf.dcc196.hunterapp.*;
import br.ufjf.dcc196.hunterapp.DB.*;
import br.ufjf.dcc196.hunterapp.Model.*;

public class ProducaoDetalheActivity extends AppCompatActivity {

    private EditText titulo;
    private EditText descricao;
    private EditText inicio;
    private EditText fim;
    public Spinner categoria;
    public Long idCandidato;

    final List<String> listaCategoriasNomes = new ArrayList<>();
    final List<Long> listaCategoriasIds = new ArrayList<>();

    public Producao producao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producao_detalhe);

        titulo = findViewById(R.id.editTituloProducaoDetalhe);
        descricao = findViewById(R.id.editDescricaoProducaoDetalhe);
        inicio = findViewById(R.id.editInicioProducaoDetalhe);
        fim = findViewById(R.id.editFimProducaoDetalhe);
        categoria = findViewById(R.id.editCategoriaProducaoDetalhe);

        idCandidato = getIntent().getLongExtra("idCandidato",0);
        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
        Long idProducao = getIntent().getLongExtra("idProducao",0);
        List<Categoria> listaCategorias = dbHelper.getTodasAsCategoriasList();



        for (Categoria c :
                listaCategorias) {
            listaCategoriasNomes.add(c.getTitulo());
            listaCategoriasIds.add(c.getId());
        }

        ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaCategoriasNomes);

        categoria.setAdapter(categoriaAdapter);


        producao = dbHelper.getProducaoById(idProducao);

        titulo.setText(producao.getTitulo());
        descricao.setText(producao.getDescricao());
        inicio.setText(producao.getInicio());
        fim.setText(producao.getFim());
        categoria.setSelection(listaCategoriasIds.indexOf(producao.getCategoriaId()));

        Button btnSaveDetalheProducao = findViewById(R.id.btnSaveDetalheProducao);

        btnSaveDetalheProducao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                producao.setTitulo(titulo.getText().toString());
                producao.setDescricao(descricao.getText().toString());
                producao.setInicio(inicio.getText().toString());
                producao.setFim(fim.getText().toString());
                producao.setCategoriaId(listaCategoriasIds.get(listaCategoriasNomes.indexOf(categoria.getSelectedItem())));

                dbHelper.atualizarProducao(producao);

                Intent resultado = new Intent();

                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
