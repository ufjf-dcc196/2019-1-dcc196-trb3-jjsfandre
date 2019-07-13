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

public class AtividadeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener  {

    public AtividadeAdapter aAdapter;
    public static final int REQUEST_DETALHE_ATIVIDADE = 100;
    public static final int REQUEST_NOVO_ATIVIDADE = 200;
    private Long idProducao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        idProducao = getIntent().getLongExtra("idProducao",0);

        Switch switch1 = (Switch)findViewById(R.id.switchExcluirAtividade);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    aAdapter.setOnItemClickListener(listenerDeleteAtividade);
                else
                    aAdapter.setOnItemClickListener(listenerEditAtividade);
            }
        });

        final RecyclerView rv = findViewById(R.id.rvAtividades);
        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());

        aAdapter = new AtividadeAdapter(dbHelper.getCursorTodasAsAtividadesByProducaoId(idProducao),getApplicationContext());
        aAdapter.setOnItemClickListener(listenerEditAtividade);
        rv.setAdapter(aAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    private AtividadeAdapter.OnItemClickListener listenerEditAtividade =
            new AtividadeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                    TextView txtId = (TextView) itemView.findViewById(R.id.txtIdAtividade);

                    Intent intent = new Intent(AtividadeActivity.this, AtividadeDetalheActivity.class);
                    intent.putExtra("idAtividade", Long.parseLong(txtId.getText().toString()));

                    startActivityForResult(intent, REQUEST_DETALHE_ATIVIDADE);
                }
            };

    private AtividadeAdapter.OnItemClickListener listenerDeleteAtividade =
            new AtividadeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                    TextView txtId = (TextView) itemView.findViewById(R.id.txtIdAtividade);
                    TextView txtDescricao = (TextView) itemView.findViewById(R.id.txtDescricaoAtividade);
                    dbHelper.deleteAtividadeById(txtId.getText().toString(), txtDescricao.getText().toString());

                    aAdapter.setCursor(dbHelper.getCursorTodasAsAtividadesByProducaoId(idProducao));
                    aAdapter.notifyItemRemoved(position);
                }
            };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            switch (requestCode){
                case REQUEST_DETALHE_ATIVIDADE:
                    if (resultCode == Activity.RESULT_OK) {
                        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                        aAdapter.setCursor(dbHelper.getCursorTodasAsAtividadesByProducaoId(idProducao));
                    }
                    break;
                case REQUEST_NOVO_ATIVIDADE:
                    if (resultCode == Activity.RESULT_OK) {
                        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                        aAdapter.setCursor(dbHelper.getCursorTodasAsAtividadesByProducaoId(idProducao));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void showMenuAtividade(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_atividade);
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.addAtividade:
                adicionarAtividade();
                return true;
            default:
                return false;
        }

    }

    private void adicionarAtividade(){

        Intent intent = new Intent(AtividadeActivity.this, AtividadeNovoActivity.class);
        intent.putExtra("idProducao", idProducao);
        startActivityForResult(intent, REQUEST_NOVO_ATIVIDADE);
    }
}
