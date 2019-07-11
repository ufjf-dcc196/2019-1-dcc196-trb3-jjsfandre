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

public class CandidatoActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener   {

    public CandidatoAdapter cAdapter;
    public static final int REQUEST_DETALHE_CANDIDATO = 500;
    public static final int REQUEST_NOVO_CANDIDATO = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato);

        Switch switch1 = (Switch)findViewById(R.id.switchExcluirCandidato);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    cAdapter.setOnItemClickListener(listenerDeleteCandidato);
                else
                    cAdapter.setOnItemClickListener(listenerEditCandidato);
            }
        });

        final RecyclerView rv = findViewById(R.id.rvCandidatos);
        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());

        cAdapter = new CandidatoAdapter(dbHelper.getCursorTodosOsCandidatos(),getApplicationContext());
        cAdapter.setOnItemClickListener(listenerEditCandidato);
        rv.setAdapter(cAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    private CandidatoAdapter.OnItemClickListener listenerEditCandidato =
            new CandidatoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                    TextView txtId = (TextView) itemView.findViewById(R.id.txtIdCandidato);

                    Intent intent = new Intent(CandidatoActivity.this, CandidatoDetalheActivity.class);
                    intent.putExtra("idCandidato", txtId.getText().toString());

                    startActivityForResult(intent, REQUEST_DETALHE_CANDIDATO);
                }
            };

    private CandidatoAdapter.OnItemClickListener listenerDeleteCandidato =
            new CandidatoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                    TextView txtId = (TextView) itemView.findViewById(R.id.txtIdCandidato);
                    TextView txtNome = (TextView) itemView.findViewById(R.id.txtNomeCandidato);
                    dbHelper.deleteCandidatoById(txtId.getText().toString(), txtNome.getText().toString());

                    cAdapter.setCursor(dbHelper.getCursorTodosOsCandidatos());
                    cAdapter.notifyItemRemoved(position);
                }
            };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            switch (requestCode){
                case REQUEST_DETALHE_CANDIDATO:
                    if (resultCode == Activity.RESULT_OK) {
                        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                        cAdapter.setCursor(dbHelper.getCursorTodosOsCandidatos());
                    }
                    break;
                case REQUEST_NOVO_CANDIDATO:
                    if (resultCode == Activity.RESULT_OK) {
                        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                        cAdapter.setCursor(dbHelper.getCursorTodosOsCandidatos());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void showMenuCandidato(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_candidato);
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.addCandidato:
                adicionarCandidato();
                return true;
            default:
                return false;
        }

    }

    private void adicionarCandidato(){

        Intent intent = new Intent(CandidatoActivity.this, CandidatoNovoActivity.class);

        startActivityForResult(intent, REQUEST_NOVO_CANDIDATO);
    }
}
