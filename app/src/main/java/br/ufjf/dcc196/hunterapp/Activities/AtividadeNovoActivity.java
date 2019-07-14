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

public class AtividadeNovoActivity extends AppCompatActivity {

    private EditText descricao;
    private EditText data;
    private EditText horas;
    public Long idAtividade;
    public Long idProducao;

    public Atividade atividade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_novo);

        descricao = findViewById(R.id.editDescricaoAtividadeNovo);
        data = findViewById(R.id.editDataAtividadeNovo);
        horas = findViewById(R.id.editHorasAtividadeNovo);

        idAtividade = getIntent().getLongExtra("idAtividade",0);
        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
        idProducao = getIntent().getLongExtra("idProducao",0);



        Button btnSaveNovoAtividade = findViewById(R.id.btnSaveNovoAtividade);

        btnSaveNovoAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                atividade = new Atividade();
                atividade.setDescricao(descricao.getText().toString());
                atividade.setData(data.getText().toString());
                atividade.setHoras(Double.parseDouble(horas.getText().toString()));
                atividade.setProducaoId(idProducao);

                dbHelper.inserirAtividade(atividade);

                Intent resultado = new Intent();

                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
