package br.ufjf.dcc196.hunterapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.ufjf.dcc196.hunterapp.Model.*;

public class HunterAppDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=3;
    public static final String DATABASE_NAME="ToDoList";

    public HunterAppDBHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HunterAppContract.Candidato.CREATE_TABLE);
        db.execSQL(HunterAppContract.Producao.CREATE_TABLE);
        db.execSQL(HunterAppContract.Categoria.CREATE_TABLE);
        db.execSQL(HunterAppContract.Atividade.CREATE_TABLE);
        addBaseData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(HunterAppContract.Candidato.DROP_TABLE);
        db.execSQL(HunterAppContract.Producao.DROP_TABLE);
        db.execSQL(HunterAppContract.Categoria.DROP_TABLE);
        db.execSQL(HunterAppContract.Atividade.DROP_TABLE);
        onCreate(db);
    }

    //region Dados básicos

    private void addBaseData(SQLiteDatabase db){
        addCategoriaData(db);
        addCandidatoData(db);
    }

    private void addCategoriaData(SQLiteDatabase db){

        Categoria c1 = new Categoria();
        c1.setTitulo("Categoria 1");
        Categoria c2 = new Categoria();
        c2.setTitulo("Categoria 2");
        Categoria c3 = new Categoria();
        c3.setTitulo("Categoria 3");

        ContentValues values = populateContentValueCategoria(c1);
        db.insert(HunterAppContract.Categoria.TABLE_NAME,null,values);

        values = populateContentValueCategoria(c2);
        db.insert(HunterAppContract.Categoria.TABLE_NAME,null,values);

        values = populateContentValueCategoria(c3);
        db.insert(HunterAppContract.Categoria.TABLE_NAME,null,values);
    }

    private void addCandidatoData(SQLiteDatabase db){

        Candidato c1 = new Candidato("Candidato 1", "30/03/1994","3215-0101","perfil 1","mail1@mail.com");
        ContentValues values = populateContentValueCandidato(c1);
        db.insert(HunterAppContract.Candidato.TABLE_NAME,null,values);

        Candidato c2 = new Candidato("Candidato 2", "30/03/1996","3215-0202","perfil 2","mail2@mail.com");
        values = populateContentValueCandidato(c2);
        db.insert(HunterAppContract.Candidato.TABLE_NAME,null,values);

        Candidato c3 = new Candidato("Candidato 3", "30/03/1998","3215-0303","perfil 3","mail3@mail.com");
        values = populateContentValueCandidato(c3);
        db.insert(HunterAppContract.Candidato.TABLE_NAME,null,values);
    }

    //endregion

    //region Categoria
    public Cursor getCursorTodasAsCategorias(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sort = HunterAppContract.Categoria.COLUMN_TITULO + " ASC";
        Cursor c = db.query(HunterAppContract.Categoria.TABLE_NAME, camposCategoria, null, null, null, null, sort);
        return c;
    }

    public void deleteCategoriaById(String id, String titulo){
        SQLiteDatabase db = this.getWritableDatabase();
        String select = HunterAppContract.Categoria._ID+" = ?";

        String[] selectArgs = {id};
        db.delete(HunterAppContract.Categoria.TABLE_NAME,select,selectArgs);
        Log.i("DBINFO", "DEL titulo: " + titulo);
    }

    public Categoria getCategoriaById(Long id) {
        return getCategoriaById(id+"");
    }

    public Categoria getCategoriaById(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        String selecao = HunterAppContract.Categoria._ID+ "= ?";
        String[] args = {id};
        Cursor c = db.query(HunterAppContract.Categoria.TABLE_NAME,camposCategoria,selecao,args,null,null,null);
        int idxId = c.getColumnIndex(HunterAppContract.Categoria._ID);
        int idxTitulo = c.getColumnIndex(HunterAppContract.Categoria.COLUMN_TITULO);
        c.moveToFirst();
        if (c.getCount()>0){

            Long idCategoria = c.getLong(idxId);
            String titulo = c.getString(idxTitulo);

            return new Categoria(idCategoria,titulo);
        }
        return null;
    }

    public void atualizarCategoria(Categoria c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueCategoria(c);

        String selecao = HunterAppContract.Categoria._ID+ "= ?";
        String[] args = {c.getId()+""};

        db.update(HunterAppContract.Categoria.TABLE_NAME,values,selecao, args);
    }

    public void inserirCategoria(Categoria c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueCategoria(c);

        db.insert(HunterAppContract.Categoria.TABLE_NAME,null,values);
    }

    //endregion

    //region Content values

    private ContentValues populateContentValueCategoria(Categoria c){

        ContentValues values = new ContentValues();
        values.put(HunterAppContract.Categoria.COLUMN_TITULO,c.getTitulo());

        return values;

    }

    private ContentValues populateContentValueCandidato(Candidato c){
        ContentValues values = new ContentValues();
        values.put(HunterAppContract.Candidato.COLUMN_NOME,c.getNome());
        values.put(HunterAppContract.Candidato.COLUMN_NASCIMENTO,c.getNascimento());
        values.put(HunterAppContract.Candidato.COLUMN_TELEFONE,c.getTelefone());
        values.put(HunterAppContract.Candidato.COLUMN_PERFIL,c.getPerfil());
        values.put(HunterAppContract.Candidato.COLUMN_EMAIL,c.getEmail());

        return values;
    }

    //endregion

    //region Campos
    private final String[] camposCategoria = {
            HunterAppContract.Categoria._ID,
            HunterAppContract.Categoria.COLUMN_TITULO
    };

    //endregion

}
