package br.ufjf.dcc196.hunterapp.Activities;

import android.content.Intent;
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

public class CategoriaActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener  {

    public CategoriaAdapter cAdapter;
    public static final int REQUEST_DETALHE_CATEGORIA = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        Switch switch1 = (Switch)findViewById(R.id.switchExcluirCategoria);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    cAdapter.setOnItemClickListener(listenerDeleteCategoria);
                else
                    cAdapter.setOnItemClickListener(listenerEditCategoria);
            }
        });

        final RecyclerView rv = findViewById(R.id.rvCategorias);
        HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());

        cAdapter = new CategoriaAdapter(dbHelper.getCursorTodasAsCategorias(),getApplicationContext());
        cAdapter.setOnItemClickListener(listenerEditCategoria);
        rv.setAdapter(cAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    private CategoriaAdapter.OnItemClickListener listenerEditCategoria =
            new CategoriaAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                    TextView txtId = (TextView) itemView.findViewById(R.id.txtIdCategoria);

                    Intent intent = new Intent(CategoriaActivity.this, CategoriaDetalheActivity.class);
                    intent.putExtra("idCategoria", txtId.getText().toString());

                    startActivityForResult(intent, REQUEST_DETALHE_CATEGORIA);
                }
            };

    private CategoriaAdapter.OnItemClickListener listenerDeleteCategoria =
            new CategoriaAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    HunterAppDBHelper dbHelper = new HunterAppDBHelper(getApplicationContext());
                    TextView txtId = (TextView) itemView.findViewById(R.id.txtIdCategoria);
                    TextView txtTitulo = (TextView) itemView.findViewById(R.id.txtTituloCategoria);
                    dbHelper.deleteCategoriaById(txtId.getText().toString(), txtTitulo.getText().toString());

                    cAdapter.setCursor(dbHelper.getCursorTodasAsCategorias());
                    cAdapter.notifyItemRemoved(position);
                }
            };

    public void showMenuCategoria(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_categoria);
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.addCategoria:
                adicionarCategoria();
                return true;
            case R.id.listCandidatos:
                listarCandidatos();
                return true;
            default:
                return false;
        }

    }

    private void adicionarCategoria(){

    }
    private void listarCandidatos(){

    }
}
