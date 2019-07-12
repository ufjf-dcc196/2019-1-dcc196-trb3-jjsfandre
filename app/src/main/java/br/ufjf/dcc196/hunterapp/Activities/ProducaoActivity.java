

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

public class ProducaoActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener   {

    public ProducaoAdapter cAdapter;
    public static final int REQUEST_DETALHE_PRODUCAO = 300;
    public static final int REQUEST_NOVO_PRODUCAO = 400;
    private Long idCandidato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producao);

        Switch switch1 = (Switch)findViewById(R.id.switchExcluirProducao);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    cAdapter.setOnItemClickListener(listenerDeleteProducao);
                else
                    cAdapter.setOnItemClickListener(listenerEditProducao);
            }
        });

        final RecyclerView rv = findViewById(R.id.rvProducoes);
        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());

        cAdapter = new ProducaoAdapter(dbHelper.getCursorTodasAsProducoes(),getApplicationContext());
        cAdapter.setOnItemClickListener(listenerEditProducao);
        rv.setAdapter(cAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    private ProducaoAdapter.OnItemClickListener listenerEditProducao =
            new ProducaoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                    TextView txtId = (TextView) itemView.findViewById(R.id.txtIdProducao);

                    Intent intent = new Intent(ProducaoActivity.this, ProducaoDetalheActivity.class);
                    intent.putExtra("idProducao", txtId.getText().toString());

                    startActivityForResult(intent, REQUEST_DETALHE_PRODUCAO);
                }
            };

    private ProducaoAdapter.OnItemClickListener listenerDeleteProducao =
            new ProducaoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                    TextView txtId = (TextView) itemView.findViewById(R.id.txtIdProducao);
                    TextView txtTitulo = (TextView) itemView.findViewById(R.id.txtTituloProducao);
                    dbHelper.deleteProducaoById(txtId.getText().toString(), txtTitulo.getText().toString());

                    cAdapter.setCursor(dbHelper.getCursorTodasAsProducoes());
                    cAdapter.notifyItemRemoved(position);
                }
            };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            switch (requestCode){
                case REQUEST_DETALHE_PRODUCAO:
                    if (resultCode == Activity.RESULT_OK) {
                        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                        cAdapter.setCursor(dbHelper.getCursorTodasAsProducoes());
                    }
                    break;
                case REQUEST_NOVO_PRODUCAO:
                    if (resultCode == Activity.RESULT_OK) {
                        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                        cAdapter.setCursor(dbHelper.getCursorTodasAsProducoes());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void showMenuProducao(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_producao);
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.addProducao:
                adicionarProducao();
                return true;
            default:
                return false;
        }

    }

    private void adicionarProducao(){

        Intent intent = new Intent(ProducaoActivity.this, ProducaoNovoActivity.class);

        startActivityForResult(intent, REQUEST_NOVO_PRODUCAO);
    }
}
