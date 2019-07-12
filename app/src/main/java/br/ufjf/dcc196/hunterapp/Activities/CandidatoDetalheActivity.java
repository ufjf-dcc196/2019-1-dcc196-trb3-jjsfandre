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

public class CandidatoDetalheActivity extends AppCompatActivity {

    private EditText nome;
    private EditText nascimento;
    private EditText telefone;
    private EditText perfil;
    private EditText email;
    public Candidato candidato;
    public Long idCandidato;

    public static final int REQUEST_LISTAR_PRODUCAO = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato_detalhe);
        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());

        nome = findViewById(R.id.editNomeCandidatoDetalhe);
        nascimento = findViewById(R.id.editNascimentoCandidatoDetalhe);
        telefone = findViewById(R.id.editTelefoneCandidatoDetalhe);
        perfil = findViewById(R.id.editPerfilCandidatoDetalhe);
        email = findViewById(R.id.editEmailCandidatoDetalhe);

        idCandidato = Long.parseLong(getIntent().getStringExtra("idCandidato"));
        candidato = dbHelper.getCandidatoById(idCandidato);

        nome.setText(candidato.getNome());
        nascimento.setText(candidato.getNascimento());
        telefone.setText(candidato.getTelefone());
        perfil.setText(candidato.getPerfil());
        email.setText(candidato.getEmail());

        Button btnSaveDetalheCandidato = findViewById(R.id.btnSaveDetalheCandidato);

        btnSaveDetalheCandidato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());

                candidato.setNome(nome.getText().toString());
                candidato.setNascimento(nascimento.getText().toString());
                candidato.setTelefone(telefone.getText().toString());
                candidato.setPerfil(perfil.getText().toString());
                candidato.setEmail(email.getText().toString());
                dbHelper.atualizarCandidato(candidato);

                Intent resultado = new Intent();
                setResult(RESULT_OK, resultado);
                finish();
            }
        });

        Button btnListarProducaoPorCandidato = findViewById(R.id.btnListarProducaoPorCandidato);

        btnListarProducaoPorCandidato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CandidatoDetalheActivity.this, ProducaoActivity.class);
                intent.putExtra("idCandidato", idCandidato);

                startActivityForResult(intent, REQUEST_LISTAR_PRODUCAO);
            }
        });
    }
}
