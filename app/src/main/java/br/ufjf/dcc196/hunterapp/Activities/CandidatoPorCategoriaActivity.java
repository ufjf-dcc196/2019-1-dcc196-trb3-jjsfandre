package br.ufjf.dcc196.hunterapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

import br.ufjf.dcc196.hunterapp.*;
import br.ufjf.dcc196.hunterapp.Adapters.*;
import br.ufjf.dcc196.hunterapp.DB.HunterAppDBHelper;

public class CandidatoPorCategoriaActivity extends AppCompatActivity {

    public CandidatoPorCategoriaAdapter cAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato_por_categoria);


        final RecyclerView rv = findViewById(R.id.rvCandidatosPorCategoria);
        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());

        Long idCategoria = getIntent().getLongExtra("idCategoria",0);

        cAdapter = new CandidatoPorCategoriaAdapter(dbHelper.getListCandidatosPorCategoria(idCategoria),getApplicationContext());
        rv.setAdapter(cAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }
}
