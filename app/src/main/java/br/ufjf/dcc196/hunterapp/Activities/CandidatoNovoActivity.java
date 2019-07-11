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

public class CandidatoNovoActivity extends AppCompatActivity {

    private EditText nome;
    private EditText nascimento;
    private EditText telefone;
    private EditText perfil;
    private EditText email;
    public Candidato candidato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato_novo);

        nome = findViewById(R.id.editNomeCandidatoNovo);
        nascimento = findViewById(R.id.editNascimentoCandidatoNovo);
        telefone = findViewById(R.id.editTelefoneCandidatoNovo);
        perfil = findViewById(R.id.editPerfilCandidatoNovo);
        email = findViewById(R.id.editEmailCandidatoNovo);

        Button btnSaveNovoCandidato = findViewById(R.id.btnSaveNovoCandidato);

        btnSaveNovoCandidato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                candidato = new Candidato();
                candidato.setNome(nome.getText().toString());
                candidato.setNascimento(nascimento.getText().toString());
                candidato.setTelefone(telefone.getText().toString());
                candidato.setPerfil(perfil.getText().toString());
                candidato.setEmail(email.getText().toString());

                dbHelper.inserirCandidato(candidato);

                Intent resultado = new Intent();

                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
