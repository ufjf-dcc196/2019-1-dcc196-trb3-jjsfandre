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

public class ProducaoNovoActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_producao_novo);

        titulo = findViewById(R.id.editTituloProducaoNovo);
        descricao = findViewById(R.id.editDescricaoProducaoNovo);
        inicio = findViewById(R.id.editInicioProducaoNovo);
        fim = findViewById(R.id.editFimProducaoNovo);
        categoria = findViewById(R.id.editCategoriaProducaoNovo);

        idCandidato = getIntent().getLongExtra("idCandidato",0);

        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
        List<Categoria> listaCategorias = dbHelper.getTodasAsCategoriasList();
        for (Categoria c :
                listaCategorias) {
            listaCategoriasNomes.add(c.getTitulo());
            listaCategoriasIds.add(c.getId());
        }

        ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaCategoriasNomes);

        categoria.setAdapter(categoriaAdapter);



        Button btnSaveNovoProducao = findViewById(R.id.btnSaveNovoProducao);

        btnSaveNovoProducao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                producao = new Producao();
                producao.setTitulo(titulo.getText().toString());
                producao.setDescricao(descricao.getText().toString());
                producao.setInicio(inicio.getText().toString());
                producao.setFim(fim.getText().toString());
                producao.setCandidatoId(idCandidato);
                producao.setCategoriaId(listaCategoriasIds.get(listaCategoriasNomes.indexOf(categoria.getSelectedItem())));

                dbHelper.inserirProducao(producao);

                Intent resultado = new Intent();

                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
